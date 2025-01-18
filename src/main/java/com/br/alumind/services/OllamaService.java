package com.br.alumind.services;

import com.br.alumind.dtos.FeedbackDto;
import com.br.alumind.models.FeedbackModel;
import com.br.alumind.repositories.FeedbackRepository;
import com.br.alumind.repositories.VectorDatabaseRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.br.alumind.utils.Parser.parseFeedback;


@Primary
@Service
public class OllamaService {
    @Autowired
    private ChatClient.Builder chatClientBuilder;
    @Autowired
    private PromptService promptService;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private VectorDatabaseRepository repository;

    private FeedbackDto dto;
    public OllamaService() {
        this.dto = new FeedbackDto();
    }

    public FeedbackModel runClassifyTest(FeedbackModel feedbackModel) {
        var chatClient = chatClientBuilder.build();
        Prompt prompt = promptService.generatePromptFromPromptRequest(feedbackModel.getFeedback());
        String response = chatClient
                .prompt(prompt)
                .call()
                .content();
        System.out.println("resposta " + response);
        FeedbackModel feedbackModelParse = parseFeedback(response);
        if(Objects.isNull(feedbackModelParse)) return null;
        feedbackModelParse.setId(feedbackModel.getId());
        List<Document> documents = Collections.singletonList(feedbackModel.toDocument(feedbackModelParse));
        repository.addDocuments(documents);
        return feedbackModelParse;
    }

    public FeedbackDto.ResponseFeedback runClassify(String userPrompt) {
        var chatClient = chatClientBuilder.build();
        Prompt prompt = promptService.generatePromptFromPromptRequest(userPrompt);
        String response = chatClient
                .prompt(prompt)
                .call()
                .content();
        FeedbackModel feedbackModel = feedbackRepository.save(parseFeedback(response));
        repository.addDocuments(Collections.singletonList(feedbackModel.toDocument(feedbackModel)));
        return dto.buildFeedbackToResponseFeedback(feedbackModel);
    }

    public FeedbackDto.ResponseFeedback runRAG(String message) throws IOException {
        var chatClient = chatClientBuilder.build();
        Prompt prompt = promptService.generatePromptFromPromptRequestAndRag(message);
        String response = chatClient.
                prompt(prompt)
                .call().content();
        FeedbackModel feedbackModel = feedbackRepository.save(parseFeedback(response));
        repository.addDocuments(Collections.singletonList(feedbackModel.toDocument(feedbackModel)));
        return dto.buildFeedbackToResponseFeedback(feedbackModel);
    }

}
