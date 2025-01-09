package com.br.alumind.dtos;
import java.util.ArrayList;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SentimentAnalysisDto {
    private UUID id;
    @NotNull
    private String text;
    @NotNull
    private String sentiment;
    @NotNull
    private boolean predictedCorrectly;

    public SentimentAnalysisDto(UUID id, String text, String sentiment, boolean predictedCorrectly) {
        this.id = id;
        this.text = text;
        this.sentiment = sentiment;
        this.predictedCorrectly = predictedCorrectly;
    }


}
