package gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Assets {

    public static BufferedImage empty_slot;
    public static BufferedImage back_button, restart_button;
    public static BufferedImage black_peg_button, white_peg_button;
    public static BufferedImage peg_black, peg_white, yes, no;
    public static BufferedImage confirm_button, delete_button;
    public static BufferedImage aiGameboard, playerGameboard, pvpGameboard;
    public static BufferedImage emptyGameBoard;
    public static BufferedImage[] colours;

    public static void init(){

        colours = new BufferedImage[6];
        colours[0] = loadImage("/texture/blue.png");
        colours[1] = loadImage("/texture/green.png");
        colours[2] = loadImage("/texture/orange.png");
        colours[3] = loadImage("/texture/purple.png");
        colours[4] = loadImage("/texture/red.png");
        colours[5] = loadImage("/texture/yellow.png");
        peg_black = loadImage("/texture/black.png");
        peg_white = loadImage("/texture/white.png");
        empty_slot = loadImage("/texture/brown.png");
        yes = loadImage("/texture/yes.png");
        no = loadImage("/texture/no.png");
        emptyGameBoard = loadImage("/texture/empty_gameboard_background.png");
        aiGameboard = loadImage("/texture/computer_codebreaker_board.png");
        playerGameboard = loadImage("/texture/player_codebreaker_board.png");
        pvpGameboard = loadImage("/texture/pvp_codebreaker_board.png");
        back_button = loadImage("/texture/back_button.png");
        restart_button = loadImage("/texture/restart_button.png");
        black_peg_button = loadImage("/texture/black_peg_button.png");
        white_peg_button = loadImage("/texture/white_peg_button.png");
        confirm_button = loadImage("/texture/confirm_button.png");
        delete_button = loadImage("/texture/delete_button.png");
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
