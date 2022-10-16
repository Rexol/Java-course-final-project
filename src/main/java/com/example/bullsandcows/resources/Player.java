package com.example.bullsandcows.resources;

public class Player {
    private final String name;
    private int totalGames;
    private int wonGames;

    public Player(String name) {
        this(name, 0, 0);
    }

    public Player(String name, int total, int won) {
        this.name = name;
        this.totalGames = total;
        this.wonGames = won;
    }

    public String getName() {
        return this.name;
    }

    public int getTotalGames() {
        return this.totalGames;
    }

    public int getWonGames() {
        return this.wonGames;
    }

    public void incrementTotalGames() {
        this.totalGames += 1;
    }

    public void incrementWonGames() {
        this.wonGames += 1;
    }
}
