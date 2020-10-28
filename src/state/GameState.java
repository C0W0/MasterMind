/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 27, 2020
Java 13.0.2
The abstract parent class for every game play states
Contains necessary fields and methods for every single
game play state to run

List of Global Variables:
panel - a table of images in which the coloured pieces of each guesses
    are stored. It is used to render the coloured pieces on the screen.
     </type BufferedImage[][]>
allPegs - a table of images in which the black and white pegs of each
    hints are stored. It is used to render the pegs on the screen.
     </type BufferedImage[][]>
code - the secret code in the form of an image array. It will not be showed
    if the program is not the keeper of the code and is unable to figure out
    the code. </type BufferedImage[]>
cornerWidth - the width of the corner area of the screen. This area is
    reserved for logos and title of the current game state </type int>
cornerHeight - the height of the corner area of the screen. This area is
    reserved for logos and title of the current game state. </type int>
maxGuess - the maximum number of guesses the codebreaker may attempt before
    losing. </type int>
dupColour - the maximum number of duplicate colours allowed in the secret
    code. </type int>
isGameActive - whether the game is still on going and the player can take
    actions. </type boolean>
numberOfGuesses - the current number of guesses </type int>
===============================================================================
*/
package state;

import gfx.Assets;
import ui.UIButton;
import gamelogic.Game;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameState extends State{

    //graphics
    protected BufferedImage[][] panel, allPegs;
    protected BufferedImage[] code;
    protected final int cornerWidth, cornerHeight;

    //control
    protected int maxGuess, dupColour;
    protected boolean isGameActive;
    protected int numberOfGuesses;

    /**GameState method
     * The constructor method of the GameState class
     * All the significant parameters are initialized in this method
     *
     * @param game - The reference of the main object of the game. It is
     *             passed in here for state switching. </type Game>
     */
    GameState(Game game){
    	super(game);
    	maxGuess = 10;
    	dupColour = 4;
        panel = new BufferedImage[10][4];
        allPegs = new BufferedImage[10][4];
        code = new BufferedImage[4];
        uiManager.addUIButton(new UIButton(135, 648, 90, 90, Assets.restartButton, this::init));
        cornerWidth = (int)(1020.f/2000*1024);
        cornerHeight = (int)(1170.f/1428*768);
        isGameActive = false;
    }

    /**render method
     * This procedural method is inherited from the State class. It is called
     * in the main game object 45 times a second. It is used to render text and
     * images onto the screen and all of the other rendering methods
     * (e.g. postRender()) within the object are called here.
     *
     * @param graphics - a graphics context for drawing buffer </type Graphics>
     */
    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(Assets.emptyGameboard, 0, 0, 1024, 768, null);

        for(int y = 0; y < panel.length; y++){
            for(int x = 0; x < panel[y].length; x++){
                if(panel[y][x] == null)
                    graphics.drawImage(Assets.emptySlot, 690+50*x, (int)(81+54.5*y), 30, 30, null);
                else
                    graphics.drawImage(panel[y][x], 690+50*x, (int)(81+54.5*y), 30, 30, null);
            }
        }

        for(int y = 0; y < allPegs.length; y++){
            for(int x = 0; x < 2; x++){
                if(allPegs[y][x] != null)
                    graphics.drawImage(allPegs[y][x], 920+17*x, 81+54*y, 15, 15, null);
            }
            for(int x = 2; x < 4; x++){
                if(allPegs[y][x] != null)
                    graphics.drawImage(allPegs[y][x], 920+17*(x-2), 98+54*y, 15, 15, null);
            }
        }

        if(code[0] != null)
            for(int i = 0; i < code.length; i++)
                graphics.drawImage(code[i], 680+50*i, 690, 30, 30, null);

        postRender(graphics);
        graphics.drawImage(Assets.numberButtons[dupColour], 445, 635, 50,  50, null);
        graphics.drawImage(Assets.numberButtons[maxGuess], 445, 695, 50,  50, null);
        messageRender(graphics);
        uiManager.render(graphics);
    }

    /**init method
     * This procedural method is inherited from the State class. It is triggered by
     * clicking buttons and switching states. This method refreshes come critical
     * variables within this state object.
     */
    @Override
    public void init() {
        panel = new BufferedImage[10][4];
        allPegs = new BufferedImage[10][4];
        code = new BufferedImage[4];
        isGameActive = true;

        start();
    }

    /**start method
     * This abstract procedural method will be implemented in each game play state.
     * It is called in the init method and should contain all of the procedures to
     * start a new game.
     */
    protected abstract void start();

    /**postRender method
     * This abstract procedural method will be implemented in each game play state.
     * It is called in the render method and should contain all of the procedures to
     * render any additional graphical contents.
     *
     * @param graphics - a graphics context for drawing buffer </type Graphics>
     */
    protected abstract void postRender(Graphics graphics);

    /**messageRender method
     * This abstract procedural method will be implemented in each game play state.
     * It is called in the render method and should contain all of the procedures to
     * render any text messages onto the screen.
     *
     * @param graphics - a graphics context for drawing buffer </type Graphics>
     */
    protected abstract void messageRender(Graphics graphics);

    /**showCode method
     * This procedural method reveals the secret code on the bottom of the screen.
     * It might not be called for every single time game.
     *
     * @param code - the integer array form of the secret code </type int[]>
     */
    protected void showCode(int[] code){
        this.code = toColour(code);
    }

    /**toColour method
     * This functional method converts a colour combination from its integer array
     * form to its BufferedImage array form so it can be rendered on the screen.
     *
     * List of Local Variables:
     * colours - the BufferedImage array form of the colour combination </type BufferedImage[]>
     *
     * @param intColour - the integer array form of the colour combination </type int[]>
     * @return colours - the BufferedImage array form of the colour
     *      combination </type BufferedImage[]>
     */
    protected BufferedImage[] toColour(int[] intColour){
        BufferedImage[] colours = new BufferedImage[4];
        for(int i = 0; i < 4; i++){
            colours[i] = Assets.colours[intColour[i]];
        }
        return colours;
    }

    /**serMaxGuess method
     * This procedural method sets the maximum number of guesses the codebreaker
     * may attempt before losing.
     *
     * @param maxGuess - the maximum number of guesses the codebreaker may attempt
     *                 before losing. </type int>
     */
    void setMaxGuess(int maxGuess) {
        this.maxGuess = maxGuess;
    }

    /**setDupColour method
     * This procedural method sets the maximum number of duplicate colours allowed in the secret
     * code.
     *
     * @param dupColour - the maximum number of duplicate colours allowed in the secret
     *                  code. </type int>
     */
    void setDupColour(int dupColour) {
        this.dupColour = dupColour;
    }
}
