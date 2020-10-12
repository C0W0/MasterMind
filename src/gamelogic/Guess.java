package gamelogic;

import java.util.ArrayList;

public class Guess {

    private int worstCase;
    private boolean guessIsImpossible;
    private int[] guess;

    public Guess(int worstCase, boolean guessIsImpossible, int[] guess){
        this.worstCase = worstCase;
        this.guessIsImpossible = guessIsImpossible;
        this.guess = guess;
    }

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

    public boolean isGuessImpossible() {
        return guessIsImpossible;
    }

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
//        System.out.println(minimumGuess.worseCase+" "+minimumGuess.guessIsImpossible+" "+Arrays.toString(minimumGuess.guesses));
        return minimumGuess.guess;
    }
}
