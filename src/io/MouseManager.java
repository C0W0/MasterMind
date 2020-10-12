/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 8, 2020
Java 13.0.2
The class which receives mouse input from the user
@reference https://github.com/C0W0/2D-Java-Game-Development by C0W0 (Terry Zha)
===============================================================================
*/

package io;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

    private boolean leftPressed, rightPressed;
    private int cursorX, cursorY;


    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1){
            leftPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON3){
            rightPressed = true;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1){
            leftPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3){
            rightPressed = false;
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
