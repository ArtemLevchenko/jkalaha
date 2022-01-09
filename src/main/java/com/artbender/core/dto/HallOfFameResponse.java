package com.artbender.core.dto;

import com.artbender.core.model.HallOfFame;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Using in REST API for response Statistics parameters
 *
 * @author Artsiom Leuchanka
 */
@Getter
@Builder(builderClassName = "Builder")
public class HallOfFameResponse {
    // for statistics
    private List<HallOfFame> hallOfFameList;
}
