package com.artbender.service.processor;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.dto.CustomGameRequest;
import com.artbender.core.dto.CustomGameResponse;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import com.artbender.core.model.Turn;
import com.artbender.service.api.IKalahaProcessor;
import com.artbender.service.event.GameEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of operation from RestController
 *
 * @author Artsiom Leuchanka
 */
@Service
public class GameProcessorImpl implements IKalahaProcessor {

    private final List<GameEvent> gameEvents;
    private GameContext gameContext;

    @Autowired
    public GameProcessorImpl(List<GameEvent> gameEvents) {
        this.gameEvents = gameEvents;
        this.gameContext = initGame();
    }

    @Override
    public CustomGameResponse play(CustomGameRequest gameRequest) {
        Player newTakeTurnPlayer = gameContext.getPlayers().stream()
                .filter(player -> player.getName().equals(gameRequest.getTakeTurnPlayer())).toList()
                .get(0);
        gameContext.setTakeTurnPlayer(newTakeTurnPlayer);
        gameContext.setCurrentTurn(new Turn(gameRequest.getCurrentCup()));
        gameEvents.forEach(gameEvent -> gameEvent.execute(gameContext));
        return createResponse();
    }

    @Override
    public CustomGameResponse restart() {
        this.gameContext = initGame();
        return createResponse();
    }

    @Override
    public CustomGameResponse gameInfo() {
        return createResponse();
    }

    private CustomGameResponse createResponse() {
        return CustomGameResponse.builder().cups(gameContext.getBoard().getCups())
                .over(gameContext.isOver())
                .started(gameContext.isStarted())
                .winner(gameContext.getWinner())
                .players(gameContext.getPlayers().stream().map(Player::getName).collect(Collectors.toList()))
                .takeTurnPlayer(gameContext.getTakeTurnPlayer().getName())
                .build();
    }

    private GameContext initGame() {
        GameContext gameContext = new GameContext(Arrays.asList(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH),
                new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH)));
        gameContext.setTakeTurnPlayer(gameContext.getPlayers().get(0));
        return gameContext;
    }
}
