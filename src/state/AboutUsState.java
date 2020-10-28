package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class AboutUsState extends State{
	
	/**AboutUsState method
     * Constructor method of the AboutUsState class
     * 
     * This method creates & places a 'back button' that takes the user back to the RulesAboutMenuState
     * 
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
	public AboutUsState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.backButton,()->game.setState(State.states[9])));
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
		
		graphics.drawImage(Assets.aboutUsPage, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
