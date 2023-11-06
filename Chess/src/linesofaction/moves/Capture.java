package linesofaction.moves;

import game.core.GameOver;
import game.core.Square;
import linesofaction.pieces.Stone;

public class Capture extends SimpleMove {
    public Capture(Stone stone, Square[] squares) {
        super(stone, squares);
    }

    @Override
    public void doMove() throws GameOver {
        // TODO
    }

    @Override
    public void undoMove() {
        // TODO
    }
}
