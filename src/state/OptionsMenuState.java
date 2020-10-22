package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class OptionsMenuState extends State{
	
	private int dupColours = 4;
	private int maxGuesses = 10;
	
	public OptionsMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.backButton,()->game.setState(State.states[1])));
		
		for(int i=1; i<=4; i++) {
			int count1 = i;
			uiManager.addUIButton(new UIButton(115+95*(i-1), 270, 75, 75, Assets.numberButtons[i],()->setDupColours(count1)));
		}
		
		for(int i=6; i<=10; i++) {
			int count2 = i;
			uiManager.addUIButton(new UIButton(90+90*(i-6), 525, 75, 75, Assets.numberButtons[i],()->setMaxGuesses(count2)));
		}

	}

	@Override
	public void init() { }

	private void setDupColours(int dupColours) {
		this.dupColours = dupColours;
		for(int i = 4; i < 9; i++){
			GameState state = (GameState)State.states[i];
			state.setDupColour(dupColours);
		}
	}

	private void setMaxGuesses(int maxGuesses) {
		this.maxGuesses = maxGuesses;
		for(int i = 4; i < 9; i++){
			GameState state = (GameState)State.states[i];
			state.setMaxGuess(maxGuesses);
		}
	}

	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(Assets.optionsMenu, 0, 0, 1024, 768, null);
		
		graphics.drawImage(Assets.pointer, 116+95*(dupColours-1), 360, 73, 36, null);
		graphics.drawImage(Assets.pointer, 94+90*(maxGuesses-6), 615, 73, 36, null);
		uiManager.render(graphics);
	}
	
}
