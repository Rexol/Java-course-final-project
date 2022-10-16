package com.example.bullsandcows;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bullsandcows.exceptions.ControllerException;
import com.example.bullsandcows.resources.ErrorResponse;
import com.example.bullsandcows.resources.Game;
import com.example.bullsandcows.resources.GuessResult;
import com.example.bullsandcows.resources.Player;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class GameController {
    private HashMap<String, Player> users;
    private HashMap<String, Game> activeGames;
    private ArrayList<Game> playedGames;
    private final AtomicLong counter = new AtomicLong();

    public GameController() {
        this.users = new HashMap<>();
        this.activeGames = new HashMap<>();
        this.playedGames = new ArrayList<>();
    }

    @ExceptionHandler(ControllerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleNoRecordFoundException(ControllerException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @GetMapping("/help")
    public String getHelp() {
        return "Hello, this is a bulls and cows game. The rules are simple - you need to guess 4 digit (if you hasn't specified other) number. You can make your guesses and if your guess has one digit as in secret number, then you get +1 cow, if it has the same position then you have +1 bull instead.";
    }

    @PostMapping("/addPlayer")
    public boolean addNewPlayer(@RequestParam(value = "name") String name) throws ControllerException {
        if (this.users.containsKey(name)) {
            throw new ControllerException("Player " + name + " already exists");
        }
        this.users.put(name, new Player(name));

        return true;
    }

    @PostMapping("/newGame")
    public boolean newGame(@RequestParam(value = "name") String name,
            @RequestParam(value = "wordSize", defaultValue = "4") int wordSize) throws ControllerException {
        this.validateUser(name);
        if (this.activeGames.containsKey(name)) {
            if (this.activeGames.get(name).isActive()) {
                throw new ControllerException("Player " + name + " already have an active game");
            }
            this.playedGames.add(this.activeGames.get(name));
            this.activeGames.remove(name);
        }
        this.activeGames.put(name, new Game(counter.incrementAndGet(), this.users.get(name), wordSize));

        return true;
    }

    @PutMapping("/guess")
    public GuessResult makeGuess(@RequestParam(value = "name") String name, @RequestParam(value = "guess") String guess)
            throws ControllerException {
        this.validateGame(name);
        Game game = this.activeGames.get(name);

        if (!game.isGuessValid(guess)) {
            throw new ControllerException("Your guess should have " + game.getWordSize() + " digits");
        }

        GuessResult result = game.guess(guess);
        if (result.equals(GuessResult.getInvalidGuessResult())) {
            throw new ControllerException("Your guess is invalid");
        }

        return result;
    }

    @GetMapping("/player")
    public Player getPlayer(@RequestParam(value = "name") String name) throws ControllerException {
        this.validateUser(name);

        return this.users.get(name);
    }

    @GetMapping("/game")
    public Game getGame(@RequestParam(value = "name") String name) throws ControllerException {
        this.validateGame(name);
        return this.activeGames.get(name);
    }

    @GetMapping("/finished")
    public boolean isGameFinished(@RequestParam(value = "name") String name) throws ControllerException {
        this.validateUser(name);
        if (!this.activeGames.containsKey(name)) {
            throw new ControllerException("Player " + name + " hasn't played any game");
        }
        return !this.activeGames.get(name).isActive();
    }

    @PostMapping("/giveup")
    public String giveUp(@RequestParam(value = "name") String name) throws ControllerException {
        this.validateGame(name);

        Game game = this.activeGames.get(name);
        return "The answer was " + game.giveUp();
    }

    private void validateUser(String name) throws ControllerException {
        if (!this.users.containsKey(name)) {
            throw new ControllerException("Player " + name + " does not exists");
        }
    }

    private void validateGame(String name) throws ControllerException {
        this.validateUser(name);
        if (!this.activeGames.containsKey(name)) {
            throw new ControllerException("Player " + name + " does not have an active game");
        } else if (!this.activeGames.get(name).isActive()) {
            throw new ControllerException("Player " + name + " has finished game. You can create new one");
        }
    }

}
