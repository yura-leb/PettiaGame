package renju.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import renju.moves.RenjuMove;

public class Stone extends Piece {
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		return target.isEmpty();
	}

	@Override
	public Move makeMove(Square... squares) {
		return new RenjuMove(this, squares);
	}

	@Override
	public String toString() {
		return "" + square;
	}
}