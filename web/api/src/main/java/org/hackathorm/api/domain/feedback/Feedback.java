package org.hackathorm.api.domain.feedback;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static org.hackathorm.api.conf.MongoConstants.FEEDBACKS_COLLECTION_NAME;

@Data
@Document(collection = FEEDBACKS_COLLECTION_NAME)
public class Feedback {
    @Id
    private String id;
    private FeedbackRate rate;
    private String comment;
}
