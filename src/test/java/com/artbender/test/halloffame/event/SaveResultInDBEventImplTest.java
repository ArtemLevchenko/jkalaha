package com.artbender.test.event;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.GameContext;
import com.artbender.core.model.HallOfFame;
import com.artbender.core.model.Player;
import com.artbender.service.ServiceConfig;
import com.artbender.service.event.SaveResultInDBEventImpl;
import com.artbender.service.hallofame.HallOfFameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SaveResultInDBEventImplTest {

    @Autowired
    private SaveResultInDBEventImpl saveResultInDBEvent;
    @Autowired
    private HallOfFameService hallOfFameService;
    private GameContext gameContext;

    @Before
    public void setUp() throws Exception {
        gameContext = new GameContext(Arrays.asList(new Player(GameConstants.SOUTH_PLAYER_NAME, GameConstants.PIT_INDEX_SOUTH),
                new Player(GameConstants.NORTH_PLAYER_NAME, GameConstants.PIT_INDEX_NORTH)));
        gameContext.setOver(Boolean.TRUE);
        gameContext.setWinner(GameConstants.SOUTH_PLAYER_NAME);
    }

    @Test
    public void testSaveAndGetAllHallOfFame() throws Exception {
        saveResultInDBEvent.execute(gameContext);
        List<HallOfFame> dbHallOfFameList = hallOfFameService.findAllHallOfFame();
        Assert.assertEquals("Checked that Hall of Fame row in DB", gameContext.getWinner(), dbHallOfFameList.get(dbHallOfFameList.size() - 1).getWinnerName());
    }

}
