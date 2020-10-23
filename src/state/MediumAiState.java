package state;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;


public class MediumAiState extends GameState {

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

	public MediumAiState(Game game) {
		super(game);

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
	    if(isGameActive)
            for(int i = 0; i < 4; i++){
                if(currentPegs[i] == null){
                    blackPegCount++;
                    currentPegs[i] = Assets.pegBlack;
                    return;
                }
            }
	}

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
	
	protected void addAllColourImages(BufferedImage[][] target, BufferedImage[] source){
        target[numberOfGuesses-1] = source;
    }

	private void confirmFeedback() {
		if(!isGameActive)
			return;

		BufferedImage[] pegsImage = new BufferedImage[4];
		for(int i = 0; i < blackPegCount; i++)
			pegsImage[i] = Assets.pegBlack;
		for(int i = blackPegCount; i < blackPegCount+whitePegCount; i++)
			pegsImage[i] = Assets.pegWhite;

		if(numberOfGuesses != 0)
			addAllColourImages(allPegs, pegsImage);
		numberOfGuesses++;

		if(blackPegCount==4) {
			showCode(guess);
			isGameActive = false;
			return;
		}
		
		else if(numberOfGuesses>maxGuess) {
			System.out.println("not decoded");
			isGameActive = false;
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

		addAllColourImages(panel, toColour(guess));
	}


	private int getScore(int index) {
		
		int count=0;
		
		for(int i=0; i<guess.length; i++) {
			if(allPermutations[index][i]==guess[i])
				count++;
		}
		return count;		
	}

}
