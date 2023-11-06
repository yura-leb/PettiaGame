package coffee.pieces;

import game.core.*;

public class Bean extends Piece {
    public final Dirs dir;

    public Bean(Square square, PieceColor color, Dirs dir) {
        super(square, color);
        this.dir = dir;
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        return false;
    }

    @Override
    public Move makeMove(Square... squares) {
        return null;
    }
}
