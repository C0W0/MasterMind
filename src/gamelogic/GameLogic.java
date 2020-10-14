package gamelogic;

import ai.AI;
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
        code = new int[]{2, 4, 1, 0};
        ai = new AI();
    }

    public void play(){
        while (numberOfGuesses < 10){
            int[] guess = ai.makeGuesses(numberOfGuesses == 0, lastGuess, lastScore);
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
