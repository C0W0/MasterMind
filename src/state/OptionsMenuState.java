package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class OptionsMenuState extends State{
	
	private int dupColours = 1;
	private int maxGuesses = 10;
	
	public OptionsMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.backButton,()->game.setState(State.states[1])));
		
		for(int i=1; i<=4; i++) {
			int count1 = i;
			uiManager.addUIButton(new UIButton(112+95*(i-1), 260, 105, 81, Assets.numberButtons[i],()->dupColours=count1));
		}
		
		for(int i=6; i<=9; i++) {
			int count2 = i;
			uiManager.addUIButton(new UIButton(90+85*(i-6), 525, 105, 81, Assets.numberButtons[i],()->maxGuesses=count2));
		}
		
		uiManager.addUIButton(new UIButton(420, 525, 105, 106, Assets.numberButtons[10],()->maxGuesses=10));

	}

	@Override
	public void init() {
		
		
		
		
		
	}

	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(Assets.optionsMenu, 0, 0, 1024, 768, null);
		
		graphics.drawImage(Assets.pointer, 116+95*(dupColours-1), 360, 73, 36, null);
		graphics.drawImage(Assets.pointer, 94+85*(maxGuesses-6), 625, 73, 36, null);
		uiManager.render(graphics);
	}
	
}
