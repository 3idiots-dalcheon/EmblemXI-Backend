package org.idiotsdalcheon.emblemeleven.game.service;

import org.idiotsdalcheon.emblemeleven.game.dao.ClubRepository;
import org.idiotsdalcheon.emblemeleven.game.dao.PlayerClubRepository;
import org.idiotsdalcheon.emblemeleven.game.dao.PlayerRepository;
import org.idiotsdalcheon.emblemeleven.game.domain.Club;
import org.idiotsdalcheon.emblemeleven.game.domain.Player;
import org.idiotsdalcheon.emblemeleven.game.domain.PlayerClub;
import org.idiotsdalcheon.emblemeleven.game.dto.ClubDto;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerInfoResponse;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerSaveRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private PlayerClubRepository playerClubRepository;


    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void 랜덤으로_선수들이_조회된다() {
        // given
        Player player1 = new Player(1L, "Lionel Messi", "messi.jpg","아르헨티나", Arrays.asList());
        Player player2 = new Player(2L, "Cristiano Ronaldo", "ronaldo.jpg", "포르투갈", Arrays.asList());
        Player player3 = new Player(3L, "Lionel Messi1", "messi1.jpg","아르헨티나", Arrays.asList());
        Player player4 = new Player(4L, "Cristiano Ronaldo1", "ronaldo1.jpg","포르투갈", Arrays.asList());
        Player player5 = new Player(5L, "Lionel Messi2", "messi2.jpg","포르투갈", Arrays.asList());
        Player player6 = new Player(6L, "Cristiano Ronaldo2", "ronaldo2.jpg","포르투갈", Arrays.asList());
        Player player7 = new Player(7L, "Lionel Messi3", "messi3.jpg", "포르투갈", Arrays.asList());
        Player player8 = new Player(8L, "Cristiano Ronaldo3", "ronaldo3.jpg", "포르투갈", Arrays.asList());
        Player player9 = new Player(9L, "Lionel Messi4", "messi4.jpg", "포르투갈", Arrays.asList());
        Player player10 = new Player(10L, "Cristiano Ronaldo4", "ronaldo4.jpg", "포르투갈", Arrays.asList());
        Player player11 = new Player(11L, "Cristiano Ronaldo5", "ronaldo4.jpg", "포르투갈", Arrays.asList());

        when(playerRepository.count()).thenReturn(11L);
        when(playerRepository.findRandomPlayers(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(player1, player2, player3, player4, player5)));

        // when
        PlayerInfoResponse response = playerService.getRandomPlayerInfo(5);

        // then
        assertNotNull(response);
        assertEquals(5, response.getPlayerInfo().size());
        assertTrue(response.getPlayerInfo().stream().anyMatch(player -> player.getPlayerName().equals("Lionel Messi")));
        assertTrue(response.getPlayerInfo().stream().anyMatch(player -> player.getPlayerName().equals("Cristiano Ronaldo")));
    }


    @Test
    void 선수가_없으면_예외를_반환한다() {
        // given
        when(playerRepository.count()).thenReturn(0L);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            playerService.getRandomPlayerInfo(10);
        });

        // then
        assertEquals("현재 선수들 데이터가 없습니다.", exception.getMessage());
    }

    @Test
    void 정상적으로_선수가_저장된다() {
        // given
        ClubDto clubDto1 = new ClubDto("토트넘", "1234", 2);
        ClubDto clubDto2 = new ClubDto("레버쿠젠", "5678", 1);
        List<ClubDto> clubDtoList = Arrays.asList(clubDto1, clubDto2);

        PlayerSaveRequest playerSaveRequest = new PlayerSaveRequest(
                "손흥민",
                "https://example.com/photo.png",
                "대한민국",
                clubDtoList
        );


        // when
        ResponseEntity<?> response = playerService.savePlayer(playerSaveRequest);

        ArgumentCaptor<Player> playerCaptor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository, times(1)).save(playerCaptor.capture());
        Player savedPlayer = playerCaptor.getValue();

        ArgumentCaptor<Club> clubCaptor = ArgumentCaptor.forClass(Club.class);
        verify(clubRepository, times(2)).save(clubCaptor.capture());
        List<Club> saveClub = clubCaptor.getAllValues();

        ArgumentCaptor<PlayerClub> playerClubCaptor = ArgumentCaptor.forClass(PlayerClub.class);
        verify(playerClubRepository, times(2)).save(playerClubCaptor.capture());
        List<PlayerClub> savePlayerClub = playerClubCaptor.getAllValues();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualTo("저장 완료");

        assertThat(savedPlayer.getName()).isEqualTo("손흥민");
        assertThat(savedPlayer.getPhotoUrl()).isEqualTo("https://example.com/photo.png");

        assertThat(saveClub.get(0).getName()).isEqualTo("토트넘");
        assertThat(saveClub.get(1).getName()).isEqualTo("레버쿠젠");

        assertThat(savePlayerClub.get(0).getOrder()).isEqualTo(2);
        assertThat(savePlayerClub.get(1).getOrder()).isEqualTo(1);
    }

}