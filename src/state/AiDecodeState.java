package state;

import ai.AI;
import gamelogic.Score;
import gfx.Assets;
import ui.UIButton;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AiDecodeState extends GameState {

    private BufferedImage[] currentPegs;
    private int blackPegCount, whitePegCount;

    private int numberOfGuesses;
    private String lastGuess;
    private Score lastScore;
    private AI ai;

    public AiDecodeState(){
        super();
        currentPegs = new BufferedImage[4];
        uiManager.addUIButton(new UIButton(150, 150, 30, 30, Assets.peg_black, this::incrementBlackPegs));
        uiManager.addUIButton(new UIButton(200, 150, 30, 30, Assets.peg_white, this::incrementWhitePegs));
        uiManager.addUIButton(new UIButton(150, 200, 30, 30, Assets.yes, this::confirmScore));
        uiManager.addUIButton(new UIButton(200, 200, 30, 30, Assets.no, this::removeScore));
    }

    @Override
    protected void start() {
        removeScore();
        numberOfGuesses = 0;
        lastScore = null;
        lastGuess = null;
        ai = new AI();
        int[] guess = ai.makeGuesses(true, lastGuess, lastScore);
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
        }
        int[] guess = ai.makeGuesses(false, lastGuess, lastScore);
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

    private void addAllColourImages(BufferedImage[][] target, BufferedImage[] source){
        target[numberOfGuesses] = source;
    }

}
