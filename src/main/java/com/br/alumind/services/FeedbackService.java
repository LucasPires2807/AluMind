package com.br.alumind.services;


import com.br.alumind.utils.AnalysisResult;
import com.br.alumind.dtos.FeedbackDto;
import com.br.alumind.models.FeaturesModel;
import com.br.alumind.models.FeedbackModel;
import com.br.alumind.repositories.FeatureRepository;
import com.br.alumind.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackService  {
/*
    @Autowired
    private FeedbackRepository repository;
    //@Autowired
    //private GiminiService giminiService;
    @Autowired
    private OpenAiService openAiService;
    @Autowired
    private FeatureRepository featureRepository;
    private final FeedbackDto dto;

    public FeedbackService() {
        this.dto = new FeedbackDto();
    }

    public List<FeedbackDto.ResponseFeedback> findAll() {
        List<FeedbackModel> Feedebacks = repository.findAll();
        return Feedebacks.stream().map(Feedeback -> {
            FeaturesModel featuresModel = featureRepository.findByFeedbackId(Feedeback.getId());
            return dto.buildFeedbackToResponseFeedback(Feedeback, featuresModel);
        }).toList();
    }
    public FeedbackDto.ResponseFeedback findById(UUID id){
        Optional<FeedbackModel> Feedback = repository.findById(id);
        FeedbackDto.ResponseFeedback responseFeedeback = null;

        if(Feedback.isPresent()){
            FeaturesModel featuresModel = featureRepository.findByFeedbackId(Feedback.get().getId());
            return dto.buildFeedbackToResponseFeedback(Feedback.get(), featuresModel);
        }
        return responseFeedeback;
    }
    public FeedbackDto.ResponseFeedback save(FeedbackDto.RequestFeedback requestFeedback){
        FeedbackDto.ResponseFeedback responseFeedeback = null;
        String sentiment = openAiService.avaiableFeedback(requestFeedback.feedback());

        FeedbackModel feedeback = new FeedbackModel().builder()
                    .text(requestFeedback.feedback())
                    .sentiment(sentiment)
                    .build();
        FeedbackModel createFeedback = repository.save(feedeback);

        FeaturesModel featuresModel = openAiService.saveFeature(createFeedback.getText(), createFeedback.getId());

        responseFeedeback = dto.buildFeedbackToResponseFeedback(createFeedback, featuresModel);
        return responseFeedeback;
    }

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
