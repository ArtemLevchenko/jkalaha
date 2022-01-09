package com.artbender.service.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Order(3)
 * Event to handle special rule:
 * - When the last stone in your hand lands in one of your own cups, if that cup had been empty you
 * get to keep all of the stones in your opponents cup on the opposite side. Put those captured stones,
 * as well as the last seed that you just played on your side, into the pit.
 *
 * @author Artsiom Leuchanka
 */
@Service
@Order(3)
public class EmptyCupEventImpl extends AbstractGameEventImpl implements GameEvent {

    @Override
    public void execute(GameContext gameContext) {
        if (gameContext.getCurrentTurn() != null && !gameContext.isOver()) {
            int lastSownIndex = gameContext.getCurrentTurn().getLastSownIndex();
            if (!canPlayerTakeTurn(gameContext.getTakeTurnPlayer(), lastSownIndex)) {
                prepareDataForNextTurn(gameContext);
                return;
            }
            captureOppositeAndCurrentCupIntoPlayerPit(gameContext, lastSownIndex);
            prepareDataForNextTurn(gameContext);
        }
    }

    /**
     * If value of cup by lastSownIndex is equal to 1, it means the cup was empty before we put last stone there
     *
     * @param gameContext  - our game context
     * @param lastSownIndex - index of cup where the last cup we put
     */
    private void captureOppositeAndCurrentCupIntoPlayerPit(GameContext gameContext, int lastSownIndex) {
        List<Integer> pitIndexes = gameContext.getPlayers().stream().map(Player::getPitIndex).toList();

        if (!pitIndexes.contains(lastSownIndex)
                && lastSownCupIsCurrentPlayerCups(lastSownIndex, gameContext.getTakeTurnPlayer())) { // only for cups
            int lastSownCupValue = getNumberOfStonesByIndex(gameContext.getBoard(), lastSownIndex);
            if (lastSownCupValue == 1) { // it means the cup was empty before we put last stone there
                int oppositeCupIndex = Math.abs(lastSownIndex - 12);
                int oppositeCupValue = getNumberOfStonesByIndex(gameContext.getBoard(), oppositeCupIndex);
                if (oppositeCupValue > 0) { // get stones
                    int[] cups = gameContext.getBoard().getCups();
                    cups[gameContext.getTakeTurnPlayer().getPitIndex()] += oppositeCupValue + lastSownCupValue;
                    cups[oppositeCupIndex] = 0;
                    cups[lastSownIndex] = 0;
                }
            }
        }
    }

    private boolean lastSownCupIsCurrentPlayerCups(int lastSownIndex, Player takeTurnPlayer) {
        int storeIndex = takeTurnPlayer.getPitIndex();
        return lastSownIndex < storeIndex && lastSownIndex >= storeIndex - GameConstants.CUPS_PER_PLAYER;
    }

    /**
     * Prepare for next turn. Set current turn as previous one. Find another player to take a turn.
     *
     * @param gameContext
     */
    private void prepareDataForNextTurn(GameContext gameContext) {
        gameContext.setCurrentTurn(null);
        Player nextPlayer = gameContext.getPlayers().stream()
                .filter(player -> !player.getName().equals(gameContext.getTakeTurnPlayer().getName())).toList()
                .get(0);
        gameContext.setTakeTurnPlayer(nextPlayer);
    }

}
