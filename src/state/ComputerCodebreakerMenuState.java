package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class ComputerCodebreakerMenuState extends State{
	
	public ComputerCodebreakerMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.back_button,()->game.setState(State.states[1])));
		uiManager.addUIButton(new UIButton(60, 150, 165, 515, Assets.easyButton,()->game.setState(State.states[5])));
		uiManager.addUIButton(new UIButton(435, 325, 165, 515, Assets.mediumButton,()->game.setState(State.states[6])));
		uiManager.addUIButton(new UIButton(60, 508, 160, 515, Assets.hardButton,()->game.setState(State.states[7])));
	}

	@Override
	public void init() {
		
		
		
		
		
	}

	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(Assets.computerCodebreakerMenu, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
