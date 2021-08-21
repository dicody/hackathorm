package org.hackathorm.api.domain.image;

import lombok.Value;

@Value
public class ImageDto {
    String id;
    String name;
    String submittedBy;
    String submittedAt;
}
