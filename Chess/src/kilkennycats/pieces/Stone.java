package kilkennycats.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import kilkennycats.moves.SimpleMove;

public class Stone extends Piece {
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square next = squares[0];
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

	/**
	 * Дать цвет противоположный заданному цвету.
	 * 
	 * @param color - заданный цвет фигуры.
	 * @return противоположный цвет фигур.
	 */
	public boolean isEnemyColor(PieceColor aColor) {
		switch (getColor()) {
		case RED:
		case MAGENTA:
			return (aColor == PieceColor.BLUE) || (aColor == PieceColor.GREEN);
		case BLUE:
		case GREEN:
			return (aColor == PieceColor.RED) || (aColor == PieceColor.MAGENTA);
		default:
			return false;
		}
	}
}
