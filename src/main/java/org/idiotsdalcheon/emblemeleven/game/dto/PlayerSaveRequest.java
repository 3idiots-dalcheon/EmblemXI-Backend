package org.idiotsdalcheon.emblemeleven.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlayerSaveRequest {
    private String playerName;
    private String playerUrl;
    private String nationality;
    private List<ClubDto> clubs;
}
