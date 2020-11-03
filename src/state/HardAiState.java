/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 27, 2020
Java 13.0.2
The object created from this class runs the ai decode game mode
of CodeBreaker and contains the best AI algorithm

List of Global Variables:
currentPegs - the images of currently selected pegs for hint </type BufferedImage[]>
message - the list of all messages, loaded from a file </type String[]>
lastGuess - the last guess of the AI, in the String form </type String>
lastScore - the score of last guess, provided by the player </type Score>
blackPegCount - the number of black pegs picked for hint </type int>
whitePegCount - the number of white pegs picked for hint </type int>
playerCheated - whether the number of guess exceed 5. Since the AI will always
    succeed in 5 or less tries, the player must be cheating or made a mistake
    for that to happen </type boolean>
possibleAnswers - a lost of all potential combinations that can be the secret
    code. This is used for the AI to make the best decision </type ArrayList>
allScores - a copy of possibleScores from the Constants class </type HashMap>
possibleScores - also a copy of possibleScores from the Constants class.
    This one, however, will be modified to remove all impossible answers for
    the AI to make the best decision </type HashMap>
===============================================================================
*/
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
    private String[] messages;

    //guess and score
    private String lastGuess;
    private Score lastScore;
    private int blackPegCount, whitePegCount;
    private boolean playerCheated;

    //ai
    private ArrayList<String> possibleAnswers;
    private HashMap<String, HashMap<String, Score>> allScores, possibleScores;

    /**HardAiState method
     * Constructor method of the HardAiState class
     *
     * This method creates & places all of the necessary buttons for this game state.
     * It also initializes some variables.
     *
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
    public HardAiState(Game game){
        super(game);
        currentPegs = new BufferedImage[4];
        uiManager.addUIButton(new UIButton(90, 270, 55, 55, Assets.blackPegButton, this::incrementBlackPegs));
        uiManager.addUIButton(new UIButton(155, 270, 55, 55, Assets.whitePegButton, this::incrementWhitePegs));
        uiManager.addUIButton(new UIButton(90, 350, 55, 150, Assets.confirmButton, this::confirmScore));
        uiManager.addUIButton(new UIButton(265, 350, 55, 150, Assets.deleteButton, this::removeScore));

        uiManager.addUIButton(new UIButton(30, 648, 90, 90, Assets.backButton, ()->game.setState(State.states[2])));

        messages = Utils.loadFileAsArrayList("res/data/hard_ai.txt").toArray(new String[0]);
        message = messages[0];
        playerCheated = false;
    }

    /**start method
     * This procedural method is inherited from the parent class, the GameState class.
     * It is called in the init method and contains all of the procedures to start a
     * new game.
     *
     * List of Local Variables:
     * guess - the initial guess of the AI, in integer array form </type int[]>
     */
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
        message = messages[0];
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
        graphics.drawImage(Assets.aiGameboard, 0, 0, cornerWidth, cornerHeight, null);
        for(int i = 0; i < currentPegs.length; i++)
            if(currentPegs[i] != null)
                graphics.drawImage(currentPegs[i], 235+45*i, 275, 40, 40, null);
        
        if(!isGameActive && !playerCheated) {
             graphics.drawImage(Assets.codebreakerWins, 80, 460, 380, 90, null);
             graphics.drawImage(Assets.numberButtons[numberOfGuesses], 229, 509, 35, 35, null);
    	}
    }


    /**messageRender method
     * This procedural method is inherited from the parent class, the GameState class.
     * It is called in the render method and contains all of the procedures to render
     * text messages onto the screen.
     *
     * List of Local Variables:
     * lines - when doing text rendering, a simple algorithm is applied to break
     *      text into even lines without destroying the words. This variable
     *      stores the lines of String after applying to algorithm to the current
     *      message </type String>
     *
     * @param graphics - a graphics context for drawing buffer </type Graphics>
     */
    @Override
    protected void messageRender(Graphics graphics) {
        ArrayList<String> lines = Utils.splitString(message, 40);
        if(!isGameActive && !playerCheated){
            for(int y = 0; y < lines.size(); y++) {
                Utils.drawText(graphics, lines.get(y),
                        260, 560 + 25 * y, Color.BLACK, Assets.arial20);
            }
        }else if(numberOfGuesses == 0){
            Utils.drawText(graphics, "Welcome to HARD AI codebreaker",
                    260, 470, Color.BLACK, Assets.arial20);
            for(int y = 0; y < lines.size(); y++){
                Utils.drawText(graphics, lines.get(y),
                        260, 500+25*y, Color.BLACK, Assets.arial20);
            }
            for(int i = 0; i < 6; i++)
                graphics.drawImage(Assets.colours[i], 105+55*i, 565, 26, 26, null);
        }else {
            for(int y = 0; y < lines.size(); y++){
                Utils.drawText(graphics, lines.get(y),
                        260, 495+25*y, Color.BLACK, Assets.arial20);
            }
        }

    }

    /**confirmScore method
     * This procedural method confirms the selected hint for a guess.
     * This method also checks for the win/lose condition.
     *
     * List of Local Variables:
     * guess - the guess AI made for this turn, in integer array form </type int[]>
     */
    private void confirmScore(){
        if(!isGameActive)
            return;
        allPegs[numberOfGuesses] = currentPegs;
        lastScore = new Score(blackPegCount, whitePegCount);
        removeScore();
        numberOfGuesses ++;
        if(lastScore.isDecoded()){
            showCode(Utils.toIntArrayColour(lastGuess));
            message = messages[(int)(Math.random()*3+1)];
            isGameActive = false;
            return;
        }else if(numberOfGuesses >= maxGuess){
            playerCheated = true;
            message = messages[(int)(Math.random()*2+7)];
            isGameActive = false;
            return;
        }
        message = "(The AI is making decision)";
        int[] guess = makeGuesses();

        if(numberOfGuesses == 4)
            message = messages[(int)(Math.random()*3+4)];
        else if(numberOfGuesses > 4)
            message = messages[(int)(Math.random()*3+9)];
        else
            message = messages[(int)(Math.random()*9+12)];

        lastGuess = Utils.toStringColour(guess);
        panel[numberOfGuesses] = toColour(guess);
    }

    /**removeScore method
     * This procedural method clears the selected hint for a guess.
     */
    private void removeScore(){
        blackPegCount = 0;
        whitePegCount = 0;
        currentPegs = new BufferedImage[4];
    }

    /**incrementBlackPegs method
     * This procedural method increments the number of black pegs for the hint by
     * 1 and places in the appropriate image.
     */
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

    /**incrementWhitePegs method
     * This procedural method increments the number of white pegs for the hint by
     * 1 and places in the appropriate image
     */
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

    /**makeGuess method
     * This functional method makes the optimum guesses to decode any possible code
     * within 5 guesses or less with Donald Knuth's algorithm.
     * @see <a href="https://www.cs.uni.edu/~wallingf/teaching/cs3530/resources/knuth-mastermind.pdf"></a>
     *
     * List of Local Variables:
     * guesses - a list of every single potential guesses </type ArrayList>
     * it - an Iterator object used to iterate through HashMaps </type Iterator>
     * allPossibleScores - a HashMap that stores all possible scores of a
     *      given guess </type HashMap>
     * worst - an integer score assigned to a given guess based on how
     *      many potential solutions such a guess can eliminate </type int>
     * guessIsImpossible - is this given guess a possible solution </type boolean>
     *
     * @return - the optimum guess of this turn
     */
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
            guesses.add(new Guess(worst, guessIsImpossible, entry.getKey()));

        }
        return Guess.getMinimumGuess(guesses);
    }

}
