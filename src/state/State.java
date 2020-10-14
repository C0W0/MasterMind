package state;

import ui.UIManager;

import java.awt.*;

public abstract class State {

    protected UIManager uiManager;

    public State(){
        uiManager = new UIManager();
    }

    public abstract void init();

    public abstract void render(Graphics graphics);

    public UIManager getUiManager() {
        return uiManager;
    }
}
