package org.idiotsdalcheon.emblemeleven.game.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PlayerRepositoryTest {
    @Autowired
    private PlayerRepository playerRepository;

}