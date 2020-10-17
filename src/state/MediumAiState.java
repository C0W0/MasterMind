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
	private int blackPegCount, whitePegCount;

	private boolean generated = false;
	private boolean firstGuess = true;
	
	private int numberOfGuesses,coloursFound;
	protected int choice;

	protected int[] guess;
	private int[] knownColours;
	protected int[][] allPermutations;

	protected ArrayList<Integer> choiceList;

	public MediumAiState() {
		super();

		currentPegs= new BufferedImage[4];

		uiManager.addUIButton(new UIButton(90, 270, 55, 55, Assets.black_peg_button, this::incrementBlackPegs));
		uiManager.addUIButton(new UIButton(155, 270, 55, 55, Assets.white_peg_button, this::incrementWhitePegs));
		uiManager.addUIButton(new UIButton(90, 350, 55, 150, Assets.confirm_button, this::confirmFeedback));
		uiManager.addUIButton(new UIButton(265, 350, 55, 150, Assets.delete_button, this::removeFeedback));

		choiceList = new ArrayList<>();
		guess = new int[4];
		knownColours = new int[4];
		allPermutations = new int[24][4];
	}


	@Override
	protected void start() {

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

		confirmFeedback();
	}


	@Override
	protected void postRender(Graphics graphics) {
        graphics.drawImage(Assets.aiGameboard, 0, 0, cornerWidth, cornerHeight, null);
		for(int i=0; i<currentPegs.length; i++) //draws black/white pegs (during selection)
			if(currentPegs[i]!=null)
				graphics.drawImage(currentPegs[i], 235+45*i, 275, 40, 40, null);
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
	
	protected void addAllColourImages(BufferedImage[][] target, BufferedImage[] source){
        target[numberOfGuesses-1] = source;
    }

	private void confirmFeedback() {

		BufferedImage[] pegsImage = new BufferedImage[4];
		for(int i = 0; i < blackPegCount; i++)
			pegsImage[i] = Assets.peg_black;
		for(int i = blackPegCount; i < blackPegCount+whitePegCount; i++)
			pegsImage[i] = Assets.peg_white;

		if(numberOfGuesses != 0)
			addAllColourImages(allPegs, pegsImage);
		numberOfGuesses++;

		if(blackPegCount==4) {
			showCode(guess);
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
				generatePermutations(4,knownColours);
				generated = true;
			}
			makeGuess();
		}
		
		removeFeedback();
	}

	private void removeFeedback() {
		currentPegs = new BufferedImage[4];
		whitePegCount = blackPegCount = 0;
	}
	

	private void findKnownColours() {
		Arrays.fill(guess, numberOfGuesses - 1);
		System.out.println("called "+numberOfGuesses);
		addAllColourImages(panel, toColour(guess));
	}
	
	private void updateColoursFound(){
		for(int i=0; i<blackPegCount; i++) {
			knownColours[coloursFound]=numberOfGuesses-2;
			coloursFound++;
		}
	}
	
	private void fillRemainingColour() {
		while(numberOfGuesses==6 && coloursFound<4) {
			knownColours[coloursFound]=5;
			coloursFound++;
		}
	}
	private void generatePermutations(int n, int[] elements) {

		if(n == 1) {
			allPermutations[choiceList.size()] = elements.clone();
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
	
	private void swap(int[] input, int n1, int n2) {
		int temp = input[n1];
		input[n1] = input[n2];
		input[n2] = temp;
	}

	protected void makeGuess() {
		
		if(!firstGuess) {
			if(choiceList.size()!=1)
				choiceList.remove(choice);

			for(int i=0; i<choiceList.size(); i++) {
				if(getScore(choiceList.get(i))!=blackPegCount) {
					if(choiceList.size()!=1) {
						choiceList.remove(i);
						i--;
					}
				}
			}
		}

		if(firstGuess) {
			choice=0;
			firstGuess=false;
		}
		else{
			choice=(int)(Math.random()*(choiceList.size()));
		}

		guess = allPermutations[choiceList.get(choice)].clone();

		System.out.println("called B "+numberOfGuesses);
		addAllColourImages(panel, toColour(guess));
	}


	protected int getScore(int index) {
		
		int count=0;
		
		for(int i=0; i<guess.length; i++) {
			if(allPermutations[index][i]==guess[i])
				count++;
		}
		return count;		
	}

}
