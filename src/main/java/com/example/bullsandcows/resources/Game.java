package com.example.bullsandcows.resources;

import java.util.ArrayList;

public class Game {
    private final long id;
    private final Player player;
    private final String number;
    private final int wordSize;
    private ArrayList<HistoryEntry> guessHistory;
    private boolean active;

    public Game(long l, Player player, int wordSize) {
        this.id = l;
        this.player = player;
        this.wordSize = wordSize;
        this.number = Integer.toString((int) (Math.random() * Math.pow(10, wordSize)));
        this.guessHistory = new ArrayList<HistoryEntry>();
        this.active = true;
        this.player.incrementTotalGames();
    }

    public long getId() {
        return this.id;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getNumber() {
        return this.number;
    }

    public int getWordSize() {
        return this.wordSize;
    }

    public ArrayList<HistoryEntry> getGuessHistory() {
        return this.guessHistory;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isGuessValid(String guess) {
        try {
            Integer.parseInt(guess);
        } catch (NumberFormatException e) {
            return false;
        }
        return guess.length() == this.wordSize;
    }

    public GuessResult guess(String guess) {
        if (!this.active || !this.isGuessValid(guess)) {
            return GuessResult.getInvalidGuessResult();
        }
        int bulls = 0;
        int cows = 0;

        int[] numberCount = new int[10];
        int[] guessCount = new int[10];

        for (int i = 0; i < this.wordSize; ++i) {
            char numberChar = this.number.charAt(i);
            char guessChar = guess.charAt(i);

            if (numberChar == guessChar) {
                bulls += 1;
            } else {
                numberCount[numberChar - '0'] += 1;
                guessCount[guessChar - '0'] += 1;
            }
        }
        for (int i = 0; i < 10; ++i) {
            cows += Math.min(guessCount[i], numberCount[i]);
        }
        GuessResult res = new GuessResult(bulls, cows);

        this.addEntryToHistory(new HistoryEntry(guess, res));
        this.checkAnswer(guess);

        return res;
    }

    public String giveUp() {
        this.active = false;
        return this.number;
    }

    private void checkAnswer(String guess) {
        if (guess.equals(this.number)) {
            this.active = false;
            this.player.incrementWonGames();
        }
    }

    private void addEntryToHistory(HistoryEntry entry) {
        this.guessHistory.add(entry);
    }
}
