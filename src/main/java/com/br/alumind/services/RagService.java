package com.br.alumind.services;

import com.br.alumind.repositories.VectorDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.chat.messages.Message;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//serviço para se conectar com o banco de dados vetorial

@Service
public class RagService {

    private static final Logger log = LoggerFactory.getLogger(RagService.class);

    @Value("${queries.top-k:2}")
    private int topK;
    @Value("classpath:/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    @Autowired
    private VectorDatabaseRepository documentRepository;

    public Prompt generatePromptFromPromptRequest(String clientPrompt) {
        log.info("Generating prompt for client prompt: {}", clientPrompt);
        List<Document> docs = documentRepository.similaritySearchWithTopK(clientPrompt, 5);
        Message systemMessage = getMessageFromRagTemplate(docs, clientPrompt);
        UserMessage userMessage = new UserMessage(clientPrompt);
        return new Prompt(List.of(systemMessage, userMessage));
    }
    private Message getMessageFromRagTemplate(List<Document> similarDocuments, String question) {

        List<String> documents = similarDocuments.stream().map(Document::getContent).collect(Collectors.toList());
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(ragPromptTemplate);
        Map<String, Object> mapper = new HashMap<>();
        // Adiciona os exemplos ao template
        for (int i = 0; i < documents.size(); i++) {
            mapper.put("feedback_exemplo_" + (i + 1), documents.get(i));
            mapper.put("sentimento_exemplo_" + (i + 1), ""); // Pode ser ajustado para o sentimento correspondente
            mapper.put("justificativa_exemplo_" + (i + 1), ""); // Pode ser ajustado para a justificativa correspondente
        }

        //mapper.put("documents", documents);
        mapper.put("feedback", question);
        return systemPromptTemplate.createMessage(mapper);
    }
}
