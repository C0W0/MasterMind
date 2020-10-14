package ai;

import gamelogic.Guess;
import gamelogic.Score;
import utils.Constants;

import java.util.*;

public class AI {

    private ArrayList<String> possibleAnswers;
    private HashMap<String, HashMap<String, Score>> allScores, possibleScores;


    @SuppressWarnings("unchecked")
    public AI(){
        possibleAnswers = (ArrayList<String>) Constants.allStringCombinations.clone();
        allScores = (HashMap<String, HashMap<String, Score>>) Constants.possibleScores.clone();
        possibleScores = (HashMap<String, HashMap<String, Score>>) Constants.possibleScores.clone();
    }

    public int[] makeGuesses(boolean isInitialGuess, String LastGuess, Score lastScore){
        if(isInitialGuess){
            return new int[]{0,0,1,1};
        }
        for(int i = 0; i < possibleAnswers.size(); i++){
            if(!allScores.get(LastGuess).get(possibleAnswers.get(i)).equals(lastScore)){
                possibleAnswers.remove(i);
                i --; //remove impossible answers from possibleAnswers
            }
        }

        ArrayList<Guess> guesses = new ArrayList<>();
        for(Map.Entry<String, HashMap<String, Score>> entry: possibleScores.entrySet()){
            Iterator<Map.Entry<String, Score>> it = entry.getValue().entrySet().iterator();
            while (it.hasNext()){
                if(!possibleAnswers.contains(it.next().getKey()))
                    it.remove();
            }
            //removes any non-potential answer from possibleScores

            it = entry.getValue().entrySet().iterator();
            HashMap<String, Integer> allPossibleScores = new HashMap<>();
            while (it.hasNext()){
                allPossibleScores.merge(it.next().getValue().toString(), 1, Integer::sum);
            }
            int worst = 0;
            for(Map.Entry<String, Integer> possibleScoreEntry: allPossibleScores.entrySet())
                worst = Math.max(worst, possibleScoreEntry.getValue());
            boolean guessIsImpossible = !possibleAnswers.contains(entry.getKey());
            Guess guess = new Guess(worst, guessIsImpossible, entry.getKey());
            guesses.add(guess);

        }
        return Guess.getMinimumGuess(guesses);
    }

}
