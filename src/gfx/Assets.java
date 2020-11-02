/*==========================================================================================
Codebreaker
Terry Zha and Jonathan Xie
November 1, 2020
Java 13.0.2
The assets class of the Code Breaker
This class imports all graphics files (type png) and stores them in BufferedImage variables for GUI usage

List of Global Variables:
arial28 - size 28 arial font used to output computer display messages <type Font>
arial30 - size 20 arial font used to output computer display messages  <type Font>
emptySlot - graphic used to indicate an empty slot for colours to be displayed on the gameboard <type BufferedImage>
pointer - graphic used to indicate/point to the selected number in the options menu  <type BufferedImage>
backButton - button graphic used to navigate to the previous menu state <type BufferedImage>
restartButton - button graphic used to restart/re-initialize the gameboard <type BufferedImage>
forwardButton - button graphic used to scroll forward through the rules pages  <type BufferedImage>
playButton - button graphic used to navigate to the "Play Menu" state  <type BufferedImage>
rulesAboutButton - button graphic used to navigate to the "Rules/About Menu" state <type BufferedImage>
exitButtonbutton - button graphic used to exit/terminate the program <type BufferedImage>
optionsButton - button graphic used to navigate to the "Options" state <type BufferedImage>
computerCodebreakerButton - button graphic used to navigate to the "Computer Codebreaker Menu" state <type BufferedImage>
playerCodebreakderButton - button graphic used to navigate to the player codebreaker game state <type BufferedImage>
pvpCodebreakerButton - button graphic used to navigate to the player vs player codebreaker game state <type BufferedImage>
easyButton - button graphic used to navigate to the easy AI game state <type BufferedImage>
mediumButton - button graphic used to navigate to the medium AI game state <type BufferedImage>
hardButton - button graphic used to navigate to the hard AI game state <type BufferedImage>
rulesButton - button graphic used to navigate to the "Rules" state <type BufferedImage>
aboutButton - button graphic used to navigate to the "About Us" state <type BufferedImage>
blackPegButton - button graphic used to add a black feedback peg <type BufferedImage>
whitePegButton - button graphic used to add a white feedback peg <type BufferedImage>
pegBlack - graphic used to indicate a black peg given to a guessed code on the gameboard <type BufferedImage>
pegWhite - graphic used to indicate a white peg given to a guessed code on the gameboard <type BufferedImage>
confrimButton - button graphic used to confirm the player's current guess or feedback <type BufferedImage>
deleteButton - button graphic used to delete/reset the player's current pending guess or feedback <type BufferedImage>
emptyGameboard - background graphic used for all game states  <type BufferedImage>
aiGameboard - background graphic used for the computer codebreaker game state
playerGameboard - background graphic used for the player codebreaker game state
pvpGameboard - background graphic used for the player vs player codebreaker game state
codebreakerWins - graphic used to indicate that the codebreaker has won the game (code was guessed) <type BufferedImage>
codemakerWins - graphic used to indicate that the codemaker has won the game (code was not guessed) <type BufferedImage>
mainMenu - background graphic used for the "Main Menu" state, the menu that is initially called <type BufferedImage>
playMenu - background graphic used for the "Play Menu" state <type BufferedImage>
computerCodebreakerMenu - background graphic used for the "Computer Codebreaker Menu" state <type BufferedImage>
rulesAboutMenu - background graphic used for the "Rules/About Menu" state <type BufferedImage>
aboutUsPage - background graphic used for the "About Us Menu" state <type BufferedImage>
optionsMenu - background graphic used for the "Options Menu" state <type BufferedImage>
colours - array used to store graphics used to display the 6 possible code colours (blue, green, orange, purple, red, yellow) <type BufferedImage Array>
rulesPages - array used to store background graphics for the 3 pages/sections of the "Rules" state <type BufferedImage Array>
numberButtons - array used to store graphics used to display numbers from 1-10 <type BufferedImage Array>
============================================================================================
*/
package gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {

    public static Font arial28, arial20;
    public static BufferedImage emptySlot, pointer;
    public static BufferedImage backButton, restartButton, forwardButton;
    public static BufferedImage playButton, rulesAboutButton, exitButton, optionsButton, computerCodebreakerButton, 
    playerCodebreakerButton, pvpCodebreakerButton, easyButton, mediumButton, hardButton, rulesButton, aboutButton;
    public static BufferedImage blackPegButton, whitePegButton;
    public static BufferedImage pegBlack, pegWhite;
    public static BufferedImage confirmButton, deleteButton;
    public static BufferedImage emptyGameboard, aiGameboard, playerGameboard, pvpGameboard;
    public static BufferedImage codebreakerWins, codemakerWins;
    public static BufferedImage mainMenu,playMenu,computerCodebreakerMenu,rulesAboutMenu,aboutUsPage,optionsMenu;
    public static BufferedImage[] colours, rulesPages, numberButtons;
   
    /**Init method
     * This procedural method assigns each BufferedImage variable their appropriate png graphics
     * file (located in the project resource folder) for GUI usage
     */
    public static void init() {

        colours = new BufferedImage[6];
        colours[0] = loadImage("/texture/colours/blue.png");
        colours[1] = loadImage("/texture/colours/green.png");
        colours[2] = loadImage("/texture/colours/orange.png");
        colours[3] = loadImage("/texture/colours/purple.png");
        colours[4] = loadImage("/texture/colours/red.png");
        colours[5] = loadImage("/texture/colours/yellow.png");

        pegBlack = loadImage("/texture/colours/black.png");
        pegWhite = loadImage("/texture/colours/white.png");
        emptySlot = loadImage("/texture/colours/brown.png");
        pointer = loadImage("/texture/images/pointer.png");

        emptyGameboard = loadImage("/texture/backgrounds/empty_gameboard_background.png");
        aiGameboard = loadImage("/texture/images/computer_codebreaker_board.png");
        playerGameboard = loadImage("/texture/images/player_codebreaker_board.png");
        pvpGameboard = loadImage("/texture/images/pvp_codebreaker_board.png");

        backButton = loadImage("/texture/buttons/back_button.png");
        restartButton = loadImage("/texture/buttons/restart_button.png");
        blackPegButton = loadImage("/texture/buttons/black_peg_button.png");
        whitePegButton = loadImage("/texture/buttons/white_peg_button.png");
        confirmButton = loadImage("/texture/buttons/confirm_button.png");
        deleteButton = loadImage("/texture/buttons/delete_button.png");
        forwardButton = loadImage("/texture/buttons/forward_button.png");
        
        playButton = loadImage("/texture/buttons/play_button.png");
        rulesAboutButton = loadImage("/texture/buttons/rules_about_button.png");
        exitButton = loadImage("/texture/buttons/exit_button.png");
        
        optionsButton = loadImage("/texture/buttons/options_button.png");
        playerCodebreakerButton = loadImage("/texture/buttons/player_codebreaker_button.png");
        computerCodebreakerButton = loadImage("/texture/buttons/computer_codebreaker_button.png");
        pvpCodebreakerButton = loadImage("/texture/buttons/pvp_codebreaker_button.png");

        codebreakerWins = loadImage("/texture/images/codebreaker_wins.png");
        codemakerWins = loadImage("/texture/images/codemaker_wins.png");

        easyButton = loadImage("/texture/buttons/easy_button.png");
        mediumButton = loadImage("/texture/buttons/medium_button.png");
        hardButton = loadImage("/texture/buttons/hard_button.png");
        
        rulesButton = loadImage("/texture/buttons/rules_button.png");
        aboutButton = loadImage("/texture/buttons/about_button.png");

        mainMenu = loadImage("/texture/backgrounds/main_menu.png");
        playMenu = loadImage("/texture/backgrounds/play_menu.png");
        optionsMenu = loadImage("/texture/backgrounds/options_menu.png");
        computerCodebreakerMenu = loadImage("/texture/backgrounds/computer_codebreaker_menu.png");
        rulesAboutMenu = loadImage("/texture/backgrounds/rules_about_menu.png");
        aboutUsPage = loadImage("/texture/backgrounds/about_us_page.png");
        
        rulesPages = new BufferedImage[3];
        rulesPages[0] = loadImage("/texture/backgrounds/rules_page_1.png");
        rulesPages[1] = loadImage("/texture/backgrounds/rules_page_2.png");
        rulesPages[2] = loadImage("/texture/backgrounds/rules_page_3.png");
        
        numberButtons = new BufferedImage[11];
        numberButtons[0] = loadImage("/texture/images/one.png"); //just in case
        numberButtons[1] = loadImage("/texture/images/one.png");
        numberButtons[2] = loadImage("/texture/images/two.png");
        numberButtons[3] = loadImage("/texture/images/three.png");
        numberButtons[4] = loadImage("/texture/images/four.png");
        numberButtons[5] = loadImage("/texture/images/five.png");
        numberButtons[6] = loadImage("/texture/images/six.png");
        numberButtons[7] = loadImage("/texture/images/seven.png");
        numberButtons[8] = loadImage("/texture/images/eight.png");
        numberButtons[9] = loadImage("/texture/images/nine.png");
        numberButtons[10] = loadImage("/texture/images/ten.png");

        arial28 = loadFront("res/Arial.ttf", 28, Font.BOLD);
        arial20 = loadFront("res/Arial.ttf", 20, Font.PLAIN);
    }
    
    /**loadImage method
     * This procedural method assigns a BufferedImage variable a png image by locating it using a certain file path
     * 
     *  @param path - the file path used to locate the png image file used <type String>
     */
    private static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(Assets.class.getResource(path));
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static Font loadFront(String path, int size, int style){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(style, size);
        }catch (IOException | FontFormatException e){
            e.printStackTrace();
        }
        return null;
    }

}
