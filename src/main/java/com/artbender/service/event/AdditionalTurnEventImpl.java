package com.artbender.service.event;

import com.artbender.core.model.GameContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Order(2)
 * Event to handle and check if additional move is available for Player:
 * - When the last stone in your hand lands in your pit, take another turn.
 *
 * @author Artsiom Leuchanka
 */
@Service
@Order(2)
public class AdditionalTurnEventImpl implements GameEvent {

    @Override
    public void execute(GameContext gameContext) {
        if (!gameContext.isOver()) {
            int lastSownIndex = gameContext.getCurrentTurn().getLastSownIndex();
            if (gameContext.getTakeTurnPlayer().getPitIndex() == lastSownIndex) {
                gameContext.setCurrentTurn(null);
            }
        }
    }
}
