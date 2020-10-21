package state;

import ui.UIManager;
import gamelogic.Game;
import java.awt.*;

public abstract class State {
	
    public static State[] states = new State[12];

    protected UIManager uiManager;
    protected Game game;//refers game variable for all children classes
    
    public State(Game game){
    	this.game = game; //refers game variable within this CLASS
    	uiManager = new UIManager();
    }


    
    public abstract void init();

    public abstract void render(Graphics graphics);

    public UIManager getUiManager() {
        return uiManager;
    }
    
    public static void generateStates(Game game) {
    	
    	states[0]= new MainMenuState(game);
    	states[1]= new PlayMenuState(game);
    	states[2]= new ComputerCodebreakerMenuState(game);
    	//states[3]= new OptionsMenuState(game);
    	states[4]= new PlayerDecodeState(game);
    	states[5]= new EasyAiState(game);
    	states[6]= new MediumAiState(game);
    	states[7]= new HardAiState(game);
    	states[8]= new PVPState(game);
    	states[9]= new RulesAboutMenuState(game);
    	//states[10]= new RulesState(game);
    	//states[11]= new AboutUsState(game);
    	
    }
    
    
    
    
}

