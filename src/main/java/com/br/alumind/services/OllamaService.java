package com.br.alumind.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Primary
@Service
public class OllamaService {
    private final ChatClient.Builder chatClientBuilder;
    @Autowired
    private DataLoaderService dataLoaderService;
    @Autowired
    private VectorStore vectorStore;
    @Value("classpath:/rag-prompt-template.st")
    private Resource ragPromptTemplate;
    private static final Logger log = LoggerFactory.getLogger(DataLoaderService.class);


    public OllamaService(ChatClient.Builder chatClientBuilder) {
        this.chatClientBuilder = chatClientBuilder;
    }

    public String run(String userPrompt) {

        var chatClient = chatClientBuilder.build();

        return chatClient
                .prompt()
                .user(userPrompt)
                .call()
                .content()
                .replace("\n", "");
    }


    public String runRAG(String message) throws IOException {

        dataLoaderService.load();
        var chatClient = chatClientBuilder.build();

        var similarDocuments = vectorStore.similaritySearch(
                SearchRequest
                        .query(message)
                        .withTopK(25)
        );

        var contentList = similarDocuments.stream()
                .filter(doc -> doc.getContent() != null)
                .map(doc -> {
                    //String filteredContent =  cleanMessage(doc.getContent());
                    return new Document(doc.toString());
                })
                .toList();

        //log.info("Documentos Recuperados para [%s]:".formatted(message));
        contentList.forEach(doc -> log.info(doc.getContent()));
        //log.info("Total: %d documentos".formatted(contentList.size()));

        var promptTemplate = new PromptTemplate(ragPromptTemplate.getContentAsString(StandardCharsets.UTF_8));
        var promptParameters = new HashMap<String,Object>();
        promptParameters.put("input",message);
        promptParameters.put("documents",contentList);
        var prompt = promptTemplate.create(promptParameters);


        return chatClient
                .prompt(prompt)
                .call()
                .content();

    }
}
