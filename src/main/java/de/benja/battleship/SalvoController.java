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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

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

    private Player getAuthPlayer(Authentication authentication) {
        List<Player> players = playerRepository.findByUserName(authentication.getName());
        return players.stream().findFirst().orElse(null);
    }

    //Controller Definition from module Java I, gonna be changed in Java II to send all the games of the currently logged in user
    @RequestMapping("/games")
    //Authentication object as optional parameter of the method getGames()
    public Map getGames(Authentication authentication) {
        //DRY!!! thats why getauthplayer method
        //List<Player> players = playerRepository.findByUserName(authentication.getName());
        //System.out.println(players);

        Player authPlayer = getAuthPlayer(authentication);
        //specifies the kind of map which is returned by the method getGames(), in this case its an HashMap
        HashMap returnMap = new HashMap();

        ArrayList<HashMap> playerArray = new ArrayList();

        HashMap<String, Object> playerMap = new HashMap<String, Object>();
        //the same as playerMap.put("id", players.get(0).getId());
        playerMap.put("id", authPlayer.getId());
        playerMap.put("email", authPlayer.getUserName());
        //get all Games, so its possible, that currently logged in user can join other games
        List<Game> allGames = gameRepository.findAll();
        ArrayList<HashMap> gameArray = new ArrayList();
        //looping thru allGames with one game as an iterator
        allGames.forEach(game -> {
            HashMap<String,Object> gameMap = new HashMap();
            gameMap.put("id",game.getId());
            gameMap.put("date",game.getDate());


            Set<GamePlayer> gamePlayers = game.getGamePlayers();
            List<Map> gamePlayerList = new ArrayList<Map>();
            // for each gamePlayer a HashMap is created
            gamePlayers.forEach(gamePlayer -> {
                HashMap<String,Object> gamePlayerMap = new HashMap<String,Object>();
                gamePlayerMap.put("id", gamePlayer.getId());

                Player player = gamePlayer.getPlayer();

                HashMap<String, Object> gameplayerPlayerMap = new HashMap<String, Object>();
                gameplayerPlayerMap.put("id", player.getId());
                gameplayerPlayerMap.put("email", player.getUserName());

                Score score = gamePlayer.getScore();
                gameplayerPlayerMap.put("score",score);

                gamePlayerMap.put("player",gameplayerPlayerMap);

                gamePlayerList.add(gamePlayerMap);

            });

            gameMap.put("gameplayers",gamePlayerList);

            playerArray.add(gameMap);
            gameArray.add(gameMap);
        });
        playerArray.add(playerMap);
        returnMap.put("player",playerMap);
        returnMap.put("games",gameArray);
        return returnMap;
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

    @RequestMapping(path = "/players", method = RequestMethod.GET)
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

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createPlayer(@RequestParam String userName, String password) {
        if (userName.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No name"), HttpStatus.FORBIDDEN);
        }
        Player player = playerRepository.findOneByUserName(userName);
        if (player != null) {
            return new ResponseEntity<>(makeMap("error", "No such user"), HttpStatus.CONFLICT);
        }
        player = playerRepository.save(new Player(userName,password));
        return new ResponseEntity<>(makeMap("id", player.getId()) , HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

}

