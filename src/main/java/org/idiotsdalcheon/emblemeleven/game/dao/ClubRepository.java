package org.idiotsdalcheon.emblemeleven.game.dao;

import org.idiotsdalcheon.emblemeleven.game.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long>  {
    Optional<Club> findByName(String name);
}
