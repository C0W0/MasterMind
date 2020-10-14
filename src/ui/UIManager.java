package ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {

    private ArrayList<UIImage> uiImages;
    private ArrayList<UIButton> uiButtons;

    public UIManager(){
        uiImages = new ArrayList<>();
        uiButtons = new ArrayList<>();
    }

    public void render(Graphics graphics){
        for(UIObject o: uiImages)
            o.render(graphics);
        for(UIObject o: uiButtons)
            o.render(graphics);
    }

    public void onMouseClick(MouseEvent e){
        for(UIButton button: uiButtons)
            button.onMouseClick(e);
    }

    public void addUIImage(UIImage image){
        uiImages.add(image);
    }

    public void addUIButton(UIButton button){
        uiButtons.add(button);
    }

}
