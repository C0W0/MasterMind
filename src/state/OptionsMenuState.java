package state;

import java.awt.Graphics;
import gamelogic.Game;
import gfx.Assets;
import ui.UIButton;

public class OptionsMenuState extends State{
	
	private int dupColours = 4;
	private int maxGuesses = 10;
	
	/**OptionsMenuState method
     * Constructor method of the OptionsMenuState class
     * 
     * This method creates & places 4 buttons that allow the user to adjust the number of duplicate colours allowed in the code (from 1-4),
     * as well as 5 buttons that allow the adjustment of the maximum permitted guesses for the codebreaker (from 6-10)
     * 
     * List of Local Variables
     * count1 - temporary integer that determines the functional return value of the duplicate colour buttons </type int>
     * count2 - temporary integer that determines the functional return value of the guess limit buttons </type int>
     * 
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
	public OptionsMenuState(Game game){
		
		super(game);
		
		uiManager.addUIButton(new UIButton(670, 16, 90, 90, Assets.backButton,()->game.setState(State.states[1])));
		
		for(int i=1; i<=4; i++) {
			int count1 = i;
			uiManager.addUIButton(new UIButton(115+95*(i-1), 270, 75, 75, Assets.numberButtons[i],()->setDupColours(count1)));
		}
		
		for(int i=6; i<=10; i++) {
			int count2 = i;
			uiManager.addUIButton(new UIButton(90+90*(i-6), 525, 75, 75, Assets.numberButtons[i],()->setMaxGuesses(count2)));
		}

	}
	
	/**init method
     * This procedural method is inherited from the parent class, the State class
     * It is usually called by the use of a button, but not required for this state
     */
	@Override
	public void init() { 
		
	}

	/**setDupColours method
     * This procedural method confirms the user's input of the number of duplicate colours allowed
     * (affects code generation for player codebreaker)
     * 
     * List of Local Variables:
     *  state - the casted object (from type State) that allows access to the setDupColour method </type GameState>
     * 
     * @param dupColours - the number (from 1-4) of duplicate colours the player chooses (determined by button pressing) </type int>
     */
	private void setDupColours(int dupColours) {
		this.dupColours = dupColours;
		for(int i = 4; i < 9; i++){
			GameState state = (GameState)State.states[i]; //casts the object state from type State to GameState to access the GameState method
			state.setDupColour(dupColours); //calls the now-accessible setDupColour method
		}
	}

	/**setDupColours method
     * This procedural method confirms the user's input of the maximum number of permitted guesses
     * (affects all gameplay-states & determines when the codebreaker loss message will appear)
     * 
     * List of Local Variables:
     * state - the casted object (from type State) that allows access to the setMaxGuess method </type GameState>
     * 
     * @param maxGuesses - the number (from 6-10) of maximum permitted guesses the player chooses (determined by button pressing) </type int>
     */
	private void setMaxGuesses(int maxGuesses) {
		this.maxGuesses = maxGuesses;
		for(int i = 4; i < 9; i++){
			GameState state = (GameState)State.states[i];
			state.setMaxGuess(maxGuesses);
		}
	}
	
	/**render method
     * This procedural method is inherited from the parent class, the State class
     * It constantly updates & renders graphics & buttons on the screen 45 times a second
     * 
     * @param graphics - the passed-in graphics context for drawing buffers on the screen </type Graphics>
     */
	@Override
	public void render(Graphics graphics) {
		
		graphics.drawImage(Assets.optionsMenu, 0, 0, 1024, 768, null);
		
		graphics.drawImage(Assets.pointer, 116+95*(dupColours-1), 360, 73, 36, null);
		graphics.drawImage(Assets.pointer, 94+90*(maxGuesses-6), 615, 73, 36, null);
		uiManager.render(graphics);
	}
	
}
