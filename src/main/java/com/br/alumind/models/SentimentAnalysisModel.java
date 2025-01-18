package com.br.alumind.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@ToString(exclude = "")
@Entity
@Table(name="sentiment_analysis", schema = "public")
public class SentimentAnalysisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int id_feedback;
    boolean predictedCorrectly;
    private LocalDateTime date_create;

    @Builder
    public SentimentAnalysisModel(int id, int id_feedback ,boolean predictedCorrectly) {
        this.id = id;
        this.id_feedback = id_feedback;
        this.predictedCorrectly = predictedCorrectly;
        this.date_create = LocalDateTime.now();
    }

}
