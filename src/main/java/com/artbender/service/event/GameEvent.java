package com.artbender.service.event;

import com.artbender.core.model.GameContext;

/**
 * Event abstraction.
 * Each processing gameContext in the chain is responsible for a certain type of event,
 * and the processing is done, it forwards the command to the next processor in the chain
 * via a spring configuration (@Ordered).
 *
 * @author Artsiom Leuchanka
 */
public interface GameEvent {
    /**
     * Entry point for execute type of game event
     * Every event execute one of the game operation
     *
     * @param gameContext
     */
    void execute(GameContext gameContext);
}
