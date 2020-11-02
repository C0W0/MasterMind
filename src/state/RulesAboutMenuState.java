/*==========================================================================================
Codebreaker
Terry Zha and Jonathan Xie
November 2, 2020
Java 13.0.2
The Rules/About Menu class of the Code Breaker
This is the state class that displays the "Rules/About Menu" on the GUI,
including all graphics & buttons associated with managing user input. 
This state allow the user to navigate to the "About Us" state, as well as the "Rules" state
============================================================================================
*/
package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class RulesAboutMenuState extends State{
	
	/**RulesAboutMenuState method
     * Constructor method of the RulesAboutMenuState class
     * 
     * This method creates & places the 'rules' button and the 'about' button, which allow
     * the user to navigate to each button's corresponding state ('Rules Page' & 'About Us Page')
     * 
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
	public RulesAboutMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.backButton,()->game.setState(State.states[0])));
		uiManager.addUIButton(new UIButton(95, 155, 245, 400, Assets.rulesButton,()->game.setState(State.states[10])));
		uiManager.addUIButton(new UIButton(530, 155, 246, 400, Assets.aboutButton,()->game.setState(State.states[11])));
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
		
		graphics.drawImage(Assets.rulesAboutMenu, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
