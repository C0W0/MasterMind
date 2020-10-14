package ui;

import java.awt.*;

public abstract class UIObject {

    protected int x, y, width, height;
    protected boolean hovering = false;
    protected Rectangle bounds;

    public UIObject(int x, int y, int height, int width){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    public abstract void render(Graphics graphics);

}
