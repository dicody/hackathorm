package com.hackathorm.examples.service;

import com.hackathorm.examples.domain.PlayerDto;
import com.hackathorm.examples.integration.PlayerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;
import reactor.util.function.Tuples;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final PlayerClient playerClient;
    private final Sinks.Many<PlayerDto> sinks = Sinks.unsafe().many().multicast().directBestEffort();

    @PostConstruct
    void init() {
        // add custom rules for the game (replace Flux if necessary)
        sinks
                .asFlux()
                .doOnNext(player -> log.info("player added to queue: {}", player))
                .window(2)
                .flatMap(players -> players.collectList().map(list -> Tuples.of(list.get(0), list.get(1))))
                .doOnNext(players -> {
                    Optional<PlayerDto> winner = new TicTacToeGame(playerClient).runGame(players.getT1(), players.getT2());
                    log.info("got WINNER: {} between: {} and {}", winner, players.getT1(), players.getT2());
                })
                .onErrorContinue((throwable, o) -> log.error("error occurred with players: {}", o, throwable))
                .subscribe();
    }

    public void addPlayerToQueue(PlayerDto player) {
        sinks.emitNext(player, (signalType, emitResult) -> {
            log.error("error on emit! signalType: {}, emitResult: {}", signalType, emitResult);
            return false;
        });
    }
}
