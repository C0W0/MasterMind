/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 4, 2020
Java 13.0.2
A class that stores the value (number of white and black pegs) of a score

list of global variables:
blackPeg - the number of black pegs of the score </type int>
whitePeg - the number of white pegs of the score </type int>
===============================================================================
*/

package gamelogic;

public class Score{

    private int blackPeg, whitePeg;

    /**Score method
     * The constructor method of the Score class
     *
     * @param blackPeg - the number of black pegs of the score </type int>
     * @param whitePeg - the number of white pegs of the score </type int>
     */
    public Score(int blackPeg, int whitePeg){
        this.blackPeg = blackPeg;
        this.whitePeg = whitePeg;
    }

    /**toString method
     * This functional method returns the String representation
     * of the score. It is composed of a number of 'B's and 'W's
     *
     * list of local variables:
     * builder - a helper object which constructs the String form
     *      of the score </type StringBuilder>
     *
     * @return builder.toString() - the String form of the score </type String>
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < blackPeg; i++){
            builder.append("B");
        }
        for(int i = 0; i < whitePeg; i++){
            builder.append("W");
        }
        return builder.toString();
    }

    /**getBlackPeg method
     * This functional method returns the number of black pegs of the score
     *
     * @return blackPeg - the number of black pegs </type int>
     */
    public int getBlackPeg() {
        return blackPeg;
    }

    /**getWhitePeg method
     * This functional method returns the number of white pegs of the score
     *
     * @return whitePeg - the number of white pegs </type int>
     */
    public int getWhitePeg() {
        return whitePeg;
    }

    /**equals method
     * This functional method will check if two scores are identical
     *
     * @param matchTarget - the score to be compared to </type Score>
     * @return whether the two scores are equal </type boolean>
     */
    public boolean equals(Score matchTarget){
        return blackPeg == matchTarget.getBlackPeg() && whitePeg == matchTarget.getWhitePeg();
    }

    /**isDecoded method
     * This functional method will check if the score represents a decoded answer
     *
     * @return is the code decoded </type boolean>
     */
    public boolean isDecoded(){
        return blackPeg == 4;
    }
}
