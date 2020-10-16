package state;

public class EasyAiState extends MediumAiState {

    public EasyAiState(){
        super();
    }

    @Override
    protected void makeGuess() {

        choice = (int)(Math.random()*(choiceList.size()));


        guess = allPermutations[choiceList.get(choice)].clone();

        if(choiceList.size()!=1)
            choiceList.remove(choice);

        addAllColourImages(panel, toColour(guess));
    }
}
