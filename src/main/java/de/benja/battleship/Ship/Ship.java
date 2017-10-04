package de.benja.battleship.Ship;

import de.benja.battleship.GamePlayer.GamePlayer;

import javax.persistence.*;
import java.util.*;

@Entity
public class Ship {



    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String shiptype;
    @ElementCollection
    @Column(name="locations")
    private List<String> locations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    public Ship() {

    }

    public Ship (String shiptype, GamePlayer gamePlayer, List locations) {
        this.shiptype = shiptype;
        this.gamePlayer = gamePlayer;
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public String getShiptype() {
        return shiptype;
    }

    public void setShiptype(String shiptype) {
        this.shiptype = shiptype;
    }



    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }



    public List getLocations() {
        return locations;
    }

    public void setLocations(List locations) {
        this.locations = locations;
    }


}
