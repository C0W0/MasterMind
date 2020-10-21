package ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton {

    private int x, y, width, height;
    private Rectangle bounds;
    private ClickListener clicker;
    private final BufferedImage image;

    public UIButton(int x, int y, int height, int width, BufferedImage image, ClickListener clicker) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
        this.image = image;
        this.clicker = clicker;
    }

    void onMouseClick(MouseEvent e){
        if(bounds.contains(e.getX(), e.getY()))
            clicker.onClick();

    }

    void render(Graphics graphics) {
        graphics.drawImage(image, x, y, width, height, null);
    }

}
