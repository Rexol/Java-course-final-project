# Bools and cows game.

## What is it

This application was implemented using spring boot as java cource final project. It is a pen-and-pencil game that you can play using http requests.

## Rules:

The rules are simple - you need to guess 4 digit (if you hasn't specified other amount) number. You can make your guesses and if your guess has one digit as in secret number, then you get +1 cow, if it has the same position then you have +1 bull instead.

## API endpoints

* `GET:/help` returns rules of the game
* `POST:/addPlayer?name=` creates new player with given name into game
* `GET:/player?name=` returns information about player with given name
* `POST:/newGame?name=&wordSize` creates new game for player with given name and number size (4 if not specified)
* `GET:/game?name=` returns information about active game of player with given name (including secret number for ease of testing)
* `PUT:/guess?name=&guess=` make guess for active player's game. Will return amount of bulls and cows for guess
* `GET:/finished?name=` returns if last active game of player with given name is finished (was moved into other endpoint because it will make more sence if implementing front-end to get boolean type from another endpoint rather then from the same where guesses made to)
* `POST:/giveup?name=` returns correct answer for player's active game and finishes this game
