package org.hackathorm.api.repository.image;

import org.hackathorm.api.domain.image.SolutionImage;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionImageRepository extends ReactiveCrudRepository<SolutionImage, String> {
}
