package com.artbender.service.event;

import com.artbender.core.model.GameContext;
import com.artbender.core.model.HallOfFame;
import com.artbender.service.hallofame.HallOfFameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Order(5)
 * Event to handle and save game result.
 *
 * @author Artsiom Leuchanka
 */
@Service
@Order(5)
public class SaveResultInDBEventImpl implements GameEvent {

    private final HallOfFameService hallOfFameService;

    @Autowired
    public SaveResultInDBEventImpl(HallOfFameService hallOfFameService) {
        this.hallOfFameService = hallOfFameService;
    }

    @Override
    public void execute(GameContext gameContext) {
        if (gameContext.isOver() && gameContext.getWinner() != null) {
            HallOfFame hallOfFame = new HallOfFame();
            hallOfFame.setWinnerName(gameContext.getWinner());
            hallOfFame.setDateOfVictory(new Date());
            hallOfFameService.saveHallOfFame(hallOfFame);
        }
    }
}
