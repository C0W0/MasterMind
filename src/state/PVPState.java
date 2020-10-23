package state;

import gfx.Assets;
import ui.UIButton;

import java.awt.*;
import java.awt.image.BufferedImage;

import gamelogic.Game;

public class PVPState extends GameState {


    private BufferedImage[] currentPegs, guessImages;
    private int[] currentGuess;
    private int blackPegCount, whitePegCount, numberOfGuessColour;
    private int numberOfGuesses;
    private boolean isDecoding;


    public PVPState(Game game){
        super(game);
        isDecoding = true;
        currentPegs = new BufferedImage[4];
        guessImages = new BufferedImage[4];
        currentGuess = new int[4];

        for(int i = 0; i < 6; i++) {
            int colour = i;
            uiManager.addUIButton(new UIButton(100+55*i, 180, 30, 30, Assets.colours[i], () -> addGuessColour(colour)));
        }
        uiManager.addUIButton(new UIButton(90, 315, 55, 150, Assets.confirmButton, this::confirmGuess));
        uiManager.addUIButton(new UIButton(265, 315, 55, 150, Assets.deleteButton, this::clearGuess));
        
        uiManager.addUIButton(new UIButton(90, 456, 55, 55, Assets.blackPegButton, this::incrementBlackPegs));
        uiManager.addUIButton(new UIButton(155, 456, 55, 55, Assets.whitePegButton, this::incrementWhitePegs));
        uiManager.addUIButton(new UIButton(90, 540, 55, 150, Assets.confirmButton, this::confirmScore));
        uiManager.addUIButton(new UIButton(265, 540, 55, 150, Assets.deleteButton, this::clearScore));
        
        uiManager.addUIButton(new UIButton(30, 648, 90, 90, Assets.backButton, ()->game.setState(State.states[1])));
        
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
            System.out.println("The decoder won");
            isGameActive = false;
            showCode(currentGuess);
        }else if(numberOfGuesses >= maxGuess){
            //should never happen
            System.out.println("The decoder lost");
            isGameActive = false;
        }
        clearScore();
    }

    private void clearScore(){
        blackPegCount = 0;
        whitePegCount = 0;
        currentPegs = new BufferedImage[4];
    }

    private void confirmGuess(){
        if(!isDecoding || !isGameActive || guessImages[3] == null)
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
                    currentPegs[i] = Assets.pegBlack;
                    return;
                }
            }
    }

    private void incrementWhitePegs(){
        if(!isDecoding && isGameActive)
            for(int i = 0; i < 4; i++){
                if(currentPegs[i] == null){
                    whitePegCount++;
                    currentPegs[i] = Assets.pegWhite;
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
