package state;

import gamelogic.Score;
import gfx.Assets;
import ui.UIButton;
import utils.Constants;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerDecodeState extends GameState {

    private int numberOfGuesses;
    private int numberOfGuessColour;
    private BufferedImage[] guessImages;
    private int[] currentGuess;
    private String code;

    public PlayerDecodeState(){
        super();
        guessImages = new BufferedImage[4];
        currentGuess = new int[4];
        uiManager.addUIButton(new UIButton(95, 360, 55, 150, Assets.confirm_button, this::confirmGuess));
        uiManager.addUIButton(new UIButton(270, 360, 55, 150, Assets.delete_button, this::emptyCurrentGuess));
        for(int i = 0; i < 6; i++) {
            int colour = i;
            uiManager.addUIButton(new UIButton(105+55*i, 240, 30, 30, Assets.colours[i], () -> addGuessColour(colour)));
        }
    }

    @Override
    protected void start() {
        numberOfGuesses = 0;
        code = Constants.allStringCombinations.get((int)(Math.random()*1296));
        System.out.println(code);
    }

    @Override
    protected void postRender(Graphics graphics) {
        graphics.drawImage(Assets.playerGameboard, 0, 0, cornerWidth, cornerHeight, null);
        for(int i = 0; i < 4; i++)
            if(guessImages[i] != null)
                graphics.drawImage(guessImages[i], 160+55*i, 300, 30, 30, null);
    }

    private void addGuessColour(int colour){
        if(numberOfGuessColour < 4){
            currentGuess[numberOfGuessColour] = colour;
            guessImages[numberOfGuessColour] = Assets.colours[colour];
            numberOfGuessColour ++;
        }
    }

    private void confirmGuess(){
        addAllColourImages(panel, guessImages);
        String guess = Utils.ColourCombination.toStringColour(currentGuess);
        Score score = Constants.possibleScores.get(guess).get(code);
        BufferedImage[] scoreImage = new BufferedImage[4];
        for(int i = 0; i < score.getBlackPeg(); i++)
            scoreImage[i] = Assets.peg_black;
        for(int i = score.getBlackPeg(); i < score.getBlackPeg()+score.getWhitePeg(); i++)
            scoreImage[i] = Assets.peg_white;
        addAllColourImages(allPegs, scoreImage);

        emptyCurrentGuess();
        if(score.isDecoded()){
            showCode(Utils.ColourCombination.toIntArrayColour(code));
            return;
        } else if(numberOfGuesses >= 10){
            System.out.println("Player lost");
            return;
        }
        numberOfGuesses ++;
    }

    private void emptyCurrentGuess(){
        numberOfGuessColour = 0;
        guessImages = new BufferedImage[4];
        currentGuess = new int[4];
    }

    private void addAllColourImages(BufferedImage[][] target, BufferedImage[] source){
        target[numberOfGuesses] = source;
    }
}
