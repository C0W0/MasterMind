package state;

import gamelogic.Game;
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
    private String lastGuess;
    private Score lastScore;
    private int blackPegCount, whitePegCount;

    //ai
    private ArrayList<String> possibleAnswers;
    private HashMap<String, HashMap<String, Score>> allScores, possibleScores;

    public HardAiState(Game game){
        super(game);
        currentPegs = new BufferedImage[4];
        uiManager.addUIButton(new UIButton(90, 270, 55, 55, Assets.blackPegButton, this::incrementBlackPegs));
        uiManager.addUIButton(new UIButton(155, 270, 55, 55, Assets.whitePegButton, this::incrementWhitePegs));
        uiManager.addUIButton(new UIButton(90, 350, 55, 150, Assets.confirmButton, this::confirmScore));
        uiManager.addUIButton(new UIButton(265, 350, 55, 150, Assets.deleteButton, this::removeScore));

        uiManager.addUIButton(new UIButton(30, 648, 90, 90, Assets.backButton, ()->game.setState(State.states[2])));
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
        lastGuess = Utils.toStringColour(guess);
        panel[numberOfGuesses] = toColour(guess);
    }

    @Override
    protected void postRender(Graphics graphics) {
        graphics.drawImage(Assets.aiGameboard, 0, 0, cornerWidth, cornerHeight, null);
        for(int i = 0; i < currentPegs.length; i++)
            if(currentPegs[i] != null)
                graphics.drawImage(currentPegs[i], 235+45*i, 275, 40, 40, null);
        
        if(!isGameActive) {
             graphics.drawImage(Assets.codebreakerWins, 80, 460, 380, 90, null);
             graphics.drawImage(Assets.numberButtons[numberOfGuesses], 229, 509, 35, 35, null);
    	}
    }

    @Override
    protected void messageRender(Graphics graphics) {

    }

    private void confirmScore(){
        if(!isGameActive)
            return;
        allPegs[numberOfGuesses] = currentPegs;
        lastScore = new Score(blackPegCount, whitePegCount);
        removeScore();
        numberOfGuesses ++;
        if(lastScore.isDecoded()){
            showCode(Utils.toIntArrayColour(lastGuess));
            isGameActive = false;
            return;
        }else if(numberOfGuesses > maxGuess){
            System.out.println("AI lose");
            isGameActive = false;
            return;
        }
        int[] guess = makeGuesses();
        lastGuess = Utils.toStringColour(guess);
        panel[numberOfGuesses] = toColour(guess);
    }

    private void removeScore(){
        blackPegCount = 0;
        whitePegCount = 0;
        currentPegs = new BufferedImage[4];
    }

    private void incrementBlackPegs(){
        if(isGameActive)
            for(int i = 0; i < 4; i++){
                if(currentPegs[i] == null){
                    blackPegCount++;
                    currentPegs[i] = Assets.pegBlack;
                    return;
                }
            }
    }

    private void incrementWhitePegs(){
        if(isGameActive)
            for(int i = 0; i < 4; i++){
                if(currentPegs[i] == null){
                    whitePegCount++;
                    currentPegs[i] = Assets.pegWhite;
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

}
