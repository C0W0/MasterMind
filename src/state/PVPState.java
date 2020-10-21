package state;

import gfx.Assets;
import ui.UIButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PVPState extends GameState {


    private BufferedImage[] currentPegs, guessImages;
    private int[] currentGuess;
    private int blackPegCount, whitePegCount, numberOfGuessColour;
    private int numberOfGuesses;
    private boolean isDecoding;


    public PVPState(){
        super();
        isDecoding = true;
        currentPegs = new BufferedImage[4];
        guessImages = new BufferedImage[4];
        currentGuess = new int[4];

        for(int i = 0; i < 6; i++) {
            int colour = i;
            uiManager.addUIButton(new UIButton(100+55*i, 180, 30, 30, Assets.colours[i], () -> addGuessColour(colour)));
        }
        uiManager.addUIButton(new UIButton(90, 315, 55, 150, Assets.confirm_button, this::confirmGuess));
        uiManager.addUIButton(new UIButton(265, 315, 55, 150, Assets.delete_button, this::clearGuess));

        uiManager.addUIButton(new UIButton(90, 456, 55, 55, Assets.black_peg_button, this::incrementBlackPegs));
        uiManager.addUIButton(new UIButton(155, 456, 55, 55, Assets.white_peg_button, this::incrementWhitePegs));
        uiManager.addUIButton(new UIButton(90, 540, 55, 150, Assets.confirm_button, this::confirmScore));
        uiManager.addUIButton(new UIButton(265, 540, 55, 150, Assets.delete_button, this::clearScore));
    }

    @Override
    protected void start() {
        isDecoding = true;
        numberOfGuesses = 0;
        clearGuess();
        clearScore();
    }

    private void confirmScore(){
        if(isDecoding || !isGameActive)
            return;
        isDecoding = true;
        allPegs[numberOfGuesses] = currentPegs;
        numberOfGuesses ++;
        if(blackPegCount == 4){
            isGameActive = false;
            showCode(currentGuess);
        }
        clearScore();
    }

    private void clearScore(){
        blackPegCount = 0;
        whitePegCount = 0;
        currentPegs = new BufferedImage[4];
    }

    private void confirmGuess(){
        if(!isDecoding || !isGameActive)
            return;
        isDecoding = false;
        panel[numberOfGuesses] = toColour(currentGuess);
        clearGuess();
    }

    private void clearGuess(){
        numberOfGuessColour = 0;
        currentGuess = new int[4];
        guessImages = new BufferedImage[4];
    }

    private void incrementBlackPegs(){
        if(!isDecoding && isGameActive)
            for(int i = 0; i < 4; i++){
                if(currentPegs[i] == null){
                    blackPegCount++;
                    currentPegs[i] = Assets.peg_black;
                    return;
                }
            }
    }

    private void incrementWhitePegs(){
        if(!isDecoding && isGameActive)
            for(int i = 0; i < 4; i++){
                if(currentPegs[i] == null){
                    whitePegCount++;
                    currentPegs[i] = Assets.peg_white;
                    return;
                }
            }
    }

    private void addGuessColour(int colour){
        if(numberOfGuessColour < 4 && isGameActive){
            currentGuess[numberOfGuessColour] = colour;
            guessImages[numberOfGuessColour] = Assets.colours[colour];
            numberOfGuessColour ++;
        }
    }

    @Override
    protected void postRender(Graphics graphics) {
        graphics.drawImage(Assets.pvpGameboard, 0, 0, cornerWidth, cornerHeight, null);
        for(int i = 0; i < currentPegs.length; i++)
            if(currentPegs[i] != null)
                graphics.drawImage(currentPegs[i], 227+45*i, 465, 40, 40, null);
        for(int i = 0; i < 4; i++)
            if(guessImages[i] != null)
                graphics.drawImage(guessImages[i], 155+55*i, 247, 30, 30, null);
    }
}
