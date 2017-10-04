package de.benja.battleship;

import de.benja.battleship.Game.Game;
import de.benja.battleship.Game.GameRepository;
import de.benja.battleship.GamePlayer.GamePlayer;
import de.benja.battleship.GamePlayer.GamePlayerRepository;
import de.benja.battleship.Player.Player;
import de.benja.battleship.Player.PlayerRepository;
import de.benja.battleship.Score.Score;
import de.benja.battleship.Ship.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @RequestMapping("/games")
    //creating method that returns something like a list containing hashmaps
    public List<HashMap> getGames() {
        //task 4
        /*ArrayList<Long> gameids = new ArrayList<Long>();

        List<Game> games = gameRepository.findAll();
        games.forEach(game -> {
            gameids.add(game.getId());
        });
        return gameids;*/
        //Task 5 create game data objects (creating array with objects containing key value pairs)
        List<Game> allGames = gameRepository.findAll();
        ArrayList<HashMap> gameArray = new ArrayList();
        allGames.forEach(game -> {
            HashMap<String,Object> gameMap = new HashMap();
            gameMap.put("id",game.getId());
            gameMap.put("date",game.getDate());


            Set<GamePlayer> gamePlayers = game.getGamePlayers();
            List<Map> gamePlayerList = new ArrayList<Map>();

            gamePlayers.forEach(gamePlayer -> {
                HashMap<String,Object> gamePlayerMap = new HashMap<String,Object>();
                gamePlayerMap.put("id", gamePlayer.getId());

                Player player = gamePlayer.getPlayer();

                HashMap<String, Object> playerMap = new HashMap<String, Object>();
                playerMap.put("id", player.getId());
                playerMap.put("email", player.getUserName());

                Score score = gamePlayer.getScore();
                playerMap.put("score",score);

                gamePlayerMap.put("player",playerMap);

                gamePlayerList.add(gamePlayerMap);

            });

            gameMap.put("gameplayers",gamePlayerList);
            gameArray.add(gameMap);
        });

        return gameArray;
    }

    @RequestMapping("/fullgames")
    public List<Game> getFullGames() {
        return gameRepository.findAll();
    }
    // and this just for fun...
    @RequestMapping("/fullplayers")
    public List<Player> getFullPlayers() {
        return playerRepository.findAll();
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String,Object> getGameView(@PathVariable Long gamePlayerId) {
        //reference gameplay found by gameplayerId in URL
        GamePlayer currentGamePlayer = gamePlayerRepository.findOne(gamePlayerId);
        //get Game (method of class GamePlayer) of current
        Game currentGame = currentGamePlayer.getGame();

        Map<String,Object> response = new HashMap<>();
        response.put("id", currentGame.getId());
        response.put("created", currentGame.getDate());
        response.put("gameplayers", currentGame.getGamePlayers());
        response.put("ships", currentGamePlayer.getShips());

        Map<String,Object> salvos = new HashMap<>();

        currentGame.getGamePlayers().forEach(gamePlayer -> {
            salvos.put(String.valueOf(gamePlayer.getId()),gamePlayer.getSalvoes());
        });

        response.put("salvoes", salvos);

        return response;
    }

    @RequestMapping("/players")
    public List<Map> getPlayers() {
        List<Map> returnArray = new ArrayList<Map>();
        List<Player> players = playerRepository.findAll();

        players.forEach( player -> {
            HashMap<String,Object> playermap = new HashMap<>();
            playermap.put( "totalScore" , player.getTotalScore());
            playermap.put("playerid", player.getId());
            playermap.put("username", player.getUserName());

            playermap.put("wins",player.countWins());
            playermap.put("ties",player.countTies());
            playermap.put("lost",player.countLoss());

            returnArray.add(playermap);
        });

        return returnArray;
    };
}

