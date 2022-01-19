package com.artbender.test.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import com.artbender.service.event.CreateBoardEventImpl;
import com.artbender.service.event.GameWinnerEventImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class GameWinnerEventImplTest {

    private GameContext gameContext;
    private final CreateBoardEventImpl createBoardEvent = new CreateBoardEventImpl();
    private GameWinnerEventImpl gameWinnerEvent = new GameWinnerEventImpl();

    @Before
    public void setUp() throws Exception {
        gameContext = new GameContext(Arrays.asList(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH),
                new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH)));
        createBoardEvent.execute(gameContext);
    }

    @Test
    public void checkSouthWinner() throws Exception {
        // set up 0 for south player
        int[] cups = gameContext.getBoard().getCups();
        for (int i = 0; i < GameConstants.CUPS_PER_PLAYER; i++) {
            cups[i] = 0;
        }
        cups[6] = 40;
        gameWinnerEvent.execute(gameContext);
        assertTrue("Game should be over", gameContext.isOver());
        assertEquals("South Winner", new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH).getName(), gameContext.getWinner());
        Assert.assertArrayEquals("Only pits has values",
                new int[]{0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 0, 0, 0, 36}, gameContext.getBoard().getCups());
    }

    @Test
    public void checkNorthWinner() throws Exception {
        // set up 0 for north player
        int[] cups = gameContext.getBoard().getCups();
        for (int i = 7; i < 14; i++) {
            cups[i] = 0;
        }
        cups[13] = 40;
        gameWinnerEvent.execute(gameContext);
        assertTrue("Game should be over", gameContext.isOver());
        assertEquals("North Winner ", new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH).getName(), gameContext.getWinner());
        Assert.assertArrayEquals("Only pits has values",
                new int[]{0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 40}, gameContext.getBoard().getCups());
    }

    @Test
    public void checkDraw() throws Exception {
        // set up 0 for south player
        int[] cups = gameContext.getBoard().getCups();
        for (int i = 0; i < GameConstants.CUPS_PER_PLAYER; i++) {
            cups[i] = 0;
        }
        cups[6] = 36; // setting 36 to south store (the same should be in the north store)
        gameWinnerEvent.execute(gameContext);
        assertTrue("Game should be over", gameContext.isOver());
        assertEquals("DRAW: ", "DRAW", gameContext.getWinner());
        Assert.assertArrayEquals("Only pits has values",
                new int[]{0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 36}, gameContext.getBoard().getCups());
    }
}
