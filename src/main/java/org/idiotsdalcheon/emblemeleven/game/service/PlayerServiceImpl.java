package org.idiotsdalcheon.emblemeleven.game.service;

import lombok.RequiredArgsConstructor;
import org.idiotsdalcheon.emblemeleven.game.dao.PlayerRepository;
import org.idiotsdalcheon.emblemeleven.game.domain.Player;
import org.idiotsdalcheon.emblemeleven.game.dto.ClubDto;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerDto;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerInfoResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    @Override
    public PlayerInfoResponse getRandomPlayerInfo(int cnt) {
        long total = playerRepository.count();
        if (total == 0) {
            throw new RuntimeException("현재 선수들 데이터가 없습니다.");
        }

        Random random = new Random();
        int randomPage = random.nextInt((int) (total / cnt));

        List<Player> players = playerRepository.findRandomPlayers(PageRequest.of(randomPage, cnt)).getContent();

        List<PlayerDto> playerResponseList = players.stream()
                .map(player -> {
                    String playerName = player.getName();
                    String playerUrl = player.getPhotoUrl();

                    List<ClubDto> clubList = player.getPlayerClubs().stream()
                            .map(pc -> new ClubDto(pc.getClub().getName(), pc.getClub().getEmblemUrl()))
                            .collect(Collectors.toList());

                    return new PlayerDto(playerName, playerUrl, clubList);
                })
                .collect(Collectors.toList());

        return new PlayerInfoResponse(playerResponseList);
    }
}
