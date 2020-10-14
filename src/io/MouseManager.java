/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 8, 2020
Java 13.0.2
The class which receives mouse input from the user
===============================================================================
*/

package io;

import ui.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

    private UIManager uiManager;


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 && uiManager != null){
            uiManager.onMouseClick(e);
        }
//        System.out.println(e.getX()+" "+e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    public void setUIManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }
}
