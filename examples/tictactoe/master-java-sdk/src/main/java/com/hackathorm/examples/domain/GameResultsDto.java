package com.hackathorm.examples.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResultsDto {
    private final String gameId;
    private final String winner;
}
