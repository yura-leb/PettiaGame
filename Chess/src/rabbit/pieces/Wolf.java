package rabbit.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import rabbit.moves.SimpleMove;

public class Wolf extends Piece {
    public Wolf(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        return true;
    }

    @Override
    public Move makeMove(Square... squares) {
        return new SimpleMove(squares);
    }

    @Override
    public String toString() {
        return "W";
    }
}
