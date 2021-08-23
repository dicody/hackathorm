package org.hackathorm.api.domain.gamemaster;

import lombok.Data;

@Data
public class GameRequest {
    private String gameId;

    private String p1ServiceId;
    private String p1Id;

    private String p2ServiceId;
    private String p2Id;
}
