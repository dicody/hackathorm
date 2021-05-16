package org.hackathorm.api.domain.game;

import lombok.Data;
import org.hackathorm.api.domain.image.Image;
import org.hackathorm.api.domain.player.Player;

@Data
public class PlayersVersion {
    private Player player;
    private Image image;
}
