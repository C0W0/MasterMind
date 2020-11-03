/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 8, 2020
Java 13.0.2
This class manages the mouse IO and records all MouseEvent

List of Global Variables:
uiManager - an object that stores the set of UI elements that receives
    the MouseEvents </type UIManager>
===============================================================================
*/

package io;

import ui.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener {

    private UIManager uiManager;

    /**mouseClicked method
     * This procedural method is invoked when the mouse button
     * has been clicked (pressed and released) within the JFrame.
     *
     * @param e - the mouse event to be processed </type MouseEvent>
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 && uiManager != null){
            uiManager.onMouseClick(e);
        }
    }

    /**mousePressed method
     * This procedural method is invoked when the mouse button
     * has been pressed within the JFrame. It is not used in
     * this program.
     *
     * @param e - the mouse event to be processed </type MouseEvent>
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**mouseReleased method
     * This procedural method is invoked when the mouse button
     * has been released within the JFrame. It is not used in
     * this program.
     *
     * @param e - the mouse event to be processed </type MouseEvent>
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**mouseEntered method
     * This procedural method is invoked when the mouse cursor
     * enters the JFrame. It is not used in this program.
     *
     * @param e - the mouse event to be processed </type MouseEvent>
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**mouseExited method
     * This procedural method is invoked when the mouse cursor
     * exits the JFrame. It is not used in this program.
     *
     * @param e - the mouse event to be processed </type MouseEvent>
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**setUIManager
     * This procedural method changes the set of UI elements receiving
     * MouseEvents
     *
     * @param uiManager - the object that stores the set of UI elements
     *     receiving the MouseEvents </type UIManager>
     */
    public void setUIManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }
}
