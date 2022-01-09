package com.artbender.service.hallofame;

import com.artbender.core.model.HallOfFame;
import com.artbender.service.repository.HallOfFameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of Hall of Fame results
 *
 * @author Artsiom Leuchanka
 */
@Service
public class HallOfFameServiceImpl implements HallOfFameService {

    private final HallOfFameRepository hallOfFameRepository;

    @Autowired
    public HallOfFameServiceImpl(HallOfFameRepository hallOfFameRepository) {
        this.hallOfFameRepository = hallOfFameRepository;
    }

    @Override
    public List<HallOfFame> findAllHallOfFame() {
        return hallOfFameRepository.findAll();
    }

    @Override
    public void saveHallOfFame(HallOfFame hallOfFame) {
        hallOfFameRepository.save(hallOfFame);
    }
}
