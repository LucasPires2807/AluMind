package com.br.alumind.mappers;

import com.br.alumind.dtos.SentimentAnalysisDto;
import com.br.alumind.models.FeedbackModel;
import com.br.alumind.models.SentimentAnalysisModel;
import org.springframework.stereotype.Component;

@Component
public class SentimentAnalysisMapper {

    // Convert Model to DTO
    public SentimentAnalysisDto toDTO(SentimentAnalysisModel model, FeedbackModel feedbackModel) {
        return SentimentAnalysisDto.builder()
                .id(model.getId())
                .text(feedbackModel.getFeedback())
                .sentiment(feedbackModel.getSentimento())
                .predictedCorrectly(model.isPredictedCorrectly())
                .build();
    }

    // Convert DTO to Model
    public SentimentAnalysisModel toModel(SentimentAnalysisDto dto, FeedbackModel feedbackModel) {
        return SentimentAnalysisModel.builder()
                .id(dto.getId())
                .id_feedback(feedbackModel.getId())
                .predictedCorrectly(dto.isPredictedCorrectly())
                .build();
    }
}
