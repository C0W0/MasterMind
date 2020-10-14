package ui;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton extends UIImage {

    private ClickListener clicker;

    public UIButton(int x, int y, int height, int width, BufferedImage image, ClickListener clicker) {
        super(x, y, height, width, image);
        this.clicker = clicker;
    }

    public void onMouseClick(MouseEvent e){
        if(bounds.contains(e.getX(), e.getY()))
            clicker.onClick();

    }

}
