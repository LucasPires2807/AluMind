package com.br.alumind.services;


import com.br.alumind.dtos.FeedbackDto;
import com.br.alumind.models.FeedbackModel;
import com.br.alumind.repositories.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FeedbackService  {
    @Autowired
    OllamaService service;
    @Autowired
    private FeedbackRepository repository;

    private final FeedbackDto dto;

    public FeedbackService() {
        this.dto = new FeedbackDto();
    }

    public FeedbackDto.ResponseFeedback save(FeedbackDto.RequestFeedback request) {
        FeedbackDto.ResponseFeedback responseFeedeback = null;
        //classificar sentimento
        FeedbackModel classify = service.runClassify(request.feedback());
        FeedbackModel feedeback = new FeedbackModel().builder()
                .text(request.feedback())
                .sentiment(classify.getSentiment())
                .justify(classify.getJustify())
                .build();
        FeedbackModel createFeedback = repository.save(feedeback);
        responseFeedeback = dto.buildFeedbackToResponseFeedback(createFeedback);
        return responseFeedeback;
    }

    public FeedbackDto.ResponseFeedback findById(UUID id){
        Optional<FeedbackModel> Feedback = repository.findById(id);
        FeedbackDto.ResponseFeedback responseFeedeback = null;

        if(Feedback.isPresent()){
            return dto.buildFeedbackToResponseFeedback(Feedback.get());
        }
        return responseFeedeback;
    }

    public List<FeedbackDto.ResponseFeedback> findAll() {
        List<FeedbackModel> Feedebacks = repository.findAll();
        return Feedebacks.stream().map(Feedeback -> {
            return dto.buildFeedbackToResponseFeedback(Feedeback);
        }).toList();
    }
/*
    public FeedbackDto.ResponseFeedback saveFromAnalize(AnalysisResult analysisResult, String feedback){
        FeedbackDto.ResponseFeedback responseFeedeback = null;
        FeedbackModel feedeback = new FeedbackModel().builder()
                .text(feedback)
                .sentiment(analysisResult.getSentimento())
                .build();
        FeedbackModel createFeedback = repository.save(feedeback);
        FeaturesModel featuresModel = null;
        if(analysisResult.isSugestao()) {
            featuresModel = openAiService.saveFeatureFromAnalise(analysisResult.getFuncionalidade(), createFeedback.getId());
        }
        responseFeedeback = dto.buildFeedbackToResponseFeedback(createFeedback, featuresModel);
        return responseFeedeback;
    }

    public boolean delete(UUID id){
        boolean isDelet = false;
        if(repository.findById(id).isPresent()) {
            repository.deleteById(id);
            isDelet = true;
        }
        return isDelet;
    } */

}
