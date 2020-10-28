/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 4, 2020
Java 13.0.2
The objects created from this class can be rendered on the screen and
a click on top of its rendered image will invoke some passed-in procedure

List of Global Variables:
x - the coordinate of the left most pixel of the button </type int>
y - the coordinate of the upper most pixel of the button </type int>
width - the width of of the button in pixels </type int>
height - the height of of the button in pixels </type int>
bounds - the clicking "hit box" of the button </type Rectangle>
clicker - the procedures to be executed when the button is clicked </type ClickListener>
image - the texture of the button </type BufferedImage>
===============================================================================
*/

package ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton {

    private int x, y, width, height;
    private Rectangle bounds;
    private ClickListener clicker;
    private final BufferedImage image;

    /**The constructor of the UIButton class.
     * All of the necessary variables are either passed in here when creating
     * an object or created here from the passed-in parameters.
     *
     * @param x - the coordinate of the left most pixel of the button </type int>
     * @param y - the coordinate of the upper most pixel of the button </type int>
     * @param height - the height of of the button in pixels </type int>
     * @param width - the width of of the button in pixels </type int>
     * @param image - the texture of the button </type BufferedImage>
     * @param clicker - the procedures to be executed when the button is clicked </type ClickListener>
     */
    public UIButton(int x, int y, int height, int width, BufferedImage image, ClickListener clicker) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
        this.image = image;
        this.clicker = clicker;
    }

    /**onMouseClick method
     * This procedural method is called by the uiManager object
     * when the mouse button has been clicked within the JFrame.
     * The method will determine whether the mouse click landed
     * on the hit box or not, and if hit, execute the procedure.
     *
     * @param e - the passed-in mouse event </type MouseEvent>
     */
    void onMouseClick(MouseEvent e){
        if(bounds.contains(e.getX(), e.getY()))
            clicker.onClick();

    }

    /**render method
     * This procedural method draws the button on the screen.
     *
     * @param graphics - the passed-in graphics context for drawing buffers on the screen </type Graphics>
     */
    void render(Graphics graphics) {
        graphics.drawImage(image, x, y, width, height, null);
    }

}
