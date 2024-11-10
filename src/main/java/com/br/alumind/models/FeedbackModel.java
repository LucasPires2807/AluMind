package com.br.alumind.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@ToString(exclude = "")
@Entity
@Table(name="feedback", schema = "public")
public class FeedbackModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String text;
    String sentiment;

    @Builder
    public FeedbackModel(UUID id, String text, String sentiment) {
        this.id = id;
        this.text = text;
        this.sentiment = Sentiment.convertToEnum(sentiment).toString();
    }

}
