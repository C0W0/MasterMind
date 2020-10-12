package utils;

import gamelogic.Score;

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {

    public static ArrayList<String> allStringCombinations = new ArrayList<>();
    public static HashMap<String, HashMap<String, Score>>
            possibleScores = new HashMap<>();

    public static void init(){
        generateAllCombinations();
        generateScore();
    }

    /**generateScore method
     * This procedural method fills the 2d global variable possibleScores
     * with all possible scores of every pair of possible combinations of
     * colours
     *
     * list of local variables:
     * line - a HashMap that associates the score with one of the two
     *      combinations of colour </type HashMap>
     */
    private static void generateScore(){
        for(int y = 0; y < 1296; y++){
            HashMap<String, Score> line = new HashMap<>();
            for(int x = 0; x < 1296; x++){
                line.put(allStringCombinations.get(x), Utils.ColourCombination.match(allStringCombinations.get(y), allStringCombinations.get(x)));
                //allCombinations.get(y).match(allCombinations.get(x))
            }
            possibleScores.put(allStringCombinations.get(y), line);
        }
    }

    /**generateAllCombinations method
     * This procedural method fills the global variable possibleCombinations
     * with all possible combination of colour
     *
     * list of local variables:
     * cc - a ColourCombination of many possible combinations </type ColourCombination>
     */
    private static void generateAllCombinations(){
        for(int i1 = 0; i1 < 6; i1++)
            for(int i2 = 0; i2 < 6; i2++)
                for(int i3 = 0; i3 < 6; i3++)
                    for(int i4 = 0; i4 < 6; i4++){
                        int[] intColourCombination = new int[]{i1, i2, i3, i4};
                        allStringCombinations.add(Utils.ColourCombination.toStringColour(intColourCombination));
                    }
    }



}
