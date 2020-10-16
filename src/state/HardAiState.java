package state;

import gamelogic.Guess;
import gamelogic.Score;
import gfx.Assets;
import ui.UIButton;
import utils.Constants;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HardAiState extends GameState {

    //display
    private BufferedImage[] currentPegs;

    //guess and score
    private int numberOfGuesses;
    private String lastGuess;
    private Score lastScore;
    private int blackPegCount, whitePegCount;

    //ai
    private ArrayList<String> possibleAnswers;
    private HashMap<String, HashMap<String, Score>> allScores, possibleScores;

    public HardAiState(){
        super();
        currentPegs = new BufferedImage[4];
        uiManager.addUIButton(new UIButton(150, 150, 30, 30, Assets.peg_black, this::incrementBlackPegs));
        uiManager.addUIButton(new UIButton(200, 150, 30, 30, Assets.peg_white, this::incrementWhitePegs));
        uiManager.addUIButton(new UIButton(150, 200, 30, 30, Assets.yes, this::confirmScore));
        uiManager.addUIButton(new UIButton(200, 200, 30, 30, Assets.no, this::removeScore));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void start() {
        removeScore();
        numberOfGuesses = 0;
        lastScore = null;
        lastGuess = null;

        possibleAnswers = (ArrayList<String>) Constants.allStringCombinations.clone();
        allScores = new HashMap<>();
        possibleScores = new HashMap<>();
        for (Map.Entry<String, HashMap<String, Score>> entry : Constants.possibleScores.entrySet()) {
            allScores.put(entry.getKey(), new HashMap<>(entry.getValue()));
            possibleScores.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }

        int[] guess = makeGuesses();
        lastGuess = Utils.ColourCombination.toStringColour(guess);
        addAllColourImages(panel, toColour(guess));
    }

    @Override
    protected void postRender(Graphics graphics) {
        for(int i = 0; i < currentPegs.length; i++){
            if(currentPegs[i] != null)
                graphics.drawImage(currentPegs[i], 100+50*i, 250, 30, 30, null);
        }
    }

    private BufferedImage[] toColour(int[] guess){
        BufferedImage[] colours = new BufferedImage[4];
        for(int i = 0; i < 4; i++){
            colours[i] = Assets.colours[guess[i]];
        }
        return colours;
    }

    private void confirmScore(){
        addAllColourImages(allPegs, currentPegs);
        lastScore = new Score(blackPegCount, whitePegCount);
        removeScore();
        numberOfGuesses ++;
        if(lastScore.isDecoded()){
            System.out.println("AI won");
            return;
        }else if(numberOfGuesses > 10){
            System.out.println("AI lose");
            return;
        }
        int[] guess = makeGuesses();
        lastGuess = Utils.ColourCombination.toStringColour(guess);
        addAllColourImages(panel, toColour(guess));
    }

    private void removeScore(){
        blackPegCount = 0;
        whitePegCount = 0;
        currentPegs = new BufferedImage[4];
    }

    private void incrementBlackPegs(){
//        System.out.println("called");
//        System.out.println(currentPegs.length);
        for(int i = 0; i < 4; i++){
            if(currentPegs[i] == null){
                blackPegCount++;
                currentPegs[i] = Assets.peg_black;
                return;
            }
        }
    }

    private void incrementWhitePegs(){
        for(int i = 0; i < 4; i++){
            if(currentPegs[i] == null){
                whitePegCount++;
                currentPegs[i] = Assets.peg_white;
                return;
            }
        }
    }

    private int[] makeGuesses(){
        if(numberOfGuesses == 0){
            return new int[]{0,0,1,1};
        }
        for(int i = 0; i < possibleAnswers.size(); i++){
            if(!allScores.get(lastGuess).get(possibleAnswers.get(i)).equals(lastScore)){
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

    private void addAllColourImages(BufferedImage[][] target, BufferedImage[] source){
        target[numberOfGuesses] = source;
    }

}
