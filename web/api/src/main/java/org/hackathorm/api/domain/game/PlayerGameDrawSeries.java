package org.hackathorm.api.domain.game;

public class PlayerGameDrawSeries extends PlayerGameSeries {
    private PlayerGameDrawSeries(long value) {
        super(GameSeries.DRAW, value);
    }

    public static PlayerGameDrawSeries draw(long value) {
        return new PlayerGameDrawSeries(value);
    }
}
