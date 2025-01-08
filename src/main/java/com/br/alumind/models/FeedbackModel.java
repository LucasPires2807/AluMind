package com.br.alumind.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.ai.document.Document;


@Data
@NoArgsConstructor
@ToString(exclude = "")
@Entity
@Table(name="feedback", schema = "public")
public class FeedbackModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String text;
    String sentiment;
    private LocalDateTime date_create;

    @Builder
    public FeedbackModel(int id, String text, String sentiment) {
        this.id = id;
        this.text = text;
        this.sentiment = Sentiment.convertToEnum(sentiment).toString();
        this.date_create = LocalDateTime.now();
    }

    public Document toDocument(@NonNull FeedbackModel feedbackModel) {
        return new Document(feedbackModel.getText(), Map.of("sentimentType", feedbackModel.sentiment, "id", feedbackModel.getId()));
    }

}
