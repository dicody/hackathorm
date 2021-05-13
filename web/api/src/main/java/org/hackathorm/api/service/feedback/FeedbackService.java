package org.hackathorm.api.service.feedback;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.feedback.Feedback;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hackathorm.api.service.feedback.MongoConstants.FEEDBACK_COLLECTION_NAME;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final ReactiveMongoTemplate mongoTemplate;

    public Flux<Feedback> list() {
        return mongoTemplate.findAll(Feedback.class, FEEDBACK_COLLECTION_NAME);
    }

    public Mono<Feedback> insert(Feedback feedback) {
        return mongoTemplate.insert(feedback, FEEDBACK_COLLECTION_NAME);
    }

}
