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
    String feedback;
    String sentimento;
    private LocalDateTime date_create;
    String justificativa;

    @Builder
    public FeedbackModel(int id, String feedback, String sentimento, String justificativa) {
        this.id = id;
        this.feedback = feedback;
        this.sentimento = Sentiment.convertToEnum(sentimento).toString();
        this.date_create = LocalDateTime.now();
        this.justificativa = justificativa;
    }

    public Document toDocument(@NonNull FeedbackModel feedbackModel) {
        return new Document(feedbackModel.getFeedback(),
                Map.of("sentimento", feedbackModel.sentimento,
                        "justificativa", feedbackModel.justificativa,
                        "id", feedbackModel.getId())
        );
    }

    @Override
    public String toString() {
        return "FeedbackModel{" +
                "id=" + id +
                ", feedback='" + feedback + '\'' +
                ", sentimento='" + sentimento + '\'' +
                ", justificativa='" + justificativa + '\'' +
                '}';
    }

}
