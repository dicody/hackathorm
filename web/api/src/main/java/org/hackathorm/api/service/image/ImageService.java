package org.hackathorm.api.service.image;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.image.Image;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hackathorm.api.conf.MongoConstants.IMAGES_COLLECTION_NAME;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ReactiveMongoTemplate mongoTemplate;

    public Flux<Image> list() {
        return mongoTemplate.findAll(Image.class, IMAGES_COLLECTION_NAME);
    }

    public Mono<Image> insert(Image image) {
        return mongoTemplate.insert(image, IMAGES_COLLECTION_NAME);
    }
}
