package shogi.pieces;

import game.core.PieceColor;
import game.core.Square;

public class Pawn extends ShogiPiece {
	
	public Pawn(Square square, PieceColor color) {
		super(square, color);
		isTransformable = true;
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
		
		if(this.isTransformed == false) {			
			if (!(dv == 0 && dh == 1)) {
				return false;
			}
			
			return true;
			
		} else {
			return GeneralGold.generalGoldMove(dh, dv);
		}
		
	}

	/*@Override
	public Move makeMove(Square... squares) {
		Square source = squares[0];
		Square target = squares[1];

		return new SimpleMove(this, source, target);
	}*/
	
	//test push
	
	@Override
	public String toString() {
		if (isTransformed == false) {
			return "P";
		} else {
			return "+P";
		}
		
	}
}
