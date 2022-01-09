package com.artbender.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Using in REST API for request parameters
 *
 * @author Artsiom Leuchanka
 */
@NoArgsConstructor
@Getter
@Setter
public class CustomGameRequest {

    /**
     * Active player in the turn
     */
    private String takeTurnPlayer;

    /**
     * Active cup in the turn
     */
    private int currentCup;
}
