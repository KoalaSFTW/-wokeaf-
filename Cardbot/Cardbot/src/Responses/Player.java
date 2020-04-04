package Responses;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private String id;
    private int coins;
    private List<Card> cardsList;
    private int wins;
    private int losses;
    private double elo;
    private int winStreak;

    public Player(String name, String id, int coins) {
        this.name = name;
        this.id = id;
        this.coins = coins;
        this.cardsList = new ArrayList<Card>();
        this.wins = 0;
        this.losses =0;
        this.elo = 500.00;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public List<Card> getCardsList() {
        return cardsList;
    }

    public void setCardsList(List<Card> cardsList) {
        this.cardsList = cardsList;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getElo() {
        return elo;
    }

    public void setElo(double elo) {
        this.elo = elo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWinStreak() {
        return winStreak;
    }

    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
    }
}
