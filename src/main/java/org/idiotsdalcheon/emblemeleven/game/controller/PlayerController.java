package org.idiotsdalcheon.emblemeleven.game.controller;

import lombok.RequiredArgsConstructor;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerInfoResponse;
import org.idiotsdalcheon.emblemeleven.game.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    @RequestMapping("/random")
    public ResponseEntity<PlayerInfoResponse> getRandomPlayers(@RequestParam(defaultValue = "10") int cnt) {
        if (cnt != 5 && cnt != 10) {
            return ResponseEntity.badRequest().body(null);
        }

        PlayerInfoResponse response = playerService.getRandomPlayerInfo(cnt);
        return ResponseEntity.ok(response);
    }
}
