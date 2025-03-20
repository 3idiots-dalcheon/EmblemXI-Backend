package org.idiotsdalcheon.emblemeleven.game.service;

import org.idiotsdalcheon.emblemeleven.game.dto.PlayerInfoResponse;

public interface PlayerService {
    public PlayerInfoResponse getRandomPlayerInfo(int cnt);
}
