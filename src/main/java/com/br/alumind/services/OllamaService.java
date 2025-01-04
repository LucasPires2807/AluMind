package com.br.alumind.services;

import com.br.alumind.repositories.VectorDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.ai.document.Document;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Primary
@Service
public class OllamaService {
    @Autowired
    private ChatClient.Builder chatClientBuilder;
    @Autowired
    private RagService ragService;
    @Autowired
    private VectorDatabaseRepository repository;

    private static final Logger log = LoggerFactory.getLogger(DataLoaderService.class);


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
        Document doc = new Document(message);
        repository.addDocuments(Collections.singletonList(doc));

        var chatClient = chatClientBuilder.build();
        Prompt prompt = ragService.generatePromptFromPromptRequest(message);

        return chatClient
                .prompt(prompt)
                .call()
                .content();

    }
}
