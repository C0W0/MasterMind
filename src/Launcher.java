/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
The main class of the Code Breaker
===============================================================================
*/

import gamelogic.GameLogic;
import utils.Constants;

public class Launcher {

    public static void main(String[] args){
        Game game = new Game("Code Breaker", 1024, 768);
        game.start();
//        Constants.init();
//        GameLogic logic = new GameLogic();
//        logic.play();
    }


    private static void play(){

    }

}
