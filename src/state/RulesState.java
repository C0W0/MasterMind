package state;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class RulesState extends State{
	
	private int count=0;
	private BufferedImage[] rulesPages;
	
	
	
	public RulesState(Game game){
		
		super(game);
		
		rulesPages = new BufferedImage[3];
		
		for(int i=0; i<rulesPages.length; i++)
			rulesPages[i] = Assets.rulesPages[i];
		
		
		uiManager.addUIButton(new UIButton(50, 32, 90, 90, Assets.backButton,()->{
			if(count==0)
				game.setState(State.states[9]);
			else
				count--;
		} ));

		uiManager.addUIButton(new UIButton(884, 32, 90, 90, Assets.forwardButton,()->{
			if(count==2){
				game.setState(State.states[9]);
				count = 0;
			}
			else
				count++;
		}));


	}

	@Override
	public void init() {
		
		
		
		
		
	}

	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(rulesPages[count], 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
