package de.benja.battleship.Score;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.benja.battleship.Game.Game;
import de.benja.battleship.Player.Player;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {
    public static double Score_Winner = 1;
    public static double Score_Tie = 0.5;
    public static double Score_Lose = 0;


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private Date finishedDate;
    private double score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    public Score() {

    }

    public Score(Game game, Player player, double score) {
        this.game = game;
        this.player = player;
        this.score = score;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    @JsonIgnore
    public Player getPlayer() {
        return player;
    }
    @JsonIgnore
    public Game getGame() {
        return game;
    }
}
