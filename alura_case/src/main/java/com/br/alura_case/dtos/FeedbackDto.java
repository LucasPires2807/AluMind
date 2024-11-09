import lombok.*;
import org.joda.time.DateTimeZone;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FeedbackDTO {
    private UUID id;
    @NotNull
    private String text;


    @Builder
    public FeedbackDTO(UUID id, String text) {
        this.id = id;
        this.text = text;
    }

    public record RequestFeedback(String text){}

    public record ResponseFeedback(UUID id, String text){}

    public FeedbackDTO buildFeedbackToResponseFeedback(Feedback feedback){
        return new FeedbackDTO().builder()
                .id(feedback.getId())
                .text(feedback.getText())
                .build();
    }
}