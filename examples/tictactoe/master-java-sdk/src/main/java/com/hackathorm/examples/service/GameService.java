package com.hackathorm.examples.service;

import com.hackathorm.examples.domain.PlayerDto;
import com.hackathorm.examples.integration.AdminClient;
import com.hackathorm.examples.integration.PlayerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final AdminClient adminClient;
    private final PlayerClient playerClient;

    public Mono<String> runGame(String gameId, String p1Id, String p1ServiceId, String p2Id, String p2ServiceId) {
        PlayerDto p1 = new PlayerDto(p1Id, p1ServiceId, 'X');
        PlayerDto p2 = new PlayerDto(p2Id, p2ServiceId, 'O');

        Optional<PlayerDto> winner = new TicTacToeGame(playerClient).runGame(p1, p2);
                    log.info("got WINNER: {} between: {} and {}", winner, p1, p2);
                    return winner
                            .map(playerDto -> adminClient.notifyWithWinner(gameId, playerDto.getId()))
                            .orElseGet(() -> adminClient.notifyWithTie(gameId))
                            .map(v -> gameId);

    }
}
