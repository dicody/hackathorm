package org.hackathorm.api.domain.image;

import lombok.Data;
import org.hackathorm.api.conf.MongoConstants;
import org.hackathorm.api.domain.player.Player;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = MongoConstants.IMAGES_COLLECTION_NAME)
public class Image {
    @Id
    private String id;
    private String url;
    private String name;
    private Player submittedBy;
    private Date submittedAt;
}
