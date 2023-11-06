package chess.players;

import game.core.Move;

public class Painter extends ChessPlayer {
    @Override
    public String getName() {
        return "Тюбик";
    }

    @Override
    public String getAuthorName() {
        return "????";
    }

    @Override
    protected int getWeight(Move m) {
        return 0;
    }

}
