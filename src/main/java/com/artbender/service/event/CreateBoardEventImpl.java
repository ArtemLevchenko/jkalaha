package com.artbender.service.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.Board;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Order(0). Event to handle Board creation and filling stones to each cup
 *
 * @author Artsiom Leuchanka
 */
@Service
@Order(0)
public class CreateBoardEventImpl implements GameEvent {

    @Override
    public void execute(GameContext gameContext) {
        if (!gameContext.isOver()) {
            Board board = gameContext.getBoard();
            if (!board.isFilled()) {
                List<Integer> pitIndexes = gameContext.getPlayers().stream().map(Player::getPitIndex).toList();
                int[] cups = board.getCups();
                for (int i = 0; i < cups.length; i++) {
                    if (!pitIndexes.contains(i)) {
                        cups[i] = GameConstants.INIT_STONES_PER_CUPS;
                    }
                }
                board.setFilled(Boolean.TRUE);
                gameContext.setStarted(Boolean.TRUE);
            }
        }
    }
}
