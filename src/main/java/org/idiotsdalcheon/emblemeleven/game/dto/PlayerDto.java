package org.idiotsdalcheon.emblemeleven.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlayerDto {
    private String playerName;
    private String playerUrl;
    private List<String> emblemURL;
}
