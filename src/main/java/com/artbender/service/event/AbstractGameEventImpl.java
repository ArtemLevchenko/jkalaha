package com.artbender.service.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.exceptions.InvalidCupElementException;
import com.artbender.core.model.Board;
import com.artbender.core.model.Player;

public abstract class AbstractGameEventImpl {

    protected boolean canPlayerTakeTurn(Player takeTurnPlayer, int chosenCup) {
        int pitIndex = takeTurnPlayer.getPitIndex();
        return chosenCup < pitIndex && chosenCup >= pitIndex - GameConstants.CUPS_PER_PLAYER;
    }

    /**
     * Get number of stones in cup by index
     * @param index index to the cups
     * @return number of stones for cup[index]
     */
    public int getNumberOfStonesByIndex(Board board, int index) {
        int[] boardCups = board.getCups();
        if (index < 0 || index >= boardCups.length) {
            throw new InvalidCupElementException("Current index " + index + " is invalid.");
        }
        return boardCups[index];
    }
}
