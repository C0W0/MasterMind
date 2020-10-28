/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
September 27, 2020
Java 13.0.2
This class contains all of the constants this program needs to run properly
This class will read and write to the 2 data files of the game

list of global variables:
allStringCombinations - a String ArrayList that contains all possible
    permutations of colours in the String form </type ArrayList>
possibleScores - a 2d HashMap that contains all possible pairs of
    guess-code combinations and their matching score </type HashMap>
===============================================================================
*/

package utils;

import gamelogic.Score;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static utils.Utils.*;

public class Constants {

    public static ArrayList<String> allStringCombinations = new ArrayList<>();
    public static HashMap<String, HashMap<String, Score>>
            possibleScores = new HashMap<>();

    /**init method
     * This procedural method is called as soon as the program starts running.
     * It initializes all of the constants the program needs
     * If the needed files do not exist, they will be created
     */
    public static void init(){
        try{
            if(!new File("res/data/permutations.txt").exists())
                generateCombinations();
            loadCombinations();

            if(!new File("res/data/scores.txt").exists())
                generateScore();
            loadScore();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**loadScore method
     * This procedural method fills the 2d global variable possibleScores
     * with all possible scores of every pair of possible combinations of
     * colours from the file scores.txt
     *
     * list of local variables:
     * allScore - the score section of the guess-code-score matrix read
     *      from a pre-stored file </type ArrayList>
     * line - a line in the guess-code-score matrix </type HashMap>
     * codeToScore - a line of scores in the guess-code-score matrix </type String[]>
     * score - a score represented by a 2-digit String number. </type String[]>
     */
    private static void loadScore(){
        ArrayList<String> allScores = loadFileAsArrayList("res/data/scores.txt");
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

    /**generateScore method
     * This procedural method will be called if the file scores.txt does
     * not exist. It will create the file and fill in all possible scores
     * of every pair of possible combinations of colours to the file
     *
     *
     * list of local variables:
     * file - the representation of scores.txt. It will be created and
     *      written to </type File>
     * fileWriter - an PrintWriter object to write to the file </type PrintWriter>
     * score - a score represented by a 2-digit String number. </type String[]>
     *
     * @throws IOException - potential errors during file handling. Should not happen
     *      since all critical parameters are hard-coded
     */
    private static void generateScore() throws IOException{
        File file = new File("res/data/scores.txt");
        file.createNewFile();
        PrintWriter fileWriter = new PrintWriter(file);
        for(int y = 0; y < 1296; y++){
            for(int x = 0; x < 1296; x++){
                Score score = match(allStringCombinations.get(y), allStringCombinations.get(x));
                fileWriter.print(score.getBlackPeg()+(score.getWhitePeg()+" "));
            }
            fileWriter.println();
        }
        fileWriter.close();
    }

    /**loadCombinations method
     * This procedural method fills the global variable possibleCombinations
     * with all possible combination of colour from the file permutations.txt
     */
    private static void loadCombinations(){
        allStringCombinations.addAll(Arrays.asList(loadFileAsString("res/data/permutations.txt").split("\\s+")));
    }

    /**generateCombinations method
     * This procedural method will be called if the file permutations.txt
     * does not exist. It will create the file and fill in all possible
     * permutations of colours to the file
     *
     * list of local variables:
     * file - the representation of permutations.txt. It will be created and
     *      written to </type File>
     * fileWriter - an PrintWriter object to write to the file </type PrintWriter>
     *
     * @throws IOException - potential errors during file handling. Should not happen
     *      since all critical parameters are hard-coded
     */
    private static void generateCombinations() throws IOException{
        File file = new File("res/data/permutations.txt");
        file.createNewFile();
        PrintWriter fileWriter = new PrintWriter(file);
        for(int i1 = 0; i1 < 6; i1++){
            for(int i2 = 0; i2 < 6; i2++) {
                for (int i3 = 0; i3 < 6; i3++){
                    for (int i4 = 0; i4 < 6; i4++) {
                        fileWriter.print(toStringColour(new int[]{i1, i2, i3, i4}) + " ");
                    }
                }
            }
        }
        fileWriter.close();
    }


}
