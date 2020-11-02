/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
A critical graphic component, responsible for the game window
A Display object will store the JFrame and the Canvas (which is responsible
for rendering images)

List of Global Variables:
frame - a JFrame object. It represents the window of the program </type JFrame>
canvas - the canvas which images will be rendered upon </type Canvas>
===============================================================================
*/

package gfx;

import javax.swing.*;
import java.awt.*;

public class Display {

    private JFrame frame;
    private Canvas canvas;

    /**Display method
     * The constructor method of the Display class
     * It sets the dimension of the window, packs in a canvas
     * object to render onto, and performs some other critical
     * procedures for displaying a window on the screen.
     *
     * @param title - The title of the JFrame </type String>
     * @param width - The width of the JFrame </type int>
     * @param height - The height of the JFrame </type int>
     */
    public Display(String title, int width, int height){
        frame = new JFrame(title);
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize((new Dimension(width,height)));
        canvas.setMaximumSize((new Dimension(width,height)));
        canvas.setMinimumSize((new Dimension(width,height)));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    //getters and setters

    /**getCanvas method
     * This functional methods returns out the canvas of the
     * Display object
     *
     * @return canvas - the canvas which images will be rendered
     *      upon </type Canvas>
     */
    public Canvas getCanvas(){
        return canvas;
    }

    /**getFrame method
     * This functional methods returns out the JFrame object
     *
     * @return frame - a JFrame object which represents the window
     *      of the program </type JFrame>
     */
    public JFrame getFrame(){
        return frame;
    }

}
