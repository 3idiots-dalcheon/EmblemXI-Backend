package org.idiotsdalcheon.emblemeleven.game.dao;

import org.idiotsdalcheon.emblemeleven.game.domain.PlayerClub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerClubRepository extends JpaRepository<PlayerClub, Long>  {
}
