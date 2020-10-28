/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
The class of utility methods
===============================================================================
*/

package utils;

import gamelogic.Score;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    /**loadFileAsArrayList method
     * This functional method loads the file from a specified path as an
     * ArrayList of String, with each line of the content of the files as
     * an element of the ArrayList
     *
     * list of local variables:
     * lines - the ArrayList that stores the content of the file </type ArrayList>
     * br - a BufferedReader object that reads the file </type BufferedReader>
     * line - a line of the content of the files </type String>
     *
     * @param path - the path of the file </type String>
     * @return lines - the content of the file </type ArrayList>
     */
    static ArrayList<String> loadFileAsArrayList(String path){
        ArrayList<String> lines = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null){
                if(!line.startsWith("//")){
                    lines.add(line);
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return lines;
    }

    /**loadFileAsString method
     * This functional method loads the file from a specified path as a String
     *
     * list of local variables:
     * builder - an object that assists in concatenating Strings </type StringBuilder>
     * br - a BufferedReader object that reads the file </type BufferedReader>
     * line - a line of the content of the files </type String>
     *
     * @param path - the path of the file </type String>
     * @return builder.toString() - the content of the file </type String>
     */
    static String loadFileAsString(String path){

        StringBuilder builder = new StringBuilder();

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null){
                String newLine = line + "\n";
                if(!newLine.startsWith("//")){
                    builder.append(newLine);
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**toStringColour method
     * This functional method converts a colour combination from
     * its integer array form to its String form
     *
     * list of local variables:
     * sb - an object that assists in concatenating Strings </type StringBuilder>
     *
     * @param colours - the integer array form of the color combination </type int[]>
     * @return sb.toString() - the String form of the colour combination </type String>
     */
    public static String toStringColour(int[] colours) {
        StringBuilder sb = new StringBuilder();
        for(int i: colours){
            switch (i){
                case 0:
                    sb.append("B");
                    break;
                case 1:
                    sb.append("G");
                    break;
                case 2:
                    sb.append("O");
                    break;
                case 3:
                    sb.append("P");
                    break;
                case 4:
                    sb.append("R");
                    break;
                case 5:
                    sb.append("Y");
                    break;
            }
        }
        return sb.toString();
    }

    /**toIntArrayColour method
     * This functional method converts a colour combination from
     * its String form to its integer array form
     *
     * list of local variables:
     * colours - the int array form of the colour combination </type int[]>
     *
     * @param coloursStr - the String form of the color combination </type String>
     * @return colours - the int array form of the colour combination </type int[]>
     */
    public static int[] toIntArrayColour(String coloursStr){
        int[] colours = new int[4];
        for(int i = 0; i < 4; i++){
            switch (coloursStr.charAt(i)) {
                case 'B':
                    colours[i] = 0;
                    break;
                case 'G':
                    colours[i] = 1;
                    break;
                case 'O':
                    colours[i] = 2;
                    break;
                case 'P':
                    colours[i] = 3;
                    break;
                case 'R':
                    colours[i] = 4;
                    break;
                case 'Y':
                    colours[i] = 5;
                    break;
            }
        }
        return colours;
    }

    /**getWhitePegCount method
     * This functional method calculate and returns the number of
     * white pegs when matching a given pair of colour combinations
     *
     * list of local variables:
     * colourSet1 - an integer array used to store the number of each
     *      colours found in the given code colour combination </type int[]>
     * colourSet1 - an integer array used to store the number of each
     *      colours found in the given guess colour combination </type int[]>
     * count - the number of colour duplicates found in the two given
     *      colour combinations </type int>
     *
     * @param code - one of the matching colour combination, the code </type int[]>
     * @param guess - one of the matching colour combination, the guess </type int[]>
     * @param blackPegCount - the number of black pegs when matching the given pair
     *                      of colour combinations. This is needed because the method
     *                      does not account for the position of each colour and therefore
     *                      need to subtract out the number of black pegs from the
     *                      number of duplicate colour found </type int>.
     * @return the number of white pegs when matching the given pair of colour combinations
     */
     private static int getWhitePegCount(int[] code, int[] guess, int blackPegCount){
        int[] colourSet1 = new int[6];
        int[] colourSet2 = new int[6];
        int count = 0;
        for(int i: code)
            colourSet1[i] ++;
        for(int i: guess)
            colourSet2[i] ++;
        for(int i = 0; i < 6; i++){
            while(colourSet1[i] != 0 && colourSet2[i] != 0){
                count ++;
                colourSet1[i] --;
                colourSet2[i] --;
            }
        }
        return count - blackPegCount;
    }

    /**getBlackPegCount method
     * This functional method calculate and returns the number of
     * black pegs when matching a given pair of colour combinations
     *
     * list of local variables:
     * count - the number of colour duplicates found at the exact same position of the
     *      two given colour combinations </type int>
     *
     * @param code - one of the matching colour combination, the code </type int[]>
     * @param guess - one of the matching colour combination, the guess </type int[]>
     * @return count - the number of black pegs when matching the given pair of colour combinations
     */
     private static int getBlackPegCount(int[] code, int[] guess){
        int count = 0;
        for(int i = 0; i < guess.length; i++)
            if(guess[i] == code[i])
                count ++;
        return count;
    }

    /**match method
     * This functional method calculate and returns the score when
     * matching a given pair of colour combinations
     *
     * list of local variables:
     * blackCount - the number of black pegs when matching the given
     *      pair of colour combinations </type int>
     *
     * @param code - one of the matching colour combination, the code </type int[]>
     * @param guess - one of the matching colour combination, the guess </type int[]>
     * @return the score when matching the given pair of colour combinations
     */
    static Score match(String code, String guess){
        int blackCount = getBlackPegCount(toIntArrayColour(code), toIntArrayColour(guess));
        return new Score(blackCount, getWhitePegCount(toIntArrayColour(code), toIntArrayColour(guess), blackCount));
    }

    /**drawText method
     * This procedural method renders text on screen
     *
     * list of local variables:
     * fm - the dimension status of a given front, used to measure the
     *      space the text is going to take on the screen </type FontMetrics>
     * x - the coordinate of the left most pixel of the text </type int>
     * y - the coordinate of the upper most pixel of the text </type int>
     *
     * @param graphics - a graphics context for drawing buffer </type Graphics>
     * @param text - the text that is to be rendered on the screen </type String>
     * @param xPos - the centre x position of the text </type int>
     * @param yPos - the centre y position of the text </type int>
     * @param colour - the colour of the text </type Colour>
     * @param font - the font of the text used </type Font>
     */
    public static void drawText(Graphics graphics, String text, int xPos, int yPos, Color colour, Font font){
        graphics.setColor(colour);
        graphics.setFont(font);
        FontMetrics fm = graphics.getFontMetrics(font);
        int x = xPos - fm.stringWidth(text)/2;
        int y = (yPos - fm.getHeight()/2) + fm.getAscent();
        graphics.drawString(text, x, y);
    }


}
