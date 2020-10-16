/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
A critical graphic class, responsible for the game window
===============================================================================
*/

package display;

import javax.swing.*;
import java.awt.*;

public class Display {

    private JFrame frame;
    private Canvas canvas;

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

    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return frame;
    }

}
