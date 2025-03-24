package org.idiotsdalcheon.emblemeleven.game.service;

import lombok.RequiredArgsConstructor;
import org.idiotsdalcheon.emblemeleven.game.dao.ClubRepository;
import org.idiotsdalcheon.emblemeleven.game.dao.PlayerClubRepository;
import org.idiotsdalcheon.emblemeleven.game.dao.PlayerRepository;
import org.idiotsdalcheon.emblemeleven.game.domain.Club;
import org.idiotsdalcheon.emblemeleven.game.domain.Player;
import org.idiotsdalcheon.emblemeleven.game.domain.PlayerClub;
import org.idiotsdalcheon.emblemeleven.game.dto.ClubDto;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerDto;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerInfoResponse;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerSaveRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final PlayerClubRepository playerClubRepository;

    @Override
    @Transactional
    public ResponseEntity<?> savePlayer(PlayerSaveRequest playerSaveRequest) {
        // player 저장
        Player player = Player.builder()
                .name(playerSaveRequest.getPlayerName())
                .photoUrl(playerSaveRequest.getPlayerUrl())
                .build();
        player = playerRepository.save(player);

        // club 저장
        for(ClubDto clubDto : playerSaveRequest.getClubs()){
            Club club = clubRepository.findByName(clubDto.getClubName())
                    .orElseGet(() -> clubRepository.save(
                            Club.builder()
                                    .name(clubDto.getClubName())
                                    .emblemUrl(clubDto.getEmblemUrl())
                                    .build()
                    ));

            // PlayerClub 저장
            PlayerClub playerClub = PlayerClub.builder()
                    .player(player)
                    .club(club)
                    .order(clubDto.getOrder())
                    .build();
            playerClubRepository.save(playerClub);
        }

        return ResponseEntity.ok("저장 완료");
    }

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

                    List<String> clubList = player.getPlayerClubs().stream()
                            .sorted(Comparator.comparing(PlayerClub::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                            .map(pc -> pc.getClub().getEmblemUrl())
                            .collect(Collectors.toList());

                    return new PlayerDto(playerName, playerUrl, clubList);
                })
                .collect(Collectors.toList());

        return new PlayerInfoResponse(playerResponseList);
    }
}
