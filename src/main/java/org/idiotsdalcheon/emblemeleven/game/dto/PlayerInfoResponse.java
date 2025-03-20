package org.idiotsdalcheon.emblemeleven.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlayerInfoResponse {
    private List<PlayerDto> playerInfo;
}
