package com.br.alumind.dtos;
import com.br.alumind.models.FeedbackModel;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FeedbackDto {
    private UUID id;
    @NotNull
    private String text;
    @NotNull
    private String sentiment;

    @Builder
    public FeedbackDto(UUID id, String text, String sentiment) {
        this.id = id;
        this.text = text;
        this.sentiment = sentiment;
    }

    public record RequestFeedback(String feedback){}
    public record ResponseFeedback(int id, String sentiment, String feedback, String justify){}
    public record ResponseFeature(String code, String reason){}

   public ResponseFeedback buildFeedbackToResponseFeedback(FeedbackModel feedback){
       return new ResponseFeedback(feedback.getId(),
               feedback.getSentimento(),
               feedback.getFeedback(),
               feedback.getJustificativa());
    }
}

