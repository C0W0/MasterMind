/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
The game class of the Code Breaker
===============================================================================
*/

import display.Display;
import gfx.Assets;
import io.MouseManager;
import state.AiDecodeState;
import state.MediumAiState;
import state.PlayerDecodeState;
import state.State;
import utils.Constants;
import utils.FpsTimer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    //game loop
    private FpsTimer timer;
    private Thread thread;

    //graphics
    private Display display;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    //input
    private MouseManager mouseManager;

    //frame parameters
    private final String title;
    private final int width, height;
    private boolean running = false;

    //state
    private State currentState;

    Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        mouseManager = new MouseManager();
    }

    private void init(){
        display = new Display(title, width, height);
        timer = new FpsTimer(30);
        Assets.init();
        Constants.init();
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        //setState(new AiDecodeState());
        setState(new PlayerDecodeState());

    }

    private void update(){

    }

    private void render(){
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if(bufferStrategy == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0,0,width,height); //clear the screen
        //Draw Below

        currentState.render(graphics);

        //End Drawing
        bufferStrategy.show();
        graphics.dispose();
    }

    public void run(){

        init();

        while(running){

            if (timer.check()){
                update();
                render();
            }

        }
        stop();
    }


    synchronized void start(){
        if(running){
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop(){
        if(!running){
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //getters and setters

    public Display getDisplay() {
        return display;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setState(State state){
        mouseManager.setUIManager(state.getUiManager());
        currentState = state;
    }
}
