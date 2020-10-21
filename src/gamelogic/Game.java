package gamelogic;
/*==========================================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
The game class of the Code Breaker
This is the main object of the Code Breaker. It is used to organize
GUI, IO, and Game Logic together

list of global variables:
thread - the thread which controls the render loop of the GUI </type Thread>
display - a critical graphics object that is responsible for the GUI window </type Display>
timePerUpdate - the time (in nanosecond) between each frame </type double>
delta - the portion of time passed between each frame. Once delta reaches 1,
        a new frame will be rendered. </type double>
lastTime - the system time (in nanosecond) of last frame rendering </type long>
mouseManager - a critical IO object that tracks mouse actions </type MouseManager>
title - the title of the frame </type String>
width - the width of the frame in pixels </type int>
height - the height of the frame in pixels </type int>
running - the control variable for the main loop </type boolean>
currentState - the current state of the game </type State>
============================================================================================
*/

import gfx.Display;
import gfx.Assets;
import io.MouseManager;
import state.*;
import utils.Constants;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    //game loop
    private Thread thread;

    //graphics
    private Display display;

    //fps control
    private double timePerUpdate, delta;
    private long lastTime;

    //input
    private MouseManager mouseManager;

    //frame parameters
    private final String title;
    private final int width, height;
    private boolean running = false;

    //state
    private State currentState;

    /**The Game method
     * The constructor method of the Game class
     *
     * @param title the passed-in title of the frame
     * @param width the passed-in width of the frame in pixels
     * @param height the passed-in height of the frame in pixels
     */
    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        mouseManager = new MouseManager();
    }

    /**The init method
     * This procedural method is be
     */
    private void init(){
        display = new Display(title, width, height);
        Assets.init();
        Constants.init();
        State.generateStates(this);
        
        display.getFrame().addMouseListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        
        setState(State.states[0]);
    }

    private void update(){

    }

    private void render(){
        BufferStrategy bufferStrategy = display.getCanvas().getBufferStrategy();
        if(bufferStrategy == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();
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

            if (timerCheck()){
                update();
                render();
            }

        }
        stop();
    }


    public synchronized void start(){
        if(running){
            return;
        }

        running = true;

        timePerUpdate = 1e9/30;
        delta = 0;
        lastTime = System.nanoTime();

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

    private boolean timerCheck(){
        long now = System.nanoTime();
        delta += (now - lastTime)/timePerUpdate;
        lastTime = now;
        if (delta >= 1){
            delta --;
            return true;
        } else{
            return false;
        }
    }


    //getters and setters

    public void setState(State state){
        mouseManager.setUIManager(state.getUiManager());
        currentState = state;
        currentState.init();
    }
}
