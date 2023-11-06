package shogi.pieces;

import game.core.PieceColor;
import game.core.Square;

public class Knight extends ShogiPiece {

	public Knight(Square square, PieceColor color) {
		super(square, color);
	}
	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		//реализован ход конём только вперёд
		Square target = squares[0];
		
		int dh = isWhite() ? (-1)*(target .h - square.h) : target .h - square.h;
		int dv = Math.abs(target .v - square.v);

		return (dh == 2 && dv == 1);
	
	}

	@Override
	public String toString() {
		return "N";
	}
}
