package com.artbender.service.api;

import com.artbender.core.dto.HallOfFameResponse;

/**
 * API Entry point to handle of Hall Of Fame statistics from RestController
 *
 * @author Artsiom Leuchanka
 */
public interface IHallOfFameProcessor {

    /**
     * Hall of Fame information
     *
     * @return - get all results
     */
    HallOfFameResponse getHallOfFame();
}
