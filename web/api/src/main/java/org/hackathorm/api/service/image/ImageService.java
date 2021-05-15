package org.hackathorm.api.service.image;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.image.Image;
import org.hackathorm.api.service.DateService;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hackathorm.api.conf.MongoConstants.IMAGES_COLLECTION_NAME;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ReactiveMongoTemplate mongo;
    private final DateService dateService;

    public Flux<Image> list() {
        return mongo.findAll(Image.class, IMAGES_COLLECTION_NAME);
    }

    public Mono<Image> insert(Image image) {
        image.setSubmittedAt(dateService.getCurrentDate());
        // todo get principal from request
        image.setSubmittedBy("anonymous");
        return mongo.insert(image, IMAGES_COLLECTION_NAME);
    }

    public Mono<Image> get(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        return mongo.findOne(query, Image.class, IMAGES_COLLECTION_NAME);
    }
}
