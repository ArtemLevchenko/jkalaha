package com.artbender.web.controller;

import com.artbender.core.dto.CustomGameRequest;
import com.artbender.core.dto.CustomGameResponse;
import com.artbender.core.dto.HallOfFameResponse;
import com.artbender.service.api.IHallOfFameProcessor;
import com.artbender.service.api.IKalahaProcessor;
import com.artbender.web.constant.GameAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Main Rest controller to handle game operation
 *
 * @author Artsiom Leuchanka
 */
@RestController
public class JKalahaGameController {

    private final IKalahaProcessor kalahaProcessor;
    private final IHallOfFameProcessor hallOfFameProcessor;

    @Autowired
    public JKalahaGameController(IKalahaProcessor kalahaProcessor, IHallOfFameProcessor hallOfFameProcessor) {
        this.kalahaProcessor = kalahaProcessor;
        this.hallOfFameProcessor = hallOfFameProcessor;
    }

    @PostMapping(value = GameAPI.MAKE_TURN)
    public ResponseEntity<CustomGameResponse> makeTurn(@RequestBody CustomGameRequest gameRequest) {
        return ResponseEntity.accepted().body(kalahaProcessor.play(gameRequest));
    }

    @GetMapping(value = GameAPI.HALL_OF_FAME)
    public ResponseEntity<HallOfFameResponse> getHallOfFame() {
        return ResponseEntity.ok(hallOfFameProcessor.getHallOfFame());
    }

    @GetMapping(value = GameAPI.INFO)
    public ResponseEntity<CustomGameResponse> getGameInfo() {
        return ResponseEntity.ok(kalahaProcessor.gameInfo());
    }

    @PostMapping(value = GameAPI.RESTART)
    public ResponseEntity<CustomGameResponse> restartGame() {
        return ResponseEntity.created(URI.create(GameAPI.INFO)).body(kalahaProcessor.restart());
    }

}
