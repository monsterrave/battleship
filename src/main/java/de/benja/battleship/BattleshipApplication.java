package de.benja.battleship;

import de.benja.battleship.Game.Game;
import de.benja.battleship.Game.GameRepository;
import de.benja.battleship.GamePlayer.GamePlayer;
import de.benja.battleship.GamePlayer.GamePlayerRepository;
import de.benja.battleship.Player.Player;
import de.benja.battleship.Player.PlayerRepository;
import de.benja.battleship.Salvo.Salvo;
import de.benja.battleship.Salvo.SalvoRepository;
import de.benja.battleship.Score.Score;
import de.benja.battleship.Score.ScoreRepository;
import de.benja.battleship.Ship.Ship;
import de.benja.battleship.Ship.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BattleshipApplication {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    ShipRepository shipRepository;

    @Autowired
    SalvoRepository salvoRepository;

    @Autowired
    ScoreRepository scoreRepository;


    private final double score_winner = 1;
    private final double score_tie  = .5;


    public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);
	}


    @Bean
    public CommandLineRunner initData() {
        return (args) -> {
            // save a couple of customers
            Player pone  = new Player("j.bauer@ctu.gov");
            playerRepository.save(pone);
            Player ptwo = new Player("c.obrian@ctu.gov");
            playerRepository.save(ptwo);
            Player pthree = new Player("t.almeida@ctu.gov");
            playerRepository.save(pthree);
            Player pfour = new Player("d.palmer@whitehouse.gov");
            playerRepository.save(pfour);
            Player pfive = new Player("kim_bauer@gmail.com");
            playerRepository.save(pfive);


            Date now = new Date();
            Date hour1 = Date.from(now.toInstant().plusSeconds(3600));
            Date hour2 = Date.from(hour1.toInstant().plusSeconds(3600));
            Date hour3 = Date.from(hour2.toInstant().plusSeconds(3600));
            Date hour4 = Date.from(hour3.toInstant().plusSeconds(3600));
            Date hour5 = Date.from(hour4.toInstant().plusSeconds(3600));

            Game gone = new Game(now);
            gameRepository.save(gone);
            Game gtwo = new Game(hour1);
            gameRepository.save(gtwo);
            Game gthree = new Game(hour2);
            gameRepository.save(gthree);
            Game gfour = new Game (hour3);
            gameRepository.save(gfour);
            Game gfive = new Game(hour4);
            gameRepository.save(gfive);
            Game gsix = new Game(hour5);
            gameRepository.save(gsix);

            GamePlayer gpone = new GamePlayer(now, gone, pone);
            gamePlayerRepository.save(gpone);
            GamePlayer gptwo = new GamePlayer(now, gone, ptwo);
            gamePlayerRepository.save (gptwo);
            GamePlayer gpthree = new GamePlayer(hour1, gtwo, pone);
            gamePlayerRepository.save(gpthree);
            GamePlayer gpfour = new GamePlayer(hour1, gtwo, ptwo);
            gamePlayerRepository.save(gpfour);
            GamePlayer gpfive = new GamePlayer(hour2, gthree,ptwo);
            gamePlayerRepository.save(gpfive);
            GamePlayer gpsix = new GamePlayer(hour2,gthree, pthree);
            gamePlayerRepository.save(gpsix);
            GamePlayer gpseven = new GamePlayer(hour3,gfour, pone);
            gamePlayerRepository.save(gpseven);
            GamePlayer gpeight = new GamePlayer(hour3, gfour, ptwo);
            gamePlayerRepository.save(gpeight);
            GamePlayer gpnine = new GamePlayer(hour4,gfive,pthree);
            gamePlayerRepository.save(gpnine);
            GamePlayer gpten = new GamePlayer(hour4,gfive,pone);
            gamePlayerRepository.save(gpten);
            GamePlayer gpeleven = new GamePlayer(hour5,gsix,pfour);
            gamePlayerRepository.save(gpeleven);

            List<String> location1 = Arrays.asList("H2", "H3", "H4");
            List<String> location2 = Arrays.asList("E1", "F1", "G1");
            List<String> location3 = Arrays.asList("B4", "B5");
            List<String> location4 = Arrays.asList("B5", "C5", "D5");
            List<String> location5 = Arrays.asList("F1", "F2");

            List<String> location6 = Arrays.asList("B5", "C5", "D5");
            List<String> location7 = Arrays.asList("C6", "C7");
            List<String> location8 = Arrays.asList("A2", "A3", "A4");
            List<String> location9 = Arrays.asList("G6", "H6");

            List<String> location10 = Arrays.asList("B5", "C5", "D5");
            List<String> location11 = Arrays.asList("C6", "C7");
            List<String> location12 = Arrays.asList("A2", "A3", "A4");
            List<String> location13 = Arrays.asList("G6", "H6");

            List<String> location14 = Arrays.asList("B5", "C5", "D5");
            List<String> location15 = Arrays.asList("C6", "C7");
            List<String> location16 = Arrays.asList("A2", "A3", "A4");
            List<String> location17 = Arrays.asList("G6", "H6");

            List<String> location18 = Arrays.asList("B5", "C5", "D5");
            List<String> location19 = Arrays.asList("C6", "C7");
            List<String> location20 = Arrays.asList("A2", "A3", "A4");
            List<String> location21 = Arrays.asList("G6", "H6");


            shipRepository.save(new Ship("Destroyer", gpone, location1));
            shipRepository.save(new Ship("Submarine", gpone, location2));
            shipRepository.save(new Ship("Patrolboat", gpone, location3));
            shipRepository.save(new Ship("Destroyer", gptwo, location4));
            shipRepository.save(new Ship("Patrolboat", gptwo, location5));

            shipRepository.save(new Ship("Destroyer",gpthree,location6));
            shipRepository.save(new Ship("Patrolboat",gpthree,location7));
            shipRepository.save(new Ship("Submarine",gpfour,location8));
            shipRepository.save(new Ship("Patrolboat",gpfour,location9));

            shipRepository.save(new Ship("Destroyer",gpfive,location10));
            shipRepository.save(new Ship("Patrolboat",gpfive,location11));
            shipRepository.save(new Ship("Submarine",gpsix,location12));
            shipRepository.save(new Ship("Patrolboat",gpsix,location13));

            shipRepository.save(new Ship("Destroyer",gpeight,location14));
            shipRepository.save(new Ship("Patrolboat",gpeight,location15));
            shipRepository.save(new Ship("Submarine",gpseven,location16));
            shipRepository.save(new Ship("Patrolboat",gpseven,location17));

            shipRepository.save(new Ship("Destroyer",gpnine,location18));
            shipRepository.save(new Ship("Patrolboat",gpnine,location19));
            shipRepository.save(new Ship("Submarine",gpten,location20));
            shipRepository.save(new Ship("Patrolboat",gpten,location21));
            //game #1
            List<String> salvolocation1 = Arrays.asList("B5","C5","F1");
            List<String> salvolocation2 = Arrays.asList("B4","B5","B6");
            List<String> salvolocation3 = Arrays.asList("F2","D5");
            List<String> salvolocation4 = Arrays.asList("E1","H3","A2");
            //game #2
            List<String> salvolocation5 = Arrays.asList("A2","A4","G6");
            List<String> salvolocation6 = Arrays.asList("B5","D5","C7");
            List<String> salvolocation7 = Arrays.asList("A3","H6");
            List<String> salvolocation8 = Arrays.asList("C5","C6");
            //game #3
            List<String> salvolocation9 = Arrays.asList("G6","H6","A4");
            List<String> salvolocation10 = Arrays.asList("H1","H2","H3");
            List<String> salvolocation11 = Arrays.asList("A2","A3","D8");
            List<String> salvolocation12 = Arrays.asList("E1","F2","G3");
            //game #4
            List<String> salvolocation13 = Arrays.asList("A3","A4","F7");
            List<String> salvolocation14 = Arrays.asList("B5","B6","H1");
            List<String> salvolocation15 = Arrays.asList("A2","G6","H6");
            List<String> salvolocation16 = Arrays.asList("C5","C7","D5");
            //game #5
            List<String> salvolocation17 = Arrays.asList("A1","A2","A3");
            List<String> salvolocation18 = Arrays.asList("B5","B6","C7");
            List<String> salvolocation19 = Arrays.asList("G6","G7","G8");
            List<String> salvolocation20 = Arrays.asList("C6","D6","E6");
            List<String> salvolocation21 = Arrays.asList("H1","H8");

            Salvo game1turn1salvo1 =new Salvo (1,gpone,salvolocation1);
            salvoRepository.save(game1turn1salvo1);
            Salvo game1turn1salvo2 = new Salvo (1,gptwo,salvolocation2);
            salvoRepository.save(game1turn1salvo2);
            Salvo game1turn2salvo1 = new Salvo (2,gpone,salvolocation3);
            salvoRepository.save(game1turn2salvo1);
            Salvo game1turn2salvo2 = new Salvo (2,gptwo,salvolocation4);
            salvoRepository.save(game1turn2salvo2);


            scoreRepository.save(new Score(gone,pone, score_winner));
            scoreRepository.save(new Score(gtwo,pone, score_tie));
            scoreRepository.save(new Score(gtwo,ptwo, score_tie));
            scoreRepository.save(new Score(gthree,ptwo,score_winner));
            scoreRepository.save(new Score(gfour,ptwo,score_tie));
            scoreRepository.save(new Score(gfour,pone,score_tie));

        };

    }

}