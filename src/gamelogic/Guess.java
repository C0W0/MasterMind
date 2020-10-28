/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 4, 2020
Java 13.0.2
The objects created from this class assists the decision making process of Ai
A Guess object associates the worstCase of each guess, whether the guess is a
possible answer, and the guess itself together, in order to compare the
guesses and choose an optimal one

List of Global Variables:
worstCase - how many possible answer this guess may lead to </type int>
guessIsImpossible - is the guess a possible answer </type boolean>
guess - the guess itself </type int[]>
===============================================================================
*/

package gamelogic;

import java.util.ArrayList;

public class Guess {

    private int worstCase;
    private boolean guessIsImpossible;
    private int[] guess;

    /**Guess method
     * The constructor method of the Guess class
     *
     * @param worseCase - how many possible answer this guess may lead to </type int>
     * @param guessIsImpossible - is the guess a possible answer </type boolean>
     * @param guess - the guess as a String </type String>
     */
    public Guess(int worseCase, boolean guessIsImpossible, String guess){
        this.worstCase = worseCase;
        this.guessIsImpossible = guessIsImpossible;
        this.guess = new int[4];
        for(int i = 0; i < 4; i++){
            switch (guess.charAt(i)) {
                case 'B':
                    this.guess[i] = 0;
                    break;
                case 'G':
                    this.guess[i] = 1;
                    break;
                case 'O':
                    this.guess[i] = 2;
                    break;
                case 'P':
                    this.guess[i] = 3;
                    break;
                case 'R':
                    this.guess[i] = 4;
                    break;
                case 'Y':
                    this.guess[i] = 5;
                    break;
            }
        }
    }

    /**getMinimumGuess method
     * This functional method compares all possible guesses and returns the optimal one
     *
     * List of Local Variables:
     * minimumGuess - the optimal guess being updated by searching through the
     *      ArrayList </type Guess>
     *
     * @param guesses - an ArrayList of guesses </type ArrayList>
     * @return minimumGuess.guess - the optimal guess </type int[]>
     */
    public static int[] getMinimumGuess(ArrayList<Guess> guesses){
        Guess minimumGuess = guesses.get(0);
        for(int i = 1; i < guesses.size(); i++){
            if(guesses.get(i).worstCase < minimumGuess.worstCase){
                minimumGuess = guesses.get(i);
            }else if(guesses.get(i).worstCase == minimumGuess.worstCase){
                if(minimumGuess.guessIsImpossible && !guesses.get(i).guessIsImpossible)
                    minimumGuess = guesses.get(i);
                else if(minimumGuess.guessIsImpossible == guesses.get(i).guessIsImpossible)
                    minimumGuess = guesses.get(i);
            }
        }
        return minimumGuess.guess;
    }
}
