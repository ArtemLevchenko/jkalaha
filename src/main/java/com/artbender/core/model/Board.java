package com.artbender.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Board entity to represent data for UI
 *
 * @author Artsiom Leuchanka
 */
public class Board {

    @Getter
    private final int[] cups = new int[14];

    // indicator of init cups
    @Setter
    @Getter
    private boolean filled = Boolean.FALSE;


}
