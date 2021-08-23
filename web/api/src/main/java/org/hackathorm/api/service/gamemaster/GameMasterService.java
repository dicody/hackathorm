package org.hackathorm.api.service.gamemaster;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.domain.gamemaster.GameRequest;
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
        data.setP1Id(game.getPlayers().get(0).getSubmittedBy().getId());
        data.setP1ServiceId(game.getPlayers().get(0).getSubmittedBy().getId());
        data.setP2Id(game.getPlayers().get(1).getSubmittedBy().getId());
        data.setP2ServiceId(game.getPlayers().get(1).getSubmittedBy().getId());
        return gameMasterClient.publishPlayer(data);
    }
}
