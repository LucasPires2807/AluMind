package com.br.alumind.services;

import com.br.alumind.dtos.SentimentAnalysisDto;
import com.br.alumind.mappers.SentimentAnalysisMapper;
import com.br.alumind.models.FeedbackModel;
import com.br.alumind.models.SentimentAnalysisModel;
import com.br.alumind.repositories.SentimentAnalysisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SentimentAnalysisService {

    @Autowired
    private SentimentAnalysisRepository repository;
    @Autowired
    OllamaService service;
    @Autowired
    private SentimentAnalysisMapper mapper;

    public SentimentAnalysisDto saveAnalysis(FeedbackModel feedbackModel) {

        FeedbackModel model = service.runClassifyTest(feedbackModel);
        if (Objects.isNull(model)) return null;
        boolean isCorrect = false;

        if(model.getSentimento().equals(feedbackModel.getSentimento())) {
            log.info("Saving sentiment analysis to database: {}", feedbackModel.getSentimento());
            isCorrect = true;
        }

        SentimentAnalysisModel sentimentAnalysisModel = SentimentAnalysisModel.builder()
                .id_feedback(feedbackModel.getId())
                .predictedCorrectly(isCorrect).build();

        SentimentAnalysisModel savedModel = repository.save(sentimentAnalysisModel);
        return mapper.toDTO(savedModel, feedbackModel);
    }

    public boolean delete(UUID id) {
        Optional<SentimentAnalysisModel> model = repository.findById(id);
        if (model.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
