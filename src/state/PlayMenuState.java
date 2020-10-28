package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class PlayMenuState extends State{
	
	/**PlayMenuState method
     * Constructor method of the PlayMenuState class
     * 
     * This method creates & places 5 buttons available to the user: 'Player Codebreaker', 'Computer Codebreaker',
     * 'Player vs Player Codebreaker', as well as an 'Options' and 'Back' button. Each of these buttons will transfer
     * the player to their corresponding state
     * 
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
	public PlayMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(525, 20, 90, 90, Assets.backButton,()->game.setState(State.states[0])));
		uiManager.addUIButton(new UIButton(640, 25, 80, 350, Assets.optionsButton,()->game.setState(State.states[3])));
		uiManager.addUIButton(new UIButton(55, 150, 175, 535, Assets.playerCodebreakerButton,()->game.setState(State.states[4])));
		uiManager.addUIButton(new UIButton(435, 325, 175, 535, Assets.computerCodebreakerButton,()->game.setState(State.states[2])));
		uiManager.addUIButton(new UIButton(55, 508, 175, 535, Assets.pvpCodebreakerButton,()->game.setState(State.states[8])));
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
		
		graphics.drawImage(Assets.playMenu, 0, 0, 1024, 768, null);
		uiManager.render(graphics);
	}
	
}
