package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class MainMenuState extends State{
	
	public MainMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(192, 215, 225, 640, Assets.playButton,()->game.setState(new PlayMenuState(game))));
		uiManager.addUIButton(new UIButton(192, 455, 165, 450, Assets.rulesAboutButton,()->game.setState(new MediumAiState(game))));
		uiManager.addUIButton(new UIButton(662, 455, 165, 170, Assets.exitButton,()->System.exit(0)));
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
