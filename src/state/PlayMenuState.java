package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class PlayMenuState extends State{
	
	public PlayMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(525, 20, 90, 90, Assets.backButton,()->game.setState(State.states[0])));
		uiManager.addUIButton(new UIButton(640, 25, 80, 350, Assets.optionsButton,()->game.setState(State.states[3])));
		uiManager.addUIButton(new UIButton(55, 150, 175, 535, Assets.playerCodebreakerButton,()->game.setState(State.states[4])));
		uiManager.addUIButton(new UIButton(435, 325, 175, 535, Assets.computerCodebreakerButton,()->game.setState(State.states[2])));
		uiManager.addUIButton(new UIButton(55, 508, 175, 535, Assets.pvpCodebreakerButton,()->game.setState(State.states[8])));
	}

	@Override
	public void init() {
		
		
		
		
		
	}

	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(Assets.playMenu, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
