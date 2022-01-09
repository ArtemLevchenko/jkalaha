package com.artbender.test.halloffame;

import com.artbender.core.constants.GameConstants;
import com.artbender.core.model.HallOfFame;
import com.artbender.service.ServiceConfig;
import com.artbender.service.hallofame.HallOfFameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HallOfFameServiceImplTest {

    @Autowired
    private HallOfFameService hallOfFameService;

    @Test
    public void testSaveAndGetAllHallOfFame() throws Exception {
        HallOfFame hallOfFame = new HallOfFame();
        hallOfFame.setWinnerName(GameConstants.SOUTH_PLAYER_NAME);
        hallOfFame.setDateOfVictory(new Date());
        hallOfFameService.saveHallOfFame(hallOfFame);
        List<HallOfFame> dbHallOfFameList = hallOfFameService.findAllHallOfFame();
        Assert.assertEquals("Checked that Hall of Fame row in DB", hallOfFame.getId(), dbHallOfFameList.get(dbHallOfFameList.size() - 1).getId());
    }
}
