package state;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class RulesState extends State{
	
	private int count=0;
	private BufferedImage[] rulesPages;
	
	
	/**RulesState method
     * Constructor method of the RulesState class
     * 
     * This method creates & places a 'forward' button and a 'back' button that allow the user to navigate between rule pages
     * (as well as navigate back to the rules/about menu)
     * 
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
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
	
	/**init method
     * This procedural method is inherited from the parent class, the State class
     * It is usually called by the use of a button, but not required for this state
     */
	@Override
	public void init() {
		
	}
	
	/**render method
     * This procedural method is inherited from the parent class, the State class
     * It constantly updates & renders graphics & buttons on the screen 45 times a second
     * 
     * @param graphics - the passed-in graphics context for drawing buffers on the screen </type Graphics>
     */
	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(rulesPages[count], 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
