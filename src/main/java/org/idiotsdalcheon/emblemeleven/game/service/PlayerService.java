package org.idiotsdalcheon.emblemeleven.game.service;

import org.idiotsdalcheon.emblemeleven.game.dto.PlayerInfoResponse;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerSaveRequest;
import org.springframework.http.ResponseEntity;

public interface PlayerService {
    public PlayerInfoResponse getRandomPlayerInfo(int cnt);

    public ResponseEntity<?> savePlayer(PlayerSaveRequest playerRequestDto);
}
