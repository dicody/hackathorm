package org.hackathorm.api.domain.player;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Player {
    @Id
    private String id;
    private String name;
}
