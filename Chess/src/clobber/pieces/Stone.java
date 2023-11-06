package clobber.pieces;

import clobber.moves.Capture;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

public class Stone extends Piece {
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}
    @Override
    public boolean isCorrectMove(Square... squares) {
    	Square target = squares[0];
        if (target.isEmpty()) 
			return false;
		return getColor() != target.getPiece().getColor() && target.isNear(square);
    }
 
    @Override
    public Move makeMove(Square... squares) {
        return new Capture(this, squares);
    }
}