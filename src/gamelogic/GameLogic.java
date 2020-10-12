package gamelogic;

import ai.AI;
import utils.DataHandler;
import utils.Utils.ColourCombination;

import java.util.Arrays;

public class GameLogic {


    private int numberOfGuesses;
    private String lastGuess;
    private Score lastScore;
    private int[] code;
    private AI ai;

    public GameLogic(){
        numberOfGuesses = 0;
        code = new int[]{4, 3, 2, 5};
        ai = new AI(new DataHandler(this));
    }

    public void play(){
        while (numberOfGuesses < 10){
            int[] guess = ai.makeGuesses();
            lastGuess = ColourCombination.toStringColour(guess);
            numberOfGuesses ++;
            lastScore = ColourCombination.match(code, guess);
            if(lastScore.isDecoded()){
                System.out.println(Arrays.toString(guess));
                System.out.println("Decoded");
                System.out.println(numberOfGuesses);
                break;
            }

        }
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public Score getLastScore() {
        return lastScore;
    }

    public String getLastGuess() {
        return lastGuess;
    }
}
