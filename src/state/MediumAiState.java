package state;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ai.AI;
import gamelogic.Score;
import gfx.Assets;
import ui.UIButton;




public class MediumAiState extends State {

	private int blackPegCount, whitePegCount;

	private int numberOfGuesses;
	private String lastGuess;
	private Score lastScore;
	private AI ai;



	private int[] guess = new int[4];



	public MediumAiState() {

		//uiManager.addUIButton(new UIButton);
		//uiManager.addUIButton(new UIButton(150, 150, 30, 30, Assets.peg_black, this::incrementBlackPegs));

		//getKnownColours(list);
	}






	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub

	}

	public void getKnownColours() {
		
		int count=0;
		/*
		while(numberOfGuesses<5 && count<4) {

			for(int j=0; j<guess.length; j++)
				guess[j]=guessNum;

			System.out.println("My guess is: ");
			display(guess);

			System.out.println("Please type feedback");
			score = x.next();

			if(score.equalsIgnoreCase("BBBB"))
				return true;

			for(int k=0; k<score.length(); k++) {
				if(score.charAt(k)=='b' || score.charAt(k)=='B') {
					list[count]=guessNum;
					count++;
				}
			}

			while(guessNum==4 && count<4) {
				list[count]=5;
				count++;
			}

			guessNum++;
		}
		*/
	}
	/*int[] guess = new int[4];
		int[] list = new int[4];
		int[][] possibleAnswers = new int[24][4];

		ArrayList<Integer> choiceList = new ArrayList<Integer>();

		String score;
		int count=0;
		int guessNum=0;
		int guessesMade=0;
		int choice, guessScore;

		while(guessNum<5 && count<4) {

			for(int j=0; j<guess.length; j++)
				guess[j]=guessNum;

			System.out.println("My guess is: ");
			display(guess);

			System.out.println("Please type feedback");
			score = x.next();

			if(score.equalsIgnoreCase("BBBB"))
				return true;

			for(int k=0; k<score.length(); k++) {
				if(score.charAt(k)=='b' || score.charAt(k)=='B') {
					list[count]=guessNum;
					count++;
				}
			}

			while(guessNum==4 && count<4) {
				list[count]=5;
				count++;
			}

			guessNum++;
		}

		generatePermuations(possibleAnswers,4,list, choiceList);

		//initializeChoices(choiceList);

		System.out.println(choiceList);

		while(guessNum<10) {

			if(guessesMade==0)
				choice=0;

			else //determines random out of remaining choices in choiceList
				choice=(int)(Math.random()*(choiceList.size()));

			//System.out.println("choice: "+choice);

			for(int i=0; i<guess.length; i++) //gets choice(index) stores in guess
				guess[i]=possibleAnswers[choiceList.get(choice)][i];

			if(guessesMade==0) //remove option that you just guessed from choiceList
				choiceList.remove(0);
			else {
				if(choiceList.size()!=1)
					choiceList.remove(choice);
			}

			System.out.println("My guess is: ");
			display(guess);

			System.out.println("Please type feedback");
			score = x.next();

			if(score.equalsIgnoreCase("BBBB"))
				return true;

			guessScore =  findNumBlacks(score);

			for(int i=0; i<choiceList.size(); i++) {

				if(getScore(possibleAnswers,guess,choiceList.get(i))!=guessScore) {

					if(choiceList.size()!=1) {
						choiceList.remove(i);
						i--;
					}
				}
			}
			System.out.println(choiceList);

			guessNum++;
			guessesMade++;
		}

		return false;*/
//	private void incrementBlackPegs(){
//		for(int i = 0; i < 4; i++){
//			if(currentPegs[i] == null){
//				blackPegCount++;
//				currentPegs[i] = Assets.peg_black;
//				return;
//			}
//		}
//	}
//
//	private void incrementWhitePegs(){
//		for(int i = 0; i < 4; i++){
//			if(currentPegs[i] == null){
//				whitePegCount++;
//				currentPegs[i] = Assets.peg_white;
//				return;
//			}
//		}
//	}
	
	private void addAllColourImages(BufferedImage[][] target, BufferedImage[] source){
        target[numberOfGuesses] = source;
    }
}
