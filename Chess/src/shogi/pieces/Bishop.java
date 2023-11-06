package shogi.pieces;

import game.core.PieceColor;
import game.core.Square;

public class Bishop extends ShogiPiece {
	public Bishop(Square square, PieceColor color) {
		super(square, color);
	}
	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		//реализовано движение слона по диагонали в любую сторону
		Square target = squares[0];
		return square.isEmptyDiagonal(target);
		
	}

	@Override
	public String toString() {
		return "B";
	}
}
