package org.hackathorm.api.domain.game;

import lombok.Data;

import java.util.List;

@Data
public class PlayerGameSeriesResponse {
    private String name;
    private List<PlayerGameSeries> series;
}
