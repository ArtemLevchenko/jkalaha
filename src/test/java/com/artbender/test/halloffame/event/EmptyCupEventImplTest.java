package com.artbender.test.halloffame.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.Player;
import com.artbender.core.model.Turn;
import com.artbender.service.event.AdditionalTurnEventImpl;
import com.artbender.service.event.CreateBoardEventImpl;
import com.artbender.service.event.EmptyCupEventImpl;
import com.artbender.service.event.TurnEventImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EmptyCupEventImplTest {

    private GameContext gameContext;

    private final CreateBoardEventImpl createBoardEvent = new CreateBoardEventImpl();
    private final TurnEventImpl turnEvent = new TurnEventImpl();
    private final AdditionalTurnEventImpl additionalTurnEvent = new AdditionalTurnEventImpl();
    private final EmptyCupEventImpl emptyCupEvent = new EmptyCupEventImpl();

    @Before
    public void setUp() throws Exception {
        gameContext = new GameContext(Arrays.asList(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH),
                new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH)));
        createBoardEvent.execute(gameContext);
        gameContext.setCurrentTurn(new Turn(3));
        gameContext.setTakeTurnPlayer(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH));
    }


    @Test
    public void testPlayerCannotCaptureFromAnotherPlayerCup() {
        Turn currentTurn = new Turn(1);
        currentTurn.setLastSownIndex(12);
        gameContext.setCurrentTurn(currentTurn);
        emptyCupEvent.execute(gameContext);
        assertEquals("Another player should take a turn", new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH).getName(), gameContext.getTakeTurnPlayer().getName());
        assertNull("Current turn should be null", gameContext.getCurrentTurn());
    }

    @Test
    public void testTryingToCaptureWithEmptyOppositeCup() throws Exception {
        // changing value to 13 to put the last stone into the cups, where we are starting
        int[] cups = gameContext.getBoard().getCups();
        cups[3] = 13;
        turnEvent.execute(gameContext);
        additionalTurnEvent.execute(gameContext);
        Player futurePreviousTakeTurnPlayer = gameContext.getTakeTurnPlayer();
        // setting opposite cup to 0
        cups[9] = 0;
        emptyCupEvent.execute(gameContext);
        int playerCupValue = emptyCupEvent.getNumberOfStonesByIndex(gameContext.getBoard(), futurePreviousTakeTurnPlayer.getPitIndex());
        assertEquals("cup value should be 1 since opposite cup is empty",
                1, playerCupValue);
        assertEquals("Another player should take a turn", new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH).getName(), gameContext.getTakeTurnPlayer().getName());
        assertEquals(1, cups[3]);
        assertEquals(0, cups[9]);
        assertNull("Current turn should be null", gameContext.getCurrentTurn());
    }

    @Test
    public void executeWithCupNotOwnedByPlayer() throws Exception {
        // changing value to 5 to put the last stone into the cup owned by another player
        int[] cups = gameContext.getBoard().getCups();
        cups[3] = 5;
        cups[8] = 0;
        turnEvent.execute(gameContext);
        additionalTurnEvent.execute(gameContext);
        Player futurePreviousTakeTurnPlayer = gameContext.getTakeTurnPlayer();
        emptyCupEvent.execute(gameContext);
        int playerCupValue = emptyCupEvent.getNumberOfStonesByIndex(gameContext.getBoard(), futurePreviousTakeTurnPlayer.getPitIndex());
        assertEquals("cup value should NOT be increased by capturing",
                1, playerCupValue);
        assertEquals("Another player should take a turn", new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH).getName(), gameContext.getTakeTurnPlayer().getName());
        assertNull("Current turn should be null", gameContext.getCurrentTurn());
    }
}
