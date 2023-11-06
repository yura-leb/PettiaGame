package breakthrough.pieces;

import breakthrough.moves.Capture;
import breakthrough.moves.SimpleMove;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Правила:
 * https://www.chessprogramming.org/Breakthrough_(Game)
 */
public class Pawn extends Piece {
	public Pawn(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];

		boolean isWhite = getColor() == PieceColor.WHITE;
		int step = isWhite ? -1 : 1;

		// Смещение по вертикали.
		int dv = Math.abs(square.v - target.v);
		int dh = target.h - square.h;
		
		if (dv > 1) // Слишком сместились в сторону.
			return false;
		
		Piece targetPiece = target.getPiece();

		if (targetPiece != null)
			if (targetPiece.getColor() == getColor())
				return false; // На клетки занятые своими фигурами не ходим.
			else
				if (dv == 0)
					return false; // Прямо брать пешку нельзя

		return dh == step; // Один шаг для пешки этого цвета
	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[1];
		if (!target.isEmpty())
			return new Capture(squares);
		
		return new SimpleMove(squares);
	}
	
	@Override
	public String toString() {
		return "";
	}
}
