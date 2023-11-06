package linesofaction.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import linesofaction.moves.Capture;
import linesofaction.moves.SimpleMove;

public class Stone extends Piece {
    public Stone(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        return false;
    }

    @Override
    public Move makeMove(Square... squares) {
        Square target = squares[1];
        if (!target.isEmpty())
            return new Capture(this, squares);

        return new SimpleMove(this, squares);
    }
}
