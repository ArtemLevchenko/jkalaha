package com.artbender.test.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import com.artbender.service.event.CreateBoardEventImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


public class CreateBoardEventImplTest {

    private GameContext gameContext;
    private final CreateBoardEventImpl createBoardEvent = new CreateBoardEventImpl();

    @Before
    public void setUp() throws Exception {
        gameContext = new GameContext(Arrays.asList(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH),
                new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH)));
    }

    @Test
    public void checkGameIsOver() throws Exception {
        gameContext.setOver(Boolean.TRUE);
        createBoardEvent.execute(gameContext);
        Assert.assertEquals("Game over. If game has finished after CreateBoardEvent. The sum should be 0", 0, Arrays.stream(gameContext.getBoard().getCups()).sum());
    }

    @Test
    public void executeCreationOfBoard() throws Exception {
        createBoardEvent.execute(gameContext);
        Assert.assertArrayEquals("Init: ", new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0}, gameContext.getBoard().getCups());
        Assert.assertTrue("Flag for FILL should be TRUE", gameContext.getBoard().isFilled());
        Assert.assertTrue("Flag for START should be TRUE", gameContext.isStarted());
    }
}
