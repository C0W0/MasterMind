package utils;

import gamelogic.GameLogic;
import gamelogic.Score;

public class DataHandler {

    private GameLogic gameLogic;

    public DataHandler(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public int getNumberOfGuesses() {
        return gameLogic.getNumberOfGuesses();
    }

    public Score getLastScore() {
        return gameLogic.getLastScore();
    }

    public String getLastGuess() {
        return gameLogic.getLastGuess();
    }

}
