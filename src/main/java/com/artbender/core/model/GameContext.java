package com.artbender.core.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Root of Game. GameContext entity save the state and behavior of the system and data
 *
 * @author Artsiom Leuchanka
 */
@Getter
@RequiredArgsConstructor
public class GameContext {
    @NonNull
    private final List<Player> players;
    private final Board board = new Board();
    // indicator of final
    @Setter
    private boolean over = Boolean.FALSE;
    // indicator of started
    @Setter
    private boolean started = Boolean.FALSE;
    @Setter
    private String winner;
    @Setter
    private Turn currentTurn;
    @Setter
    private Player takeTurnPlayer;
}
