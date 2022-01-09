package com.artbender.core.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Turn Entity. Stores information about turn
 *
 * @author Artsiom Leuchanka
 */
@Getter
@RequiredArgsConstructor
public class Turn {
    @NonNull
    private int chosenCup;

    @Setter
    private int lastSownIndex;
}
