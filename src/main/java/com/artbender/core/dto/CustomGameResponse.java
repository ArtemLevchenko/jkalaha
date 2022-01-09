package com.artbender.core.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Using in REST API for response parameters
 *
 * @author Artsiom Leuchanka
 */
@Getter
@Builder(builderClassName = "Builder")
public class CustomGameResponse {

    // cups in the board
    private int[] cups;
    // active duelist information
    private List<String> players;
    // name of winner
    private String winner;
    // active player
    private String takeTurnPlayer;

    @lombok.Builder.Default
    private boolean over = Boolean.FALSE;

    @lombok.Builder.Default
    private boolean started = Boolean.FALSE;

}
