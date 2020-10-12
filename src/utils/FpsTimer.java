/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
The timer which controls the speed of the game update and render loop
@reference https://github.com/C0W0/2D-Java-Game-Development by C0W0 (Terry Zha)
===============================================================================
*/

package utils;

public class FpsTimer {

    private double timePerUpdate;
    private double delta;
    private long lastTime;
    //private long time;
    //private int frames;
    //The code that are commented out are for FPS display


    public FpsTimer(int FPS){
        timePerUpdate = 1e9/FPS;
        delta = 0;
        lastTime = System.nanoTime();
    }

    public boolean check(){
        long now = System.nanoTime();
        delta += (now - lastTime)/timePerUpdate;
        //time += now - lastTime;
        lastTime = now;
        if (delta >= 1){
            delta --;
            //frames ++;

            //if(time >= 1e9){
            //    System.out.println("FPS: "+frames);
            //    time = 0;
            //    frames = 0;
            //}

            return true;
        } else{
            return false;
        }
    }

}
