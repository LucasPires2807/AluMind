package com.br.alumind.mappers;

import com.br.alumind.dtos.SentimentAnalysisDto;
import com.br.alumind.models.SentimentAnalysisModel;
import org.springframework.stereotype.Component;

@Component
public class SentimentAnalysisMapper {

    // Convert Model to DTO
    public SentimentAnalysisDto toDTO(SentimentAnalysisModel model) {
        return SentimentAnalysisDto.builder()
                .id(model.getId())
                .text(model.getText())
                .sentiment(model.getSentiment())
                .predictedCorrectly(model.isPredictedCorrectly())
                .build();
    }

    // Convert DTO to Model
    public SentimentAnalysisModel toModel(SentimentAnalysisDto dto) {
        return SentimentAnalysisModel.builder()
                .id(dto.getId())
                .text(dto.getText())
                .sentiment(dto.getSentiment())
                .predictedCorrectly(dto.isPredictedCorrectly())
                .build();
    }
}
