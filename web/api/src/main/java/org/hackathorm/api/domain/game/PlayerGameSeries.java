package org.hackathorm.api.domain.game;

import lombok.Getter;

@Getter
public abstract class PlayerGameSeries {
    private final String name;
    private final long value;

    public PlayerGameSeries(GameSeries name, long value) {
        this.name = name.name().toLowerCase();
        this.value = value;
    }
}
