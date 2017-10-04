package de.benja.battleship.Salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.benja.battleship.GamePlayer.GamePlayer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {



    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long turn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="salvolocations")
    private List<String> salvolocations = new ArrayList<>();

    public Salvo() {

    }

    public Salvo (long turn, GamePlayer gamePlayer, List salvolocations) {
        this.turn = turn;
        this.gamePlayer = gamePlayer;
        this.salvolocations = salvolocations;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public long getTurn() {
        return turn;
    }

    public void setTurn(long turn) {
        this.turn = turn;
    }

    @JsonIgnore
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getSalvolocations() {
        return salvolocations;
    }

    public void setSalvolocations(List<String> salvolocations) {
        this.salvolocations = salvolocations;
    }
}
