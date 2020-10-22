package utils;

import gamelogic.Score;

import java.util.ArrayList;
import java.util.Arrays;
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
     * allScore - the score section of the guess-code-score matrix read
     *      from a pre-stored file </type ArrayList>
     * line - a line in the guess-code-score matrix </type HashMap>
     * codeToScore -  a line of scores in the guess-code-score matrix </type String[]>
     * score - a score represented by a 2-digit String number. </type String[]>
     */
    private static void generateScore(){
        ArrayList<String> allScores = Utils.loadFileAsArrayList("res/data/scores.txt");
        for(int y = 0; y < 1296; y++){
            HashMap<String, Score> line = new HashMap<>();
            String[] codeToScore = allScores.get(y).split("\\s+");
            for(int x = 0; x < 1296; x++){
                String[] score = codeToScore[x].split("");
                line.put(allStringCombinations.get(x), new Score(Integer.parseInt(score[0]), Integer.parseInt(score[1])));
            }
            possibleScores.put(allStringCombinations.get(y), line);
        }
    }

    /**generateAllCombinations method
     * This procedural method fills the global variable possibleCombinations
     * with all possible combination of colour from the file permutations.txt
     */
    private static void generateAllCombinations(){
        allStringCombinations.addAll(Arrays.asList(Utils.loadFileAsString("res/data/permutations.txt").split("\\s+")));
    }



}
