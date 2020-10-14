package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIImage extends UIObject {

    private final BufferedImage image;

    public UIImage(int x, int y, int height, int width, BufferedImage image) {
        super(x, y, height, width);
        this.image = image;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image, x, y, width, height, null);
    }
}
