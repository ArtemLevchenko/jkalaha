package com.artbender.service.processor;

import com.artbender.core.dto.HallOfFameResponse;
import com.artbender.service.api.IHallOfFameProcessor;
import com.artbender.service.hallofame.HallOfFameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of Service to works with statistics operation
 *
 * @author Artsiom Leuchanka
 */
@Service
public class HallOfFameProcessorImpl implements IHallOfFameProcessor {

    private final HallOfFameService hallOfFameService;

    @Autowired
    public HallOfFameProcessorImpl(HallOfFameService hallOfFameService) {
        this.hallOfFameService = hallOfFameService;
    }

    @Override
    public HallOfFameResponse getHallOfFame() {
        return HallOfFameResponse.builder()
                .hallOfFameList(hallOfFameService.findAllHallOfFame())
                .build();
    }
}
