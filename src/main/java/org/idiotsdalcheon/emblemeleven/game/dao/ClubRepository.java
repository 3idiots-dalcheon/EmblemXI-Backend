package org.idiotsdalcheon.emblemeleven.game.dao;

import org.idiotsdalcheon.emblemeleven.game.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>  {
}
