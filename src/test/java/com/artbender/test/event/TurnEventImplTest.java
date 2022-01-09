package com.artbender.test.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.Board;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import com.artbender.core.model.Turn;
import com.artbender.service.event.CreateBoardEventImpl;
import com.artbender.service.event.TurnEventImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TurnEventImplTest {

    private GameContext gameContext;
    private final CreateBoardEventImpl createBoardEvent = new CreateBoardEventImpl();
    private final TurnEventImpl turnEvent = new TurnEventImpl();

    @Before
    public void setUp() throws Exception {
        gameContext = new GameContext(Arrays.asList(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH),
                new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH)));
        createBoardEvent.execute(gameContext);
    }

    @Test
    public void executeTurnForSouthUser() throws Exception {
        gameContext.setCurrentTurn(new Turn(4));
        gameContext.setTakeTurnPlayer(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH));
        turnEvent.execute(gameContext);
        Assert.assertEquals("After sowing, last sown index for current turn should be kept",
                10, gameContext.getCurrentTurn().getLastSownIndex());
        Assert.assertArrayEquals("After sowing, number of stones in the cups should be changed",
                new int[] {6, 6, 6, 6, 0, 7, 1, 7, 7, 7, 7, 6, 6, 0}, gameContext.getBoard().getCups());
    }

    @Test
    public void executeTurnWith20Stones() throws Exception {
        gameContext.setCurrentTurn(new Turn(3));
        gameContext.setTakeTurnPlayer(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH));
        // changing value to 20 to check corner-case with opponent store. it should not be filled out in our attempt
        gameContext.getBoard().getCups()[3] = 20;
        turnEvent.execute(gameContext);
        Assert.assertArrayEquals("After turn up, values in the cup should be changed",
                new int[] {7, 7, 7, 1, 8, 8, 2, 8, 8, 8, 8, 7, 7, 0}, gameContext.getBoard().getCups());
        Assert.assertEquals("Check last of index for current TURN",
                10, gameContext.getCurrentTurn().getLastSownIndex());

    }

    @Test
    public void checkTurnForIncorrectPlayer() throws Exception {
        gameContext.setCurrentTurn(new Turn(3));
        gameContext.setTakeTurnPlayer(new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH));
        Board boardBeforeSowing = gameContext.getBoard();
        turnEvent.execute(gameContext);
        Assert.assertArrayEquals("Data has not changed in case of incorrect player turn", boardBeforeSowing.getCups(), gameContext.getBoard().getCups());
    }
}
