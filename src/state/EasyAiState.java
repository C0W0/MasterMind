/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
November 2, 2020
Java 13.0.2
The object created from this class runs the ai decode game mode
of CodeBreaker and contains the easy-level AI algorithm. Since the
easy-level AI is just a modified medium-level AI, this class extends
the MediumAiState class.
===============================================================================
*/

package state;
import gamelogic.Game;
public class EasyAiState extends MediumAiState {

    /**MediumAiState method
     * Constructor method of the MediumAiState class
     *
     * @param game - the passed-in object of the custom-made Game class </type Game>
     */
    public EasyAiState(Game game){
        super(game);
        header = "Welcome to EASY AI codebreaker";
    }


    /**makeGuess method
     * This procedural method is inherited from the parent class, the MediumAiState class.
     * It runs a simplified version of AI algorithm in the MediumAiState class
     */
    @Override
    protected void makeGuess() {

        choice = (int)(Math.random()*(choiceList.size()));


        guess = allPermutations[choiceList.get(choice)].clone();

        if(choiceList.size()!=1)
            choiceList.remove(choice);

        addAllColourImages(panel, toColour(guess));
    }
}
