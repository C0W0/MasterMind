package state;

import ai.AI;
import gamelogic.Score;
import gfx.Assets;
import ui.UIButton;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AiDecodeState extends State {

    private BufferedImage[][] panel, allPegs;
    private BufferedImage[] currentPegs;
    private int blackPegCount, whitePegCount;

    private int numberOfGuesses;
    private String lastGuess;
    private Score lastScore;
    private AI ai;

    public AiDecodeState(){
        super();
        panel = new BufferedImage[10][4];
        currentPegs = new BufferedImage[4];
        allPegs = new BufferedImage[20][2];
        uiManager.addUIButton(new UIButton(150, 150, 30, 30, Assets.peg_black, this::incrementBlackPegs));
        uiManager.addUIButton(new UIButton(200, 150, 30, 30, Assets.peg_white, this::incrementWhitePegs));
        uiManager.addUIButton(new UIButton(150, 200, 30, 30, Assets.yes, this::confirmScore));
        uiManager.addUIButton(new UIButton(200, 200, 30, 30, Assets.no, this::removeScore));
        uiManager.addUIButton(new UIButton(150, 600, 80, 80, Assets.yes, this::init));
    }

    @Override
    public void init() {
        panel = new BufferedImage[10][4];
        currentPegs = new BufferedImage[4];
        allPegs = new BufferedImage[20][2];
        blackPegCount = 0;
        whitePegCount = 0;
        numberOfGuesses = 0;
        removeScore();
        lastScore = null;
        lastGuess = null;
        ai = new AI();
        int[] guess = ai.makeGuesses(true, lastGuess, lastScore);
        lastGuess = Utils.ColourCombination.toStringColour(guess);
        addAllColourImages(panel, toColour(guess));
    }

    @Override
    public void render(Graphics graphics) {
        uiManager.render(graphics);
        for(int y = 0; y < panel.length; y++){
            for(int x = 0; x < panel[y].length; x++){
                if(panel[y][x] != null)
                    graphics.drawImage(panel[y][x], 500+50*x, 150+60*y, 30, 30, null);
            }
        }

        for(int y = 0; y < allPegs.length; y++){
            for(int x = 0; x < allPegs[y].length; x++){
                if(allPegs[y][x] != null){
                    graphics.drawImage(allPegs[y][x], 710+30*x, 150+60*y, 15, 15, null);
                }
            }
        }

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
        blackPegCount++;
//        System.out.println("called");
//        System.out.println(currentPegs.length);
        for(int i = 0; i < 4; i++){
            if(currentPegs[i] == null){
                currentPegs[i] = Assets.peg_black;
                return;
            }
        }
    }

    private void incrementWhitePegs(){
        whitePegCount++;
        for(int i = 0; i < 4; i++){
            if(currentPegs[i] == null){
                currentPegs[i] = Assets.peg_white;
                return;
            }
        }
    }

    private void addAllColourImages(BufferedImage[][] target, BufferedImage[] source){
        target[numberOfGuesses] = source;
    }


}
