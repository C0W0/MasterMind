/*==========================================================================================
Codebreaker
Terry Zha and Jonathan Xie
November 2, 2020
Java 13.0.2
The Main Menu class of the Code Breaker
This is the state class that displays the "Main Menu" on the GUI,
including all graphics & buttons associated with managing user input. 
This state is automatically called/displayed upon launching the program, and allows
the user to navigate to the play menu, rules/about menu, or terminate the program
============================================================================================
*/
package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class MainMenuState extends State{
	
	/**MainMenuState method
     * Constructor method of the MainMenuState class
     * 
     * This method creates & places the 3 buttons available to the user in the main menu: 'Play',
     * 'Rules/About', and 'Exit'. Each of these buttons will transfer their player to their corresponding
     * state, or terminate the program
     * 
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
	public MainMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(192, 215, 225, 640, Assets.playButton,()->game.setState(State.states[1])));
		uiManager.addUIButton(new UIButton(192, 455, 165, 450, Assets.rulesAboutButton,()->game.setState(State.states[9])));
		uiManager.addUIButton(new UIButton(662, 455, 165, 170, Assets.exitButton,()->System.exit(0)));
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
		
		graphics.drawImage(Assets.mainMenu, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
