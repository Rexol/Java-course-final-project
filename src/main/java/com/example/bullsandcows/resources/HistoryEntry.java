package com.example.bullsandcows.resources;

public class HistoryEntry {
    private final String guess;
    private final GuessResult result;

    public HistoryEntry(String guess, GuessResult result) {
        this.guess = guess;
        this.result = result;
    }

    public String getGuess() {
        return this.guess;
    }

    public GuessResult getResult() {
        return this.result;
    }
}
