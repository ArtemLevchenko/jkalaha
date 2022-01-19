package com.artbender.test.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import com.artbender.core.model.Turn;
import com.artbender.service.event.AdditionalTurnEventImpl;
import com.artbender.service.event.CreateBoardEventImpl;
import com.artbender.service.event.TurnEventImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class AdditionalTurnEventImplTest {

    private GameContext gameContext;
    private final CreateBoardEventImpl createBoardEvent = new CreateBoardEventImpl();
    private final TurnEventImpl turnEvent = new TurnEventImpl();
    private final AdditionalTurnEventImpl additionalTurnEvent = new AdditionalTurnEventImpl();

    @Before
    public void setUp() throws Exception {
        gameContext = new GameContext(Arrays.asList(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH),
                new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH)));
        createBoardEvent.execute(gameContext);
        gameContext.setCurrentTurn(new Turn(3));
        gameContext.setTakeTurnPlayer(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH));
    }

    @Test
    public void testLastSownInThePitAndAdditionalMoveIsEligible() throws Exception {
        gameContext.getBoard().getCups()[3] = 3;
        turnEvent.execute(gameContext);
        Player takeTurnPlayer = gameContext.getTakeTurnPlayer();
        additionalTurnEvent.execute(gameContext);
        Assert.assertEquals("Active Player should be same when the last stone is put into the player's pit",
                takeTurnPlayer, gameContext.getTakeTurnPlayer());
        Assert.assertNull("Current turn will be null in case of the last stone is put into the player's pit", gameContext.getCurrentTurn());
    }

    @Test
    public void testGameIsOver() {
        gameContext.setOver(Boolean.TRUE);
        gameContext.getBoard().getCups()[3] = 3;
        turnEvent.execute(gameContext);
        additionalTurnEvent.execute(gameContext);
        Assert.assertNotNull("if game is over, current turn should NOT be null", gameContext.getCurrentTurn());
    }
}
