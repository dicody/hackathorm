package org.hackathorm.api.rest.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.domain.game.GameResponse;
import org.hackathorm.api.domain.game.PlayerGameSeriesResponse;
import org.hackathorm.api.domain.image.Image;
import org.hackathorm.api.domain.player.Player;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hackathorm.api.domain.game.PlayerGameDrawSeries.draw;
import static org.hackathorm.api.domain.game.PlayerGameLostSeries.lost;
import static org.hackathorm.api.domain.game.PlayerGameWonSeries.won;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameHandler {
    @NonNull
    public Mono<ServerResponse> list(ServerRequest request) {
        // todo
        Game game = new Game();
        game.setId("someuuid");
        game.setSequenceNumber(1L);
        Date startedAt = new Date();
        game.setStartedAt(startedAt);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startedAt);
        calendar.add(Calendar.MINUTE, 10);
        game.setFinishedAt(calendar.getTime());

        Player p1 = new Player();
        p1.setName("p1");
        p1.setId("1");
        Image p1Image = new Image();
        p1Image.setName("first version");
        p1Image.setSubmittedBy(p1);
        Player p2 = new Player();
        p2.setName("p2");
        p2.setId("2");
        Image p2Image = new Image();
        p2Image.setName("just do it");
        p2Image.setSubmittedBy(p2);

        game.setWinner(p1Image);
        game.setPlayers(List.of(p1Image, p2Image));
        game.setInfo("not much info to add..");

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(List.of(GameResponse.from(game), GameResponse.from(game), GameResponse.from(game)));
    }

    @NonNull
    public Mono<ServerResponse> insert(ServerRequest request) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public Mono<ServerResponse> get(ServerRequest request, String pathVariable) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public Mono<ServerResponse> statistics(ServerRequest request) {
        // todo
        PlayerGameSeriesResponse p1Series = new PlayerGameSeriesResponse();
        p1Series.setName("p1");
        p1Series.setSeries(List.of(won(6), lost(3), draw(1)));

        PlayerGameSeriesResponse p2Series = new PlayerGameSeriesResponse();
        p2Series.setName("p2");
        p2Series.setSeries(List.of(won(3), lost(6), draw(1)));

        PlayerGameSeriesResponse p3Series = new PlayerGameSeriesResponse();
        p3Series.setName("p3");
        p3Series.setSeries(List.of());
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(List.of(p1Series, p2Series, p3Series));
    }
}
