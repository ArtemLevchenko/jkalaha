package com.artbender.service.event;

import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


/**
 * Order(1). Event to handle player turn.
 * Implement the basic rules:
 * -Play always moves around the board in a counter-clockwise circle (to the right)
 * -Only put seeds in your own cup, not your opponent’s cup.
 *
 * @author Artsiom Leuchanka
 */
@Service
@Order(1)
public class TurnEventImpl extends AbstractGameEventImpl implements GameEvent {

    @Override
    public void execute(GameContext gameContext) {
        if (!gameContext.isOver()) {
            int chosenCup = gameContext.getCurrentTurn().getChosenCup();
            if (canPlayerTakeTurn(gameContext.getTakeTurnPlayer(), chosenCup)) {
                turn(gameContext, chosenCup, opponentPitIndex(gameContext));
            }
        }
    }

    /**
     * Moves around the board in a counter-clockwise circle (to the right)s and put out one stone in a cup
     *
     * @param gameContext - current context
     * @param chosenCup  - chosen cup from which we will get all stones
     * @param opponentPitIndex - index of pit cup of another player
     */
    private void turn(GameContext gameContext, int chosenCup, Integer opponentPitIndex) {
        // by the rule we need to start from the next cup of start it
        int startPositionToMove = chosenCup + 1;
        int[] targetArray = gameContext.getBoard().getCups();
        // get all stones in the cup
        int stonesInCup = targetArray[chosenCup];
        // reset stones to zero for start position (by the kalaha rule)
        targetArray[chosenCup] = 0;
        // index to find the last of sown
        int lastSownIndex = -1;
        for (int i = 0; i < stonesInCup; i++) { // iterate and put 1 stone into each next cup
            lastSownIndex = (startPositionToMove + i) % targetArray.length;
            if (lastSownIndex == opponentPitIndex) { // skip opponent store and fill next cup
                lastSownIndex = (++i + startPositionToMove) % targetArray.length;
                //add 1 stone to avoid skipping one cell when we are jumping over opposite player's pit
                stonesInCup += 1;
            }
            targetArray[lastSownIndex] += 1;
        }
        gameContext.getCurrentTurn().setLastSownIndex(lastSownIndex);
    }

    private Integer opponentPitIndex(GameContext gameContext) {
        return gameContext.getPlayers().stream()
                .map(Player::getPitIndex)
                .filter(index -> gameContext.getTakeTurnPlayer().getPitIndex() != index).toList()
                .get(0);
    }
}
