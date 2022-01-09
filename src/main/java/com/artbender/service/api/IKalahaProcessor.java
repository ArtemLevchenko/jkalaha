package com.artbender.service.api;

import com.artbender.core.dto.CustomGameRequest;
import com.artbender.core.dto.CustomGameResponse;


/**
 * API Entry point to handle of operation/events from RestController
 *
 * @author Artsiom Leuchanka
 */
public interface IKalahaProcessor {
    /**
     * @param gameRequest - request data
     * @return - game response for one of player turn
     */
    CustomGameResponse play(CustomGameRequest gameRequest);

    /**
     * Restart of game. Re-init data
     *
     * @return - game response with init data
     */
    CustomGameResponse restart();

    /**
     * Game details information
     *
     * @return - current state of game
     */
    CustomGameResponse gameInfo();
}
