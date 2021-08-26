package org.hackathorm.api.service.gamemaster;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.domain.gamemaster.GameRequest;
import org.hackathorm.api.domain.image.SolutionImage;
import org.hackathorm.api.integration.GameMasterClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GameMasterService {

    private final GameMasterClient gameMasterClient;

    public Mono<Void> publishNewGame(Game game) {
        GameRequest data = new GameRequest();
        data.setGameId(game.getId());
        // todo playerId != playerServiceId
        SolutionImage p1 = game.getPlayers().get(0);
        SolutionImage p2 = game.getPlayers().get(1);
        data.setP1Id(p1.getSubmittedBy().getId());
        data.setP1ServiceId(p1.getSubmittedBy().getId());
        data.setP2Id(p2.getSubmittedBy().getId());
        data.setP2ServiceId(p2.getSubmittedBy().getId());
        return gameMasterClient.publishGame(data);
    }
}
