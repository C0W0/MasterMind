package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class AboutUsState extends State{
	
	public AboutUsState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.back_button,()->game.setState(State.states[9])));
	}

	@Override
	public void init() {
		
		
		
		
		
	}

	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(Assets.aboutUsPage, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
