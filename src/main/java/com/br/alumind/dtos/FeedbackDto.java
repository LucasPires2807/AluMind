package com.br.alumind.dtos;
import com.br.alumind.models.FeaturesModel;
import com.br.alumind.models.FeedbackModel;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Objects;
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
    public record ResponseFeedback(UUID id, String sentiment, ArrayList<ResponseFeature> requested_features){}
    public record ResponseFeature(String code, String reason){}

   /* public ResponseFeedback buildFeedbackToResponseFeedback(FeedbackModel feedback, FeaturesModel featuresModel){
        ArrayList<ResponseFeature> arrayList = new ArrayList();
        if(!Objects.isNull(featuresModel)){
            arrayList.add(new ResponseFeature(featuresModel.getId().toString(), featuresModel.getReason()));
        }
        return new ResponseFeedback(feedback.getId(), feedback.getSentiment(), arrayList);
    } */
}

