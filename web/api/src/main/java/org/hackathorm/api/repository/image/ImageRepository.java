package org.hackathorm.api.repository.image;

import org.hackathorm.api.domain.image.Image;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends ReactiveCrudRepository<Image, String> {
}
