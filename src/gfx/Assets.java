package gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Assets {

    public static BufferedImage empty_slot;
    public static BufferedImage back_button, restart_button, forward_button;
    public static BufferedImage playButton, rulesAboutButton, exitButton, optionsButton, computerCodebreakerButton, 
    playerCodebreakerButton, pvpCodebreakerButton, easyButton, mediumButton, hardButton, rulesButton, aboutButton;
    public static BufferedImage black_peg_button, white_peg_button;
    public static BufferedImage peg_black, peg_white;
    public static BufferedImage confirm_button, delete_button;
    public static BufferedImage aiGameboard, playerGameboard, pvpGameboard;
    public static BufferedImage mainMenu,playMenu,computerCodebreakerMenu,rulesAboutMenu,aboutUsPage;
    public static BufferedImage emptyGameBoard;
    public static BufferedImage[] colours, rulesPages, numberButtons;

    public static void init() {

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
        forward_button = loadImage("/texture/forward_button.png");
        
        playButton = loadImage("/texture/play_button.png");
        rulesAboutButton = loadImage("/texture/rules_about_button.png");
        exitButton = loadImage("/texture/exit_button.png");
        
        optionsButton = loadImage("/texture/options_button.png");
        playerCodebreakerButton = loadImage("/texture/player_codebreaker_button.png");
        computerCodebreakerButton = loadImage("/texture/computer_codebreaker_button.png");
        pvpCodebreakerButton = loadImage("/texture/pvp_codebreaker_button.png");

        easyButton = loadImage("/texture/easy_button.png");
        mediumButton = loadImage("/texture/medium_button.png");
        hardButton = loadImage("/texture/hard_button.png");
        
        rulesButton = loadImage("/texture/rules_button.png");
        aboutButton = loadImage("/texture/about_button.png");

        mainMenu = loadImage("/texture/main_menu.png");
        playMenu = loadImage("/texture/play_menu.png");
        computerCodebreakerMenu = loadImage("/texture/computer_codebreaker_menu.png");
        rulesAboutMenu = loadImage("/texture/rules_about_menu.png");
        aboutUsPage = loadImage("/texture/about_us_page.png");
        
        rulesPages = new BufferedImage[3];
        rulesPages[0] = loadImage("/texture/rules_page_1.png");
        rulesPages[1] = loadImage("/texture/rules_page_2.png");
        rulesPages[2] = loadImage("/texture/rules_page_3.png");
        
        numberButtons = new BufferedImage[11];
        numberButtons[1] = loadImage("/texture/one.png");
        numberButtons[2] = loadImage("/texture/two.png");
        numberButtons[3] = loadImage("/texture/three.png");
        numberButtons[4] = loadImage("/texture/four.png");
        numberButtons[6] = loadImage("/texture/six.png");
        numberButtons[7] = loadImage("/texture/seven.png");
        numberButtons[8] = loadImage("/texture/eight.png");
        numberButtons[9] = loadImage("/texture/nine.png");
        numberButtons[10] = loadImage("/texture/ten.png");
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
