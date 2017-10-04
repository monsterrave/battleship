package de.benja.battleship.GamePlayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.benja.battleship.Game.Game;
import de.benja.battleship.Player.Player;
import de.benja.battleship.Salvo.Salvo;
import de.benja.battleship.Score.Score;
import de.benja.battleship.Ship.Ship;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GamePlayer {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Ship> ships = new HashSet<>();


    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Salvo> salvoes = new HashSet<>();

    public GamePlayer() {

    }

    public GamePlayer(Date date, Game game, Player player) {
        this.date = date;
        this.game = game;
        this.player = player;
    }
    @JsonIgnore
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    @JsonIgnore
    public Set<Ship> getShips() {
        return this.ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    @JsonIgnore
    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    @JsonIgnore
    public Score getScore() {
        return player.getScore(game);
    }
}
