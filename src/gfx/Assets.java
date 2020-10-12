package gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Assets {

    public static BufferedImage sample;

    public static void init(){

        sample = loadImage("/sample.png");

    }

    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(Assets.class.getResource(path));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
