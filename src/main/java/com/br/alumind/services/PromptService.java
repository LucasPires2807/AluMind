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

//serviço para se conectar com o banco de dados vetorial
@Service
public class PromptService {
    private static final Logger log = LoggerFactory.getLogger(PromptService.class);
    @Value("${queries.top-k:2}")
    private int topK;
    @Value("classpath:/rag-prompt-template.st")
    private Resource ragPromptTemplate;
    @Value("classpath:/prompt-template.st")
    private Resource promptTemplate;
    @Autowired
    private VectorDatabaseRepository documentRepository;

    public Prompt generatePromptFromPromptRequest(String clientPrompt) {
        log.info("Generating prompt for client prompt: {}", clientPrompt);
        Message systemMessage = getMessageFromTemplate(clientPrompt);
        UserMessage userMessage = new UserMessage(clientPrompt);
        return new Prompt(List.of(systemMessage, userMessage));
    }

    public Prompt generatePromptFromPromptRequestAndRag(String clientPrompt) {
        log.info("Generating prompt for client prompt: {}", clientPrompt);
        List<Document> docs = documentRepository.similaritySearchWithTopK(clientPrompt, 5);
        Message systemMessage = getMessageFromRagTemplate(docs, clientPrompt);
        UserMessage userMessage = new UserMessage(clientPrompt);
        return new Prompt(List.of(systemMessage, userMessage));
    }

    private Message getMessageFromRagTemplate(List<Document> similarDocuments, String question) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(ragPromptTemplate);
        Map<String, Object> mapper = new HashMap<>();
        // Adiciona os exemplos ao template
        for (int i = 0; i < 1; i++) {
            Document document = similarDocuments.get(i);
            Map<String, Object> metadata = document.getMetadata(); // Obtém os metadados do documento
            // Preenche os exemplos no mapper com os dados
            mapper.put("feedback_" + (i + 1), document.getContent());
            mapper.put("sentimento_" + (i + 1), metadata.getOrDefault("sentimentType", "")); // Tipo de sentimento
            mapper.put("justificativa_" + (i + 1), metadata.getOrDefault("justify", ""));    // Justificativa
        }

        mapper.put("feedback", question);
        return systemPromptTemplate.createMessage(mapper);
    }

    private Message getMessageFromTemplate(String question) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(promptTemplate);
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("feedback", question);
        return systemPromptTemplate.createMessage(mapper);
    }
}
