package org.hackathorm.api.service.feedback;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.feedback.Feedback;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hackathorm.api.conf.MongoConstants.FEEDBACKS_COLLECTION_NAME;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final ReactiveMongoTemplate mongo;

    public Flux<Feedback> list() {
        return mongo.findAll(Feedback.class, FEEDBACKS_COLLECTION_NAME);
    }

    public Mono<Feedback> insert(Feedback feedback) {
        return mongo.insert(feedback, FEEDBACKS_COLLECTION_NAME);
    }

    public Mono<Feedback> get(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        return mongo.findOne(query, Feedback.class, FEEDBACKS_COLLECTION_NAME);
    }
}
