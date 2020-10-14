package gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Assets {

    public static BufferedImage square_B, square_G, square_O, square_P, square_R, square_Y;
    public static BufferedImage peg_black, peg_white, yes, no;
    public static BufferedImage[] colours;

    public static void init(){

        colours = new BufferedImage[6];
        colours[0] = square_B = loadImage("/texture/blue.png");
        colours[1] = square_G = loadImage("/texture/green.png");
        colours[2] = square_O = loadImage("/texture/orange.png");
        colours[3] = square_P = loadImage("/texture/purple.png");
        colours[4] = square_R = loadImage("/texture/red.png");
        colours[5] = square_Y = loadImage("/texture/yellow.png");
        peg_black = loadImage("/texture/black.png");
        peg_white = loadImage("/texture/white.png");
        yes = loadImage("/texture/yes.png");
        no = loadImage("/texture/no.png");

    }

    private static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(Assets.class.getResource(path));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
