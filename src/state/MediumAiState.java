/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
November 2, 2020
Java 13.0.2
The object created from this class runs the ai decode game mode
of CodeBreaker and contains the medium-level AI algorithm

List of Global Variables:
currentPegs - the images of currently selected pegs for hint </type BufferedImage[]>
blackPegCount - the current number of black feedback pegs given by the player </type int>
whitePegCount - the current number of white feedback pegs given by the player </type int>
messages[] - the list of all messages, loaded from a file </type String[]>
header - the introductory message displayed when the player loads this gamemode  </type String>
generated - the variable that tracks whether or not all 24 permutations of the 
    4 known colours have been generated </type boolean>
firstGuess - the variable that tracks whether or not the AI is making its first guess </type boolean>
playerWin - the variable that tracks whether the player (codemaker) has won or lost </type boolean>
coloursFound - the total number of colours in the player's code that have been found </type int>
choice - the index of choiceList used to determine the AI's next guess </type int>
guess[] - the combination of colours that the AI is currently guessing
knownColours[] - the 4 known colours that the player's code consists of
allPermutations[][] - the 24 possible permutations of the 4 known colours in the player's code </type int[][]>
choiceList - the list of indexes (row numbers) of all remaining valid permutations. This list is gradually 
    reduced after each guess as invalid permutations that cannot be the code are removed </type ArrayList>
===============================================================================
*/
package state;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;
import utils.Utils;

public class MediumAiState extends GameState {

	private BufferedImage[] currentPegs;
	private int blackPegCount, whitePegCount;
	private String[] messages;
	protected String header;

	private boolean generated = false;
	private boolean firstGuess = true;
	private boolean playerWin;
	
	private int coloursFound;
	protected int choice;

	protected int[] guess;
	private int[] knownColours;
	protected int[][] allPermutations;

	protected ArrayList<Integer> choiceList;
	
	/**MediumAiState method
	 * Constructor method of the MediumAiState class
	 *
	 * This method creates & places all of the necessary buttons for this game state.
	 * It also initializes some variables.
	 *
	 * @param game - the passed-in object of the custom-made Game class </type Game>
	 */
	public MediumAiState(Game game) {
		super(game);
		
		playerWin = false;
		currentPegs= new BufferedImage[4];

		uiManager.addUIButton(new UIButton(90, 270, 55, 55, Assets.blackPegButton, this::incrementBlackPegs));
		uiManager.addUIButton(new UIButton(155, 270, 55, 55, Assets.whitePegButton, this::incrementWhitePegs));
		uiManager.addUIButton(new UIButton(90, 350, 55, 150, Assets.confirmButton, this::confirmFeedback));
		uiManager.addUIButton(new UIButton(265, 350, 55, 150, Assets.deleteButton, this::removeFeedback));
        uiManager.addUIButton(new UIButton(30, 648, 90, 90, Assets.backButton, ()->game.setState(State.states[2])));

		choiceList = new ArrayList<>();
		guess = new int[4];
		knownColours = new int[4];
		allPermutations = new int[24][4];

		messages = Utils.loadFileAsArrayList("res/data/medium_ai.txt").toArray(new String[0]);
		message = messages[0];
		header = "Welcome to MEDIUM AI codebreaker";
		for(int i = 0; i < messages.length; i++)
			System.out.println(i+" "+messages[i]);
	}

	/**start method
     * This procedural method is inherited from the parent class, the GameState class.
     * It is called in the init method and contains all of the procedures to start a
     * new game, including calling the confirmFeedback method once
     */
	@Override
	protected void start() {
		playerWin = false;
		generated=false;
		firstGuess=true;
		blackPegCount=whitePegCount=0;

		numberOfGuesses=0;
		coloursFound = 0;

		currentPegs = new BufferedImage[4];
		choiceList = new ArrayList<>();
		guess = new int[4];
		knownColours = new int[4];
		allPermutations = new int[24][4];
		message = messages[0];

		confirmFeedback();
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
		for(int i=0; i<currentPegs.length; i++) //draws black/white pegs (during selection)
			if(currentPegs[i]!=null)
				graphics.drawImage(currentPegs[i], 235+45*i, 275, 40, 40, null);
		
		if(!isGameActive) {
        	if(playerWin)
        		graphics.drawImage(Assets.codemakerWins, 80, 470, 380, 64, null);
        	else {
        		graphics.drawImage(Assets.codebreakerWins, 80, 460, 380, 90, null);
        		graphics.drawImage(Assets.numberButtons[numberOfGuesses-1], 229, 509, 35, 35, null);
        	}
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
		if (isGameActive) {
			if(numberOfGuesses == 1){
				Utils.drawText(graphics, header,
						260, 470, Color.BLACK, Assets.arial20);
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
	 /**addAllColourImages method
     * TERRY PLEASE FILL THIS OUT --------------------------------------------------------------------------------------------------------------------------
     */
	protected void addAllColourImages(BufferedImage[][] target, BufferedImage[] source){
        target[numberOfGuesses-1] = source;
    }
	
	/**confirmFeedback method
     * This procedural method is called once the player submits their feedback, and determines the
     * next course of action, such as: displaying a victory/loss message, determining the AI's next
     * guess, generating all permutations of the 4 known colours, and/or displaying a message
     */
	private void confirmFeedback() {
		
		if(!isGameActive)
			return;
		
		 //renders the given feedback next to the guess for the player to see
		BufferedImage[] pegsImage = new BufferedImage[4];
		for(int i = 0; i < blackPegCount; i++) 
			pegsImage[i] = Assets.pegBlack;
		for(int i = blackPegCount; i < blackPegCount+whitePegCount; i++)
			pegsImage[i] = Assets.pegWhite;

		if(numberOfGuesses != 0)
			addAllColourImages(allPegs, pegsImage); //
		numberOfGuesses++;
		 
		//checks if the codebreaker (AI) has won, and if so, 'ends' the method & game
		if(blackPegCount==4) {
			showCode(guess);
			message = messages[(int)(Math.random()*3+1)];
			isGameActive = false;
			return;
		}
		
		//checks if the codemaker (player) has won, and if so, 'ends' the method & game
		else if(numberOfGuesses>maxGuess) {
			message = messages[(int)(Math.random()*3+4)];
			isGameActive = false;
			playerWin = true;
			return;
		}
		
		//updates the number of colours found (needs to be done before guesses are made)
		if(coloursFound<4) {
			updateColoursFound();
		}
		
		//determines the purely comedic message displayed by the computer for the subsequent round
		if(numberOfGuesses != 1){
			if(coloursFound<4){
				message = messages[(int)(Math.random()*5+7)];
			}else {
				message = messages[(int)(Math.random()*11+12)];
			}
		}
		
		//if by the 6th guess, all colours are NOT found, then all remaining colours in the code
		//MUST be yellow (the last colour that has yet to be guessed)
		if(numberOfGuesses==6 && coloursFound<4)
			fillRemainingColour();
			
		//if not (above condition) and all knownColours have yet to be found, then the AI should be
		//finding the 4 code colours, rather than making an 'actual guess'
		else if(numberOfGuesses<=6 && coloursFound<4)			
			findKnownColours();
		
		//if all 4 colours in the code have been found, then the AI will begin to make 'real guesses'
		if(coloursFound==4) {
			if(!generated) { //generates all 24 permutations of the 4 known colours if yet to be done
				generatePermutations(4,knownColours);
				generated = true;
			}
			makeGuess();
		}

		removeFeedback(); //finally, the most recently submitted feeedback must be removed to allow
		//the player to provide feedback for the new guessed code
	}
	
	 /**removeFeedback method
     * This procedural method clears the player's current feedback pegs.
     */
	private void removeFeedback() {
		currentPegs = new BufferedImage[4];
		whitePegCount = blackPegCount = 0;
	}
	
	/**findKnownColours method
     * This procedural method produces guesses to determine the 4 colours used in the player's code.
     * It does so by systematically guessing 'BBBB', then 'GGGG', and so on until all 4 colours have been found.
     * This is the 'first phase' of the AI's guessing algorithm
     */
	private void findKnownColours() {
		Arrays.fill(guess, numberOfGuesses - 1);
		addAllColourImages(panel, toColour(guess));
	}
	
	/**updateColoursFound method
     * This procedural method updates the number of colours found, while also
     * adding recently found colours to the list of known colours accordingly.
     */
	private void updateColoursFound(){
		for(int i=0; i<blackPegCount; i++) {
			knownColours[coloursFound]=numberOfGuesses-2;
			coloursFound++;
		}
	}
	
	/**fillRemainingColour method
     * This procedural method fills the rest of the knownColours array with 5,
     * which is the value of the last colour, yellow.
     */
	private void fillRemainingColour() {
		while(numberOfGuesses==6 && coloursFound<4) {
			knownColours[coloursFound]=5;
			coloursFound++;
		}
	}
	
	/**generatePermutations method
     * This procedural method generates all 24 permutations of the 4 known colours using recursion.
     * It also fills choiceList with values 0 to 23 (representing possible indexes to choose from)
     * 
     *@param n - the number of slots/spaces in the permutation </type int>
     *@param elements - the list of elements to generate all permutations of </type int[]>
     */
	private void generatePermutations(int n, int[] elements) {
		
		//after all swaps have been made, store the current permutation of elements[] in allPermutations[][],
		//using the current size of choiceList as a 'row number counter' that also gets incremented
		if(n == 1) {
			allPermutations[choiceList.size()] = elements.clone();
			choiceList.add(choiceList.size()); //fills the next value of choiceList with a number (0-23)
		}

		//if not, then begin creating all permutations of the array by rearranging/swapping elements
		//every possible way, using recursive method calls
		else {
			for(int i = 0; i < n-1; i++) {
				generatePermutations(n - 1, elements);

				if(n % 2 == 0) 
					swap(elements, i, n-1);
				else
					swap(elements, 0, n-1);
			}
			generatePermutations(n - 1, elements);
		}
	}
	
	/**swap method
     * This procedural method swaps the values of two indexes in an array.
     * It is used to assist the generateAllPermutations method
     *  
     * List of Local Variables:
     * temp - the variable used to temporarily store the value of an index in the array to be swapped </type int>
     * 
     *@param input - the array in which index values will be swapped </type int[]>
     *@param n1 - the index of the first value to swap </type int>
     *@param n2 - the index of the second value to swap </type int>
     */
	private void swap(int[] input, int n1, int n2) {
		int temp = input[n1];
		input[n1] = input[n2];
		input[n2] = temp;
	}
	
	/**makeGuess method
     * This procedural method determines the 'real guesses' that the AI makes during the 'second phase' of guessing
     * by selecting one of the remaining valid permutations numbers.
     *  
     * If the first guess has not been made, then the AI defaults to selecting the first permutation (index = 0).
     * For all guesses afterwards, the AI selects a random remaining permutation index from choiceList as the guess.
     * 
     * However, after each guess, the AI scans through all remaining indexes stored in choiceList. If the permutation
     * associated with that index doesn't generate the same score (number of black pegs) as the most recent guess,
     * then that index is removed from choiceList. This allows the AI to be 'smart' & narrow down the possible guesses.
     */
	protected void makeGuess() {
		
		//responsible for removing invalid permutation indexes
		if(!firstGuess) {
			if(choiceList.size()!=1) //prevents choiceList from deleting all of its permutation indexes (in case of player error)
				choiceList.remove(choice); //removes the most recently guessed permutation index
			
			//removes all remaining indexes of associated permutations that don't generate
			//the same score as the most recent guess
			for(int i=0; i<choiceList.size(); i++) { 
				if(getScore(choiceList.get(i))!=blackPegCount) { //makes comparison
					if(choiceList.size()!=1) { //prevents complete deletion
						choiceList.remove(i); //removes the permutation index
						i--; //prevents code from 'skipping' the next index after a removal
					}
				}
			}
		}

		//always selects the first permutation as the first guess, rather than Math.random()
		if(firstGuess) { // (reasoning is that many players might build their code by selecting colours in the order presented)
			choice=0;
			firstGuess=false;
		}
		
		else{ //if not first guess, selects a random remaining permutation index in choiceLlist
			choice=(int)(Math.random()*(choiceList.size()));
		}

		//sets the value of guess to the chosen permutation
		guess = allPermutations[choiceList.get(choice)].clone();

		//adds the most recent guess to the gameboard display
		addAllColourImages(panel, toColour(guess));
	}

	/**getScore method
     * This functional method counts & then returns the number of black pegs (direct matches) generated
     * between the permutation at the specified index and the most recent guess. This method assists
     * the AI in the 'second phase' of guessing by determining which permutations should be removed. 
     * 
     * Only black pegs are necessary for determining 'score', since all permutations are guaranteed to produce 4 pegs
     * (they all contain the same 4 colours used in the code, thus meaning all non-black pegs are white)
     *  
     * List of Local Variables:
     * count - the variable used to count the number of black pegs (direct matches) between
     *    the permutation at the specified index and the most recent guess </type int>
     * 
     *@param index - the index of the permutation in allPermutations to compare the most recent guess to </type int>
     *
     *@return count - the number of direct matches between the two codes </type int>
     */
	private int getScore(int index) {
		
		int count=0;
		
		for(int i=0; i<guess.length; i++) {
			if(allPermutations[index][i]==guess[i])
				count++;
		}
		return count;		
	}

}
