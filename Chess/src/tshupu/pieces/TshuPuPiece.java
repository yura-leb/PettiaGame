package tshupu.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import tshupu.moves.SimpleMove;

public class TshuPuPiece extends Piece {
	public int count = 4;
	
    public TshuPuPiece(Square square, PieceColor color) {
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
        return "";
    }
}
