package com.example.bullsandcows.resources;

public class GuessResult {
    private final int bulls;
    private final int cows;

    public static GuessResult getInvalidGuessResult() {
        return new GuessResult(-1, -1);
    }

    public GuessResult(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }

    public GuessResult() {
        this(0, 0);
    }

    public int getBulls() {
        return this.bulls;
    }

    public int getCows() {
        return this.cows;
    }

    public boolean equals(GuessResult res) {
        if (res == this) {
            return true;
        }
        if (this.cows == res.getCows() && this.bulls == this.getBulls()) {
            return true;
        }

        return false;
    }

    public String toString() {
        return "Bulls: " + this.bulls + " cows: " + this.cows;
    }
}
