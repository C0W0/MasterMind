/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
The class of utility methods
===============================================================================
*/

package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

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


}
