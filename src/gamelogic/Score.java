package gamelogic;

public class Score{

    private int blackPeg, whitePeg;

    public Score(int blackPeg, int whitePeg){
        this.blackPeg = blackPeg;
        this.whitePeg = whitePeg;
    }

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

    public int getBlackPeg() {
        return blackPeg;
    }

    public int getWhitePeg() {
        return whitePeg;
    }

    public Score duplicate(){
        return new Score(blackPeg, whitePeg);
    }

    public boolean equals(Score matchTarget){
        return blackPeg == matchTarget.getBlackPeg() && whitePeg == matchTarget.getWhitePeg();
    }

    public boolean isDecoded(){
        return blackPeg == 4;
    }
}
