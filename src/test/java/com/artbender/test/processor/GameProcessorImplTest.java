package com.artbender.test.processor;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.dto.CustomGameRequest;
import com.artbender.core.dto.CustomGameResponse;
import com.artbender.service.ServiceConfig;
import com.artbender.service.api.IKalahaProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameProcessorImplTest {

    @Autowired
    private IKalahaProcessor gameProcessor;

    @Before
    public void setUp() throws Exception {
        gameProcessor.restart();
    }

    @Test
    public void testInit() throws Exception {
        CustomGameResponse gameResponse = gameProcessor.gameInfo();
        Assert.assertEquals("Two player in the battle", 2, gameResponse.getPlayers().size());
        Assert.assertEquals("marcoS should take a turn first", GameConstants.SOUTH_PLAYER_NAME, gameResponse.getTakeTurnPlayer());
        Assert.assertTrue("marcoS player should be on the game", gameResponse.getPlayers().contains(GameConstants.SOUTH_PLAYER_NAME));
        Assert.assertTrue("brandoN player should be on the game", gameResponse.getPlayers().contains(GameConstants.NORTH_PLAYER_NAME));
        Assert.assertNull("The battle is going to start. No winner", gameResponse.getWinner());
        Assert.assertArrayEquals("Board should be NOT initiated with non-zero values",
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                gameResponse.getCups());
    }

    @Test
    public void testRestartGame() throws Exception {
        testPlayTurn();
        CustomGameResponse gameResponse = gameProcessor.restart();
        Assert.assertEquals("Two player in the battle", 2, gameResponse.getPlayers().size());
        Assert.assertEquals("marcoS should take a turn first", GameConstants.SOUTH_PLAYER_NAME, gameResponse.getTakeTurnPlayer());
        Assert.assertTrue("marcoS player should be on the game", gameResponse.getPlayers().contains(GameConstants.SOUTH_PLAYER_NAME));
        Assert.assertTrue("brandoN player should be on the game", gameResponse.getPlayers().contains(GameConstants.NORTH_PLAYER_NAME));
        Assert.assertNull("The battle is going to start. No winner", gameResponse.getWinner());
        Assert.assertArrayEquals("Board should be NOT initiated with non-zero values",
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                gameResponse.getCups());
    }

    @Test
    public void testChangeTakeTurnPlayer() {
        CustomGameRequest customGameRequest = new CustomGameRequest();
        customGameRequest.setTakeTurnPlayer(GameConstants.NORTH_PLAYER_NAME);
        customGameRequest.setCurrentCup(13);
        CustomGameResponse response = gameProcessor.play(customGameRequest);
        Assert.assertEquals("Take-turn-player should be changed to marcoS", GameConstants.SOUTH_PLAYER_NAME, response.getTakeTurnPlayer());
    }

    @Test
    public void testPlayTurn() throws Exception {
        CustomGameRequest gameRequest = new CustomGameRequest();
        gameRequest.setTakeTurnPlayer(GameConstants.SOUTH_PLAYER_NAME);
        gameRequest.setCurrentCup(0);

        CustomGameResponse response = gameProcessor.play(gameRequest);

        Assert.assertArrayEquals("resulting board intermediate state is not matching to actual state",
                new int[]{0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0}, response.getCups());

        Assert.assertEquals("marcoS player should be given additional move", GameConstants.SOUTH_PLAYER_NAME, response.getTakeTurnPlayer());
        Assert.assertNull("The Game in progress. No winner", response.getWinner());
    }

}
