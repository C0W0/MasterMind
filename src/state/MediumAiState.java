package state;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import gamelogic.Score;
import gfx.Assets;
import ui.UIButton;


public class MediumAiState extends GameState {
	
	final private int MAXGUESSES = 10;
	private BufferedImage[] currentPegs;
	private BufferedImage[][] guessDisplay;
	private int blackPegCount, whitePegCount;

	private boolean generated = false;
	private boolean firstGuess = true;
	
	private int numberOfGuesses,coloursFound,choice;

	private int[] guess = new int[4];
	private int[] knownColours = new int[4];
	private int[][] allPermutations = new int[24][4];

	private ArrayList<Integer> choiceList = new ArrayList<Integer>();

	public MediumAiState() {
		
		currentPegs= new BufferedImage[4];
		guessDisplay = new BufferedImage[MAXGUESSES][4];
		
		uiManager.addUIButton(new UIButton(150, 150, 30, 30, Assets.peg_black, this::incrementBlackPegs));
		uiManager.addUIButton(new UIButton(250, 150, 30, 30, Assets.peg_white, this::incrementWhitePegs));
		uiManager.addUIButton(new UIButton(150, 350, 30, 30, Assets.yes, this::confirmFeedback));
		uiManager.addUIButton(new UIButton(250, 350, 30, 30, Assets.no, this::removeFeedback));
		
	}
	
	public void initiateVariables() {
		
		generated=false;
		firstGuess=true;
		blackPegCount=whitePegCount=0;
		
		numberOfGuesses=0;
		coloursFound = 0;
		
		currentPegs= new BufferedImage[4];
		guessDisplay = new BufferedImage[MAXGUESSES][4];
	}
	
	private void incrementBlackPegs(){
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

	private void confirmFeedback() {
		
		numberOfGuesses++;
		
		if(blackPegCount==4) {
			System.out.println("decoded");
			return;
		}
		
		else if(numberOfGuesses>MAXGUESSES) {
			System.out.println("not decoded");
			return;
		}
		
		if(coloursFound<4)
			updateColoursFound();
		
		if(numberOfGuesses==6 && coloursFound<4)
			fillRemainingColour();
			
		else if(numberOfGuesses<=6 && coloursFound<4)			
			findKnownColours();
		
		if(coloursFound==4) {
			
			if(!generated) {
				System.out.println("generated");
				generatePermutations(4,knownColours);
				generated = true;
				System.out.println(Arrays.deepToString(allPermutations));
			}
			makeGuess();
			System.out.println(choiceList);
		}
		
		System.out.println("made guess #"+numberOfGuesses);
		System.out.println("coloursFound: "+coloursFound);
		System.out.println("knownColours: "+Arrays.toString(knownColours));
		
		removeFeedback();
	}

	private void removeFeedback() {
		currentPegs = new BufferedImage[4];
		whitePegCount = blackPegCount = 0;
	}


	@Override
	protected void start() {
		initiateVariables();
		confirmFeedback();
	}


	@Override
	protected void postRender(Graphics graphics) {
		
		for(int i=0; i<currentPegs.length; i++) { //draws black/white pegs (during selection)
			if(currentPegs[i]!=null)
				graphics.drawImage(currentPegs[i], 100+50*i, 250, 30, 30, null);
		}
		
		for(int i=0; i<guessDisplay.length; i++) {
			for(int j=0; j<guessDisplay[0].length; j++) {
				if(guessDisplay[i][j]!=null)
					graphics.drawImage(guessDisplay[i][j], 300+50*j, 250+50*i, 30, 30, null);	
			}
		}
	}
	

	public void findKnownColours() {
		
		for(int i=0; i<guess.length; i++)
			guess[i]=numberOfGuesses-1;

		updateDisplay();
	}
	
	public void updateColoursFound(){
		for(int i=0; i<blackPegCount; i++) {
			knownColours[coloursFound]=numberOfGuesses-2;
			coloursFound++;
		}
	}
	
	public void fillRemainingColour() {
		while(numberOfGuesses==6 && coloursFound<4) {
			knownColours[coloursFound]=5;
			coloursFound++;
		}
	}
	public void generatePermutations(int n, int[] elements) {

		if(n == 1) {
			storeArray(elements);
			choiceList.add(choiceList.size());
		}

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
	
	public void swap(int[] input, int n1, int n2) {
		int temp = input[n1];
		input[n1] = input[n2];
		input[n2] = temp;
	}

	public void storeArray(int[] list) {

		for(int i=0; i<list.length; i++) {
			allPermutations[choiceList.size()][i]=list[i];
		}

	}
	
	public void makeGuess() {
		
		if(!firstGuess) {
			removeLastChoice();
			removeChoices();
		}
		getChoiceIndex();
		
		createGuess();
		
		updateDisplay();
	}

		
	public void getChoiceIndex() {
		
		if(firstGuess) {
			choice=0;
			firstGuess=false;
		}
		else
			choice=(int)(Math.random()*(choiceList.size()));
	}
	
	public void createGuess() {
		
		for(int i=0; i<guess.length; i++) //gets choice(index) stores in guess
			guess[i]=allPermutations[choiceList.get(choice)][i];
	}
	
	public void updateDisplay() {
		
		for(int i=0; i<guess.length; i++) 
			guessDisplay[numberOfGuesses-1][i] = Assets.colours[guess[i]];
	}
	
	public void removeChoices() {
		
		for(int i=0; i<choiceList.size(); i++) {

			if(getScore(choiceList.get(i))!=blackPegCount) {

				if(choiceList.size()!=1) {
						choiceList.remove(i);
						i--;
				}
			}
		}		
	}
	
	public int getScore(int index) {
		
		int count=0;
		
		for(int i=0; i<guess.length; i++) {
			if(allPermutations[index][i]==guess[i])
				count++;
		}
		return count;		
	}
	
	public void removeLastChoice() {
		if(choiceList.size()!=1)
			choiceList.remove(choice);
	}
}
