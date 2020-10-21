package state;

import ui.UIManager;
import gamelogic.Game;
import java.awt.*;

public abstract class State {
	
	
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
}
