/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 4, 2020
Java 13.0.2
The objects created from this class stores all of the buttons and
pass in the mouse clicks

List of Global Variables:
uiButtons - an ArrayList that stores all of the buttons </type ArrayList>
===============================================================================
*/

package ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {

    private ArrayList<UIButton> uiButtons;

    /**UIManager method
     * The constructor method of the UIManager class
     */
    public UIManager(){
        uiButtons = new ArrayList<>();
    }

    /**render method
     * This procedural method draws buttons on the screen
     *
     * @param graphics - the passed-in graphics context for drawing buffers on the screen </type Graphics>
     */
    public void render(Graphics graphics){
        for(UIButton o: uiButtons)
            o.render(graphics);
    }

    /**onMouseClick method
     * This procedural method is called by the mouseManager object
     * when the mouse button has been clicked within the JFrame. It
     * receives the MouseEvent and pass it to all of the buttons.
     *
     * @param e - the mouse event to be processed </type MouseEvent>
     */
    public void onMouseClick(MouseEvent e){
        for(UIButton button: uiButtons)
            button.onMouseClick(e);
    }

    /**addUIButton method
     * This procedural method adds a button to the uiButtons ArrayList
     *
     * @param button - the button to be added </type UIButton>
     */
    public void addUIButton(UIButton button){
        uiButtons.add(button);
    }

}
