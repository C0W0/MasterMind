package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class RulesAboutMenuState extends State{
	
	public RulesAboutMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.backButton,()->game.setState(State.states[0])));
		uiManager.addUIButton(new UIButton(95, 155, 245, 400, Assets.rulesButton,()->game.setState(State.states[10])));
		uiManager.addUIButton(new UIButton(530, 155, 246, 400, Assets.aboutButton,()->game.setState(State.states[11])));
	}

	@Override
	public void init() {
		
		
		
		
		
	}

	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(Assets.rulesAboutMenu, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
