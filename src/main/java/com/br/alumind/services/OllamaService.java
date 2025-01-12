package com.br.alumind.services;

import com.br.alumind.models.FeedbackModel;
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

    public FeedbackModel runClassify(String userPrompt) {

        var chatClient = chatClientBuilder.build();

        FeedbackModel   feedbackModel = chatClient
                .prompt()
                .system("Analise o seguinte feedback de um cliente e classifique-o como " +
                        "\"positivo\", \"negativo\" ou \"neutro\". Justifique a sua escolha!.")
                .user(userPrompt)
                .call()
                .entity(FeedbackModel.class);

        return feedbackModel;
    }

    public String runClassifyTest(String userPrompt) {

        var chatClient = chatClientBuilder.build();

        return chatClient
                .prompt()
                .system("Analise o seguinte feedback de um cliente e classifique-o como " +
                        "\"positivo\", \"negativo\" ou \"neutro\". Qual justificativa feita pelo feedback?")
                .user(userPrompt)
                .call()
                .content();
    }


    public String runRAG(String message) throws IOException {

        var chatClient = chatClientBuilder.build();
        Prompt prompt = ragService.generatePromptFromPromptRequest(message);
        String ollamaResponse = chatClient.prompt(prompt).call().content();

        Document doc = new Document(ollamaResponse);
        repository.addDocuments(Collections.singletonList(doc));

        return ollamaResponse;

    }
}
