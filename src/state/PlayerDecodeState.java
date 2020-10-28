
package state;

import gamelogic.Score;
import gfx.Assets;
import ui.UIButton;
import utils.Constants;
import utils.Utils;

import gamelogic.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlayerDecodeState extends GameState {

    private int numberOfGuessColour;
    private BufferedImage[] guessImages;
    private int[] currentGuess;
    private String code, message;
    private boolean playerWin;
    private String[] messages;

    public PlayerDecodeState(Game game){
        super(game);
        playerWin = false;
        guessImages = new BufferedImage[4];
        currentGuess = new int[4];
        uiManager.addUIButton(new UIButton(95, 360, 55, 150, Assets.confirmButton, this::confirmGuess));
        uiManager.addUIButton(new UIButton(270, 360, 55, 150, Assets.deleteButton, this::emptyCurrentGuess));
        uiManager.addUIButton(new UIButton(30, 648, 90, 90, Assets.backButton, ()->game.setState(State.states[1])));
        messages = Utils.loadFileAsArrayList("res/data/decode_message.txt").toArray(new String[0]);
        for(int i = 0; i < 6; i++) {
            int colour = i;
            uiManager.addUIButton(new UIButton(105+55*i, 240, 30, 30, Assets.colours[i], () -> addGuessColour(colour)));
        }
    }

    @Override
    protected void start() {
        playerWin = false;
    	numberOfGuesses = 0;
        do{
            code = Constants.allStringCombinations.get((int)(Math.random()*1296));
        }while (getNumberOfDuplicate(code) > dupColour);

        message = messages[0];
        System.out.println(code);
    }

    private int getNumberOfDuplicate(String code){
        int[] intCode = Utils.toIntArrayColour(code);
        int[] colourCount = new int[6];
        for(int i: intCode)
            colourCount[i] = colourCount[i]+1;
        int max = 0;
        for(int i: colourCount)
            max = Math.max(max, i);
        return max;
    }

    @Override
    protected void postRender(Graphics graphics) {
        graphics.drawImage(Assets.playerGameboard, 0, 0, cornerWidth, cornerHeight, null);
        for(int i = 0; i < 4; i++)
            if(guessImages[i] != null)
                graphics.drawImage(guessImages[i], 160+55*i, 300, 30, 30, null);

      
        if(!isGameActive) {
        	if(playerWin) {
        		graphics.drawImage(Assets.codebreakerWins, 80, 460, 380, 90, null);
        		graphics.drawImage(Assets.numberButtons[numberOfGuesses+1], 229, 509, 35, 35, null);
        	}
        	else
        		graphics.drawImage(Assets.codemakerWins, 80, 470, 380, 64, null);
        }
        	
        	
    }

    @Override
    protected void messageRender(Graphics graphics) {
        ArrayList<String> lines = Utils.splitString(message, 44);
        if (isGameActive) {
            if(numberOfGuesses == 0){
                Utils.drawText(graphics, "Welcome, codebreaker",
                        260, 470, Color.BLACK, Assets.arial28);
                for(int y = 0; y < lines.size(); y++){
                    Utils.drawText(graphics, lines.get(y),
                            260, 505+25*y, Color.BLACK, Assets.arial20);
                }
            }else {
                for(int y = 0; y < lines.size(); y++){
                    Utils.drawText(graphics, lines.get(y),
                            260, 495+25*y, Color.BLACK, Assets.arial20);
                }
            }
        }else {
            for(int y = 0; y < lines.size(); y++) {
                Utils.drawText(graphics, lines.get(y),
                        260, 560 + 25 * y, Color.BLACK, Assets.arial20);
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

    private void confirmGuess(){
        if(!isGameActive || guessImages[3] == null)
            return;
        addAllColourImages(panel, guessImages);
        String guess = Utils.toStringColour(currentGuess);
        Score score = Constants.possibleScores.get(guess).get(code);
        BufferedImage[] scoreImage = new BufferedImage[4];
        for(int i = 0; i < score.getBlackPeg(); i++)
            scoreImage[i] = Assets.pegBlack;
        for(int i = score.getBlackPeg(); i < score.getBlackPeg()+score.getWhitePeg(); i++)
            scoreImage[i] = Assets.pegWhite;
        addAllColourImages(allPegs, scoreImage);

        emptyCurrentGuess();
        if(score.isDecoded()){
            showCode(Utils.toIntArrayColour(code));
            isGameActive = false;
            playerWin = true;
            message = messages[(int)(Math.random()*2+1)];
            return;
        } else if(numberOfGuesses == maxGuess-1){
            showCode(Utils.toIntArrayColour(code));
            isGameActive = false;
            message = messages[(int)(Math.random()*5+9)];
            return;
        }
        if(numberOfGuesses == 0)
            message = messages[(int)(Math.random()*3+3)];
        else if(numberOfGuesses == maxGuess-2)
            message = messages[(int)(Math.random()*3+6)];
        else if(score.getBlackPeg() == 0 && score.getWhitePeg() == 0)
            message = messages[(int)(Math.random()*3+14)];
        else if(score.getBlackPeg() >= 2)
            message = messages[(int)(Math.random()*3+17)];
        else
            message = messages[(int)(Math.random()*3+20)];
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
