package de.benja.battleship.Game;

import de.benja.battleship.GamePlayer.GamePlayer;
import de.benja.battleship.Player.Player;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static java.util.stream.Collectors.toList;

@Entity
public class Game {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private Date date;
    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;


    public Game() {

    }

    public Game(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }

    public List<Player> getPlayers(){
        return gamePlayers.stream().map(sub -> sub.getPlayer()).collect(toList());
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }
}
