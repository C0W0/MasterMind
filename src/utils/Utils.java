/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 23, 2020
Java 13.0.2
The class of utility methods
@reference https://github.com/C0W0/2D-Java-Game-Development by C0W0 (Terry Zha)
===============================================================================
*/

package utils;

import gamelogic.Score;

public class Utils {


    public static class ColourCombination{

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

        public static int getWhitePegCount(int[] code, int[] guess, int blackPegCount){
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

        public static int getBlackPegCount(int[] code, int[] guess){
            int count = 0;
            for(int i = 0; i < guess.length; i++)
                if(guess[i] == code[i])
                    count ++;
            return count;
        }

        public static Score match(String code, String guess){
            return match(toIntArrayColour(code), toIntArrayColour(guess));
        }

        public static Score match(int[] code, int[] guess){
            int blackCount = getBlackPegCount(code, guess);
            return new Score(blackCount, getWhitePegCount(code, guess, blackCount));
        }
    }


}
