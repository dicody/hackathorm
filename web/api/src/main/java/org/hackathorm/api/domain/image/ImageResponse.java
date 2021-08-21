package org.hackathorm.api.domain.image;

import lombok.Value;

@Value
public class ImageResponse {
    String id;
    String name;
    String submittedBy;
    String submittedAt;
}
