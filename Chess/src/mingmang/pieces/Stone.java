package mingmang.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import mingmang.moves.SimpleMove;

public class Stone extends Piece {
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square next = squares[0];
		if (!next.isEmpty())
			return false;
		return (next.v == square.v) || (next.h == square.h);
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
