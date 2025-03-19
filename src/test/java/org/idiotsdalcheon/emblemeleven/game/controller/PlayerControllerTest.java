package org.idiotsdalcheon.emblemeleven.game.controller;

import org.idiotsdalcheon.emblemeleven.game.dao.ClubRepository;
import org.idiotsdalcheon.emblemeleven.game.dao.PlayerClubRepository;
import org.idiotsdalcheon.emblemeleven.game.dao.PlayerRepository;
import org.idiotsdalcheon.emblemeleven.game.domain.Club;
import org.idiotsdalcheon.emblemeleven.game.domain.Player;
import org.idiotsdalcheon.emblemeleven.game.domain.PlayerClub;
import org.idiotsdalcheon.emblemeleven.game.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private PlayerClubRepository playerClubRepository;

    @BeforeEach
    public void setUp() {
        Club club1 = Club.builder().name("FC Barcelona").emblemUrl("barcelona.jpg").build();
        Club club2 = Club.builder().name("Paris Saint-Germain").emblemUrl("psg.jpg").build();
        Club club3 = Club.builder().name("Manchester United").emblemUrl("manutd.jpg").build();
        Club club4 = Club.builder().name("Real Madrid").emblemUrl("realmadrid.jpg").build();
        Club club5 = Club.builder().name("Juventus").emblemUrl("juventus.jpg").build();
        Club club6 = Club.builder().name("Bayern Munich").emblemUrl("bayern.jpg").build();
        Club club7 = Club.builder().name("Chelsea").emblemUrl("chelsea.jpg").build();
        Club club8 = Club.builder().name("Liverpool").emblemUrl("liverpool.jpg").build();
        Club club9 = Club.builder().name("Arsenal").emblemUrl("arsenal.jpg").build();
        Club club10 = Club.builder().name("AC Milan").emblemUrl("acmilan.jpg").build();

        clubRepository.saveAll(Arrays.asList(club1, club2, club3, club4, club5, club6, club7, club8, club9, club10));

        // 10명의 Player 생성
        Player player1 = Player.builder()
                .name("Lionel Messi")
                .photoUrl("messi.jpg")
                .build();

        Player player2 = Player.builder()
                .name("Cristiano Ronaldo")
                .photoUrl("ronaldo.jpg")
                .build();

        Player player3 = Player.builder()
                .name("Neymar Jr.")
                .photoUrl("neymar.jpg")
                .build();

        Player player4 = Player.builder()
                .name("Kylian Mbappé")
                .photoUrl("mbappe.jpg")
                .build();

        Player player5 = Player.builder()
                .name("Robert Lewandowski")
                .photoUrl("lewandowski.jpg")
                .build();

        PlayerClub playerClub1_1 = PlayerClub.builder().player(player1).club(club1).build();
        PlayerClub playerClub1_2 = PlayerClub.builder().player(player1).club(club2).build();

        PlayerClub playerClub2_1 = PlayerClub.builder().player(player2).club(club3).build();
        PlayerClub playerClub2_2 = PlayerClub.builder().player(player2).club(club4).build();

        PlayerClub playerClub3_1 = PlayerClub.builder().player(player3).club(club5).build();
        PlayerClub playerClub3_2 = PlayerClub.builder().player(player3).club(club6).build();

        PlayerClub playerClub4_1 = PlayerClub.builder().player(player4).club(club7).build();
        PlayerClub playerClub4_2 = PlayerClub.builder().player(player4).club(club8).build();

        PlayerClub playerClub5_1 = PlayerClub.builder().player(player5).club(club9).build();
        PlayerClub playerClub5_2 = PlayerClub.builder().player(player5).club(club10).build();

        Player player6 = Player.builder().name("Player 6").photoUrl("player6.jpg").build();
        PlayerClub playerClub6_1 = PlayerClub.builder().player(player6).club(club1).build();

        Player player7 = Player.builder().name("Player 7").photoUrl("player7.jpg").build();
        PlayerClub playerClub7_1 = PlayerClub.builder().player(player7).club(club2).build();

        Player player8 = Player.builder().name("Player 8").photoUrl("player8.jpg").build();
        PlayerClub playerClub8_1 = PlayerClub.builder().player(player8).club(club3).build();

        Player player9 = Player.builder().name("Player 9").photoUrl("player9.jpg").build();
        PlayerClub playerClub9_1 = PlayerClub.builder().player(player9).club(club4).build();

        Player player10 = Player.builder().name("Player 10").photoUrl("player10.jpg").build();
        PlayerClub playerClub10_1 = PlayerClub.builder().player(player10).club(club5).build();

        playerRepository.saveAll(Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9, player10));

        playerClubRepository.saveAll(Arrays.asList(
                playerClub1_1, playerClub1_2,
                playerClub2_1, playerClub2_2,
                playerClub3_1, playerClub3_2,
                playerClub4_1, playerClub4_2,
                playerClub5_1, playerClub5_2,
                playerClub6_1, playerClub7_1, playerClub8_1,
                playerClub9_1, playerClub10_1
        ));
    }




    @Test
    void testGetRandomPlayers_ShouldReturnPlayers() throws Exception {
        // given
        int cnt = 5;

        // when
        mockMvc.perform(get("/players/random?cnt=" + cnt))
                .andDo(result -> {
                    System.out.println("Response: " + result.getResponse().getContentAsString());
                })
                .andExpect(status().isOk())  // 상태 코드 200 OK 확인
                .andExpect(jsonPath("$.playerInfo").isArray())
                .andExpect(jsonPath("$.playerInfo.length()").value(cnt));


    }

    @Test
    void 잘못된_request_param_오류_반환_테스트() throws Exception {
        // when
        mockMvc.perform(get("/players/random?cnt=7"))
                .andExpect(status().isBadRequest());
    }


}