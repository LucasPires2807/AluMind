package com.br.alumind.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.document.Document;

@Data
@NoArgsConstructor
@ToString(exclude = "")
@Entity
@Table(name="sentiment_analysis", schema = "public")
public class SentimentAnalysisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String text;
    String sentiment;
    boolean predictedCorrectly;
    private LocalDateTime date_create;

    @Builder
    public SentimentAnalysisModel(UUID id, String text, String sentiment,boolean predictedCorrectly) {
        this.id = id;
        this.text = text;
        this.sentiment = Sentiment.convertToEnum(sentiment).toString();
        this.predictedCorrectly = predictedCorrectly;
        this.date_create = LocalDateTime.now();
    }

    public Document toDocument(@NonNull SentimentAnalysisModel sentimentAnalysisModel) {
        return new Document(sentimentAnalysisModel.getText(), Map.of("sentimentType", sentimentAnalysisModel.sentiment, "id", sentimentAnalysisModel.getId()));
    }

}
