package de.benja.battleship.Game;

import de.benja.battleship.Player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Long> {

}
