package shogi.pieces;

import game.core.PieceColor;
import game.core.Square;

public class GeneralSilver extends ShogiPiece {

	public GeneralSilver(Square square, PieceColor color) {
		super(square, color);
	}
	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square target = squares[0];

		int GeneralSilverV = square.v;
		int GeneralSilverH = square.h;

		int dv =  target.v - GeneralSilverV;
		int dh = isWhite() ? target.h - GeneralSilverH : GeneralSilverH - target.h;
		if ((dh > 0 && dv == 0) || dh == 0)
			return false;
		
		if (square.isNear(target))		
			return true;
		
		return false;
	}
	//testik

	@Override
	public String toString() {
		return "S";
	}
}
