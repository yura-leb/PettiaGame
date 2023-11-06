package shogi.pieces;

import game.core.PieceColor;
import game.core.Square;

public class GeneralGold extends ShogiPiece {

	public GeneralGold(Square square, PieceColor color) {
		super(square, color);
		isTransformable = false;
	}
	
	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square source = square;
		Square target = squares[0];
		
		int dh = isWhite() ? source.h - target.h : target.h - source.h;
		int dv = Math.abs(source.v-target.v);
		
		return generalGoldMove(dh, dv);
	}
	
	static public boolean generalGoldMove(int dh, int dv) {
		if (Math.abs(dh) > 1 || dv > 1 || (dh < 0 && dv == 1)) {
			return false;
		}
		
		return true;
	}
	
// test
	@Override
	public String toString() {
		return "G";
	}
}