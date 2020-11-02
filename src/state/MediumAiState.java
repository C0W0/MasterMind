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
			message = messages[(int)(Math.random()*3+1)];
			isGameActive = false;
			return;
		}
		
		else if(numberOfGuesses>maxGuess) {
			message = messages[(int)(Math.random()*3+4)];
			isGameActive = false;
			playerWin = true;
			return;
		}
		
		if(coloursFound<4) {
			updateColoursFound();
		}
		if(numberOfGuesses != 1){
			if(coloursFound<4){
				message = messages[(int)(Math.random()*5+7)];
			}else {
				message = messages[(int)(Math.random()*11+12)];
			}
		}

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
