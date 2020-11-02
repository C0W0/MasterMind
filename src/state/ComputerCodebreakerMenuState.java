/*==========================================================================================
Codebreaker
Terry Zha and Jonathan Xie
November 2, 2020
Java 13.0.2
The Computer Codebreaker menu class of the Code Breaker
This is the state class that displays the "Computer Codebreaker Menu" on the GUI,
including all graphics & buttons associated with managing user input
This state allows the user to select which AI difficulty level (easy, medium, hard)
they would like to play against in the computer codebreaker gamemode
============================================================================================
*/
package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class ComputerCodebreakerMenuState extends State{
	
	/**ComputerCodebreakerMenuState method
     * Constructor method of the ComputerCodebreakerMenuState class
     * 
     * This method creates & places 4 buttons available to the user: 'Easy', 'Medium' and 'Hard',
     * as well as an 'Options' and 'Back' button. Each of these buttons will transfer the player 
     * to their corresponding state
     * 
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
	public ComputerCodebreakerMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.backButton,()->game.setState(State.states[1])));
		uiManager.addUIButton(new UIButton(60, 150, 165, 515, Assets.easyButton,()->game.setState(State.states[5])));
		uiManager.addUIButton(new UIButton(437, 325, 165, 515, Assets.mediumButton,()->game.setState(State.states[6])));
		uiManager.addUIButton(new UIButton(60, 508, 160, 515, Assets.hardButton,()->game.setState(State.states[7])));
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
		
		graphics.drawImage(Assets.computerCodebreakerMenu, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
