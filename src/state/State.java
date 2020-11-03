/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 27, 2020
Java 13.0.2
The abstract parent class for every game state
Contains necessary fields and abstract methods for the basic
functionality of a state

List of Global Variables:
States - an array of states that contains all states required for the
	game. This is a static variable and will be referred for state
	switching </type State[]>
uiManager - An object that stores all of the buttons and tracks button
     </type BufferedImage[][]>
game - the reference (pointer) of the main game object. It is passed and
	stored in every single state for state switching </type Game>
===============================================================================
*/

package state;

import ui.UIManager;
import gamelogic.Game;
import java.awt.*;

public abstract class State {
	
    public static State[] states = new State[12];

    protected UIManager uiManager;
    protected Game game;

	/**State method
	 * Constructor method of the State class
	 * A reference of the main game object is passed in here.
	 *
	 * @param game - the passed-in object of the custom-made Game class </type Game>
	 */
	public State(Game game){
    	this.game = game;
    	uiManager = new UIManager();
    }

	/**init method
	 * This abstract procedural method will be implemented in each state.
	 * It is invoked by clicking buttons and switching states. This method
	 * should contain the procedure to refresh critical variables within
	 * this state object.
	 */
    public abstract void init();

	/**render method
	 * This abstract procedural method will be implemented in each state.
	 * It is called 45 times a second in the main game object.
	 * It should contain the procedure to updates & renders graphics & buttons
	 * on the screen
	 *
	 * @param graphics - the passed-in graphics context for drawing buffers on the screen </type Graphics>
	 */
    public abstract void render(Graphics graphics);

	/**getUiManager method
	 * This functional method returns out the uiManager object of the state
	 *
	 * @return uiManager - the uiManager object of the state </type UIManager>
	 */
	public UIManager getUiManager() {
        return uiManager;
    }

	/**generateState method
	 * This procedural method creates all of the needed states for the game
	 * and stores them in the state array.
	 *
	 * @param game - the passed-in object of the custom-made Game class </type Game>
	 */
	public static void generateStates(Game game) {
    	
    	states[0]= new MainMenuState(game);
    	states[1]= new PlayMenuState(game);
    	states[2]= new ComputerCodebreakerMenuState(game);
    	states[3]= new OptionsMenuState(game);
    	states[4]= new PlayerDecodeState(game);
    	states[5]= new EasyAiState(game);
    	states[6]= new MediumAiState(game);
    	states[7]= new HardAiState(game);
    	states[8]= new PVPState(game);
    	states[9]= new RulesAboutMenuState(game);
    	states[10]= new RulesState(game);
    	states[11]= new AboutUsState(game);
    	
    }
    
    
    
    
}

