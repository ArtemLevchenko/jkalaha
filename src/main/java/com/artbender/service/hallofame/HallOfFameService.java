package com.artbender.service.hallofame;

import com.artbender.core.model.HallOfFame;

import java.util.List;

/**
 * Service Layer for work with HallOfFame repository
 *
 * @author Artsiom Leuchanka
 */
public interface HallOfFameService {
    /**
     * Get ALL HallOfFame results
     * @return - all HallOfFame List
     */
    List<HallOfFame> findAllHallOfFame();
    /**
     * Save new result
     */
    void saveHallOfFame(HallOfFame hallOfFame);
}
