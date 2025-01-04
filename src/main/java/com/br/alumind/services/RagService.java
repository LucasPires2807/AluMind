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
        List<Document> docs = documentRepository.similaritySearchWithTopK(clientPrompt, topK);
        Message systemMessage = getMessageFromRagTemplate(docs);
        log.info("System message: {}", systemMessage.getContent());
        UserMessage userMessage = new UserMessage(clientPrompt);
        return new Prompt(List.of(systemMessage, userMessage));
    }
    private Message getMessageFromRagTemplate(List<Document> similarDocuments) {
        String documents = similarDocuments.stream().map(Document::getContent).collect(Collectors.joining("\n"));
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(ragPromptTemplate);
        return systemPromptTemplate.createMessage(Map.of("documents", documents));
    }
}
