package ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {

    private ArrayList<UIButton> uiButtons;

    public UIManager(){
        uiButtons = new ArrayList<>();
    }

    public void render(Graphics graphics){
        for(UIButton o: uiButtons)
            o.render(graphics);
    }

    public void onMouseClick(MouseEvent e){
        for(UIButton button: uiButtons)
            button.onMouseClick(e);
    }

    public void addUIButton(UIButton button){
        uiButtons.add(button);
    }

}
