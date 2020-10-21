package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class MainMenuState extends State{
	
	public MainMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(212, 200, 225, 600, Assets.playButton,()->game.setState(new HardAiState(game))));
		uiManager.addUIButton(new UIButton(212, 450, 225, 600, Assets.rulesAboutButton,()->game.setState(new MediumAiState(game))));
		
	}

	@Override
	public void init() {
		
		
		
		
		
	}

	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(Assets.mainMenu, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
