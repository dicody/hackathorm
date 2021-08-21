package org.hackathorm.api.repository.feedback;

import org.hackathorm.api.domain.feedback.Feedback;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends ReactiveCrudRepository<Feedback, String> {
}
