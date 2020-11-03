/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 27, 2020
Java 13.0.2
The object created from this class runs the player-vs-player game mode
of CodeBreaker

List of Global Variables:
currentPegs - the images of currently selected pegs for hint </type BufferedImage[]>
guessImages - the images of currently selected colours for guess </type BufferedImage[]>
currentGuess - the integer array form of currently selected colours for guess
    </type int[]>
blackPegCount - the number of black pegs picked for hint. This is used to
    determine whether is victory condition is met </type int>
numberOfGuessColour - the number of colour pieces picked. This is used to
    prevent the submission of incomplete colour combination </type int>
isDecoding - whether the current turn is for the decoder or the code maker.
    This is used prevent the incorrect player from submitting a guess or
    hint in the other's turn </type boolean>
===============================================================================
*/
package state;

import gfx.Assets;
import ui.UIButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import gamelogic.Game;
import utils.Utils;

public class PVPState extends GameState {


    private BufferedImage[] currentPegs, guessImages;
    private int[] currentGuess;
    private int blackPegCount, numberOfGuessColour;
    private boolean isDecoding;

    /**PVPState method
     * Constructor method of the PVPState class
     *
     * This method creates & places all of the necessary buttons for this game state.
     * It also initializes some variables.
     *
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
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

    /**start method
     * This procedural method is inherited from the parent class, the GameState class.
     * It is called in the init method and contains all of the procedures to start a
     * new game.
     */
    @Override
    protected void start() {
        message = "Next Turn";
        isDecoding = true;
        numberOfGuesses = 0;
        clearGuess();
        clearScore();
    }

    /**confirmScore method
     * This procedural method confirms the selected hint for a guess.
     * This method also checks for the win/lose condition.
     */
    private void confirmScore(){
        if(isDecoding || !isGameActive)
            return;
        isDecoding = true;
        allPegs[numberOfGuesses] = currentPegs;
        numberOfGuesses ++;
        if(blackPegCount == 4){
            message = "You Win!";
            isDecoding = false;
            isGameActive = false;
            showCode(currentGuess);
        }else if(numberOfGuesses >= maxGuess){
            message = "You Win!";
            isGameActive = false;
        }
        clearGuess();
        clearScore();
    }

    /**clearScore method
     * This procedural method clears the selected hint for a guess.
     */
    private void clearScore(){
        blackPegCount = 0;
        currentPegs = new BufferedImage[4];
    }

    /**confirmGuess method
     * This procedural method confirms the selected guess.
     */
    private void confirmGuess(){
        if(!isDecoding || !isGameActive || guessImages[3] == null)
            return;
        isDecoding = false;
        panel[numberOfGuesses] = toColour(currentGuess);
        guessImages = new BufferedImage[4];
    }

    /**confirmGuess method
     * This procedural method clears the selected guess.
     */
    private void clearGuess(){
        numberOfGuessColour = 0;
        currentGuess = new int[4];
        guessImages = new BufferedImage[4];
    }

    /**incrementBlackPegs method
     * This procedural method increments the number of black pegs for the hint by
     * 1 and places in the appropriate image.
     */
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

    /**incrementWhitePegs method
     * This procedural method increments the number of white pegs for the hint by
     * 1 and places in the appropriate image
     */
    private void incrementWhitePegs(){
        if(!isDecoding && isGameActive)
            for(int i = 0; i < 4; i++){
                if(currentPegs[i] == null){
                    currentPegs[i] = Assets.pegWhite;
                    return;
                }
            }
    }

    /**addGuessColour method
     * This procedural method adds a colour to the guess combination and place
     * in the appropriate image.
     *
     * @param colour - the number code of the colour </type int>
     */
    private void addGuessColour(int colour){
        if(numberOfGuessColour < 4 && isGameActive){
            currentGuess[numberOfGuessColour] = colour;
            guessImages[numberOfGuessColour] = Assets.colours[colour];
            numberOfGuessColour ++;
        }
    }

    /**postRender method
     * This procedural method is inherited from the parent class, the GameState class.
     * It is called in the render method and contains all of the procedures to render
     * additional graphical contents to the screen.
     *
     * @param graphics - a graphics context for drawing buffer </type Graphics>
     */
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

    /**messageRender method
     * This procedural method is inherited from the parent class, the GameState class.
     * It is called in the render method and contains all of the procedures to render
     * text messages onto the screen.
     *
     * @param graphics - a graphics context for drawing buffer </type Graphics>
     */
    @Override
    protected void messageRender(Graphics graphics) {
        if(isDecoding)
            Utils.drawText(graphics, message, 310, 485, Color.BLACK, Assets.arial28);
        else
            Utils.drawText(graphics, message, 255, 260, Color.BLACK, Assets.arial28);
    }
}
