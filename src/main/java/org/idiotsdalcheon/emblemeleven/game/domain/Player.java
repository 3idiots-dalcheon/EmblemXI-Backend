package org.idiotsdalcheon.emblemeleven.game.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String photoUrl;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    @Builder.Default
    private List<PlayerClub> playerClubs = new ArrayList<>();

    public List<Club> getClubs() {
        List<Club> clubs = new ArrayList<>();
        for (PlayerClub playerClub : playerClubs) {
            clubs.add(playerClub.getClub());
        }
        return clubs;
    }
}
