package com.artbender.service.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Order(4)
 * Event to handle and calculate game winner. Rules:
 * - When one player no longer has any seeds in any of their houses, the game ends.
 *
 * @author Artsiom Leuchanka
 */
@Service
@Order(4)
public class GameWinnerEventImpl extends AbstractGameEventImpl implements GameEvent {

    @Override
    public void execute(GameContext gameContext) {
        if (!gameContext.isOver()) {
            boolean needToFinishTheGame = checkAndMove(gameContext);
            if (needToFinishTheGame) {
                calculateWinner(gameContext);
            }
        }
    }

    /**
     * Check both players one-by-one whether one of them has all the empty cups on the board.
     * 1. If so, stones from the cups of another player are moved to his pit.
     * 2. Next, cups are set up to zero
     * 3. If one has all the empty cups, game should be finished, otherwise, game should proceed.
     *
     * @param gameContext - our game context
     * @return true if game should be finished, otherwise false
     */
    private boolean checkAndMove(GameContext gameContext) {
        boolean needToFinishTheGame = Boolean.FALSE;
        List<Integer> sortedStoreIndexes = gameContext.getPlayers().stream().map(Player::getPitIndex).sorted().toList();
        Integer southPlayerStoreIndex = sortedStoreIndexes.get(0);
        Integer northPlayerStoreIndex = sortedStoreIndexes.get(1);
        boolean southPlayerFinished = checkWhetherPlayerHasAllCupsEmpty(gameContext, southPlayerStoreIndex);
        if (southPlayerFinished) {
            moveRemainingStonesToAnotherPlayerStore(gameContext, northPlayerStoreIndex);
            needToFinishTheGame = Boolean.TRUE;
        } else {
            boolean northPlayerFinished = checkWhetherPlayerHasAllCupsEmpty(gameContext, northPlayerStoreIndex);
            if (northPlayerFinished) {
                moveRemainingStonesToAnotherPlayerStore(gameContext, southPlayerStoreIndex);
                needToFinishTheGame = Boolean.TRUE;
            }
        }
        return needToFinishTheGame;
    }

    private void moveRemainingStonesToAnotherPlayerStore(GameContext gameContext, Integer whoHasNonEmptyCupStoreIndex) {
        int[] boardCups = gameContext.getBoard().getCups();
        int[] playerCups = Arrays.copyOfRange(boardCups, whoHasNonEmptyCupStoreIndex - GameConstants.CUPS_PER_PLAYER, whoHasNonEmptyCupStoreIndex);
        int sumOfStonesInNonEmptyCupsForAnotherPlayer = Arrays.stream(playerCups).sum();
        boardCups[whoHasNonEmptyCupStoreIndex] += sumOfStonesInNonEmptyCupsForAnotherPlayer;
        cleanUpCupsAfterSummingThemToStore(whoHasNonEmptyCupStoreIndex, boardCups);
    }

    private void cleanUpCupsAfterSummingThemToStore(Integer whoHasNonEmptyCupsStoreIndex, int[] boardCups) {
        for (int i = whoHasNonEmptyCupsStoreIndex - GameConstants.CUPS_PER_PLAYER; i < whoHasNonEmptyCupsStoreIndex; i++) {
            boardCups[i] = 0;
        }
    }

    private boolean checkWhetherPlayerHasAllCupsEmpty(GameContext gameContext, Integer storeIndex) {
        int[] playerCups = Arrays.copyOfRange(gameContext.getBoard().getCups(), storeIndex - GameConstants.CUPS_PER_PLAYER, storeIndex);
        return Arrays.stream(playerCups).sum() == 0;
    }

    private void calculateWinner(GameContext gameContext) {
        TreeMap<Integer, String> storeValue2PlayerName = gameContext.getPlayers().stream()
                .collect(Collectors.toMap(player -> getNumberOfStonesByIndex(gameContext.getBoard(), player.getPitIndex()),
                        Player::getName,
                        (a, b) -> a, // merge result
                        TreeMap::new));
        if (storeValue2PlayerName.size() == 1) { // if we have draw
            gameContext.setWinner(GameConstants.DRAW);
            gameContext.setOver(Boolean.TRUE);
        } else {
            String winner = storeValue2PlayerName.pollLastEntry().getValue();
            gameContext.setWinner(winner);
            gameContext.setOver(Boolean.TRUE);
        }
    }
}
