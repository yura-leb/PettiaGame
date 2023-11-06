package chess.players;

import chess.moves.Capture;
import game.core.Move;

public class Poet extends ChessPlayer {
    @Override
    public String getName() {
        return "Цветик";
    }

    @Override
    public String getAuthorName() {
        return "????";
    }
    @Override
    protected int getWeight(Move m) {
        if (m instanceof Capture)
            return 1000;

        return 0;
    }
}
