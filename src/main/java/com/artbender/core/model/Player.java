package com.artbender.core.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Player Entity. pitIndex - is index of the store in cups
 *
 * @author Artsiom Leuchanka
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Player {
    @NonNull
    private String name;
    @NonNull
    private int pitIndex;
}
