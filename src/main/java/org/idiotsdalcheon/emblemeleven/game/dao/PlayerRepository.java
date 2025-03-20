package org.idiotsdalcheon.emblemeleven.game.dao;

import org.idiotsdalcheon.emblemeleven.game.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT p FROM Player p")
    Page<Player> findRandomPlayers(Pageable pageable);
}
