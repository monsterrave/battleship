package de.benja.battleship.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.benja.battleship.Game.Game;
import de.benja.battleship.GamePlayer.GamePlayer;
import de.benja.battleship.Score.Score;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static de.benja.battleship.Score.Score.Score_Lose;
import static de.benja.battleship.Score.Score.Score_Tie;
import static de.benja.battleship.Score.Score.Score_Winner;
import static java.util.stream.Collectors.toList;

@Entity
public class Player {



    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String userName;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gameplayers;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score> scores;

    public Player() { }

    public Player(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gameplayers.add(gamePlayer);
    }

    public long getId() {
        return id;
    }

    @JsonIgnore
    public List<Game> getGames () {
        return gameplayers.stream().map(sub -> sub.getGame()).collect(toList());
    }

    public Set<Score> getScores() {
        return scores;
    }

    @JsonIgnore
    public Score getScore(Game game) {
        return scores.stream()
                .filter(score -> game.equals(score.getGame()))
                .findFirst()
                .orElse(null);

    }

    public double getTotalScore() {
        return scores.stream().mapToDouble(score -> score.getScore()).sum();

    }

    public int countPoints(double points) {

        return (int)  scores.stream().filter(score -> score.getScore() == points).count();
    }


    public double countWins() {
        return countPoints(Score.Score_Winner);
    }

    public double countTies() {
        return countPoints(Score.Score_Tie);
    }

    public int countLoss() {
        return countPoints(Score.Score_Lose);
    }
}
