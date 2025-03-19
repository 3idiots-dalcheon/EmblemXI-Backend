package org.idiotsdalcheon.emblemeleven.game.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "club")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String emblemUrl;

    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
    private List<PlayerClub> playerClubs = new ArrayList<>();
}
