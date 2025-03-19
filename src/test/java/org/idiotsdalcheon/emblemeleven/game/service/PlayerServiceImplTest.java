package org.idiotsdalcheon.emblemeleven.game.service;

import org.idiotsdalcheon.emblemeleven.game.dao.PlayerRepository;
import org.idiotsdalcheon.emblemeleven.game.domain.Player;
import org.idiotsdalcheon.emblemeleven.game.dto.PlayerInfoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void 랜덤으로_선수들이_조회된다() {
        // given
        Player player1 = new Player(1L, "Lionel Messi", "messi.jpg", Arrays.asList());
        Player player2 = new Player(2L, "Cristiano Ronaldo", "ronaldo.jpg", Arrays.asList());
        Player player3 = new Player(3L, "Lionel Messi1", "messi1.jpg", Arrays.asList());
        Player player4 = new Player(4L, "Cristiano Ronaldo1", "ronaldo1.jpg", Arrays.asList());
        Player player5 = new Player(5L, "Lionel Messi2", "messi2.jpg", Arrays.asList());
        Player player6 = new Player(6L, "Cristiano Ronaldo2", "ronaldo2.jpg", Arrays.asList());
        Player player7 = new Player(7L, "Lionel Messi3", "messi3.jpg", Arrays.asList());
        Player player8 = new Player(8L, "Cristiano Ronaldo3", "ronaldo3.jpg", Arrays.asList());
        Player player9 = new Player(9L, "Lionel Messi4", "messi4.jpg", Arrays.asList());
        Player player10 = new Player(10L, "Cristiano Ronaldo4", "ronaldo4.jpg", Arrays.asList());

        when(playerRepository.count()).thenReturn(10L);
        when(playerRepository.findRandomPlayers(PageRequest.of(0, 5)))
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
}