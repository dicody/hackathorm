package org.hackathorm.api.domain.game;

import lombok.Data;

@Data
public class GameResultsDto {
    private String gameId;
    private String winner;
}