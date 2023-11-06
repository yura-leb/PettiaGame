package camelot.pieces;

import camelot.moves.SimpleMove;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

public class Knight extends Piece {

	public Knight(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square source = square; // Клетка где стоит фигура.
		Square target = squares[0]; // Клетка куда должна пойти фигура.

		if (!target.isEmpty())
			return false;

		return source.distance(target) == 1;
	}

	@Override
	public Move makeMove(Square... squares) {
		Square source = squares[0];
		Square target = squares[1];

		return new SimpleMove(this, source, target);
	}
	

	@Override
	public String toString() {
		return "N";
	}
}



