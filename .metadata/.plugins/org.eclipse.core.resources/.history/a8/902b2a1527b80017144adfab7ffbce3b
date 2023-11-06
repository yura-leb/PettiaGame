/**
 * 
 */
package checkers.moves;

import checkers.pieces.CheckersPiece;
import game.core.Square;

/**
 * Ход шашкой с взятием одной фигуры противника.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove {
	/**
	 * Захваченая фигура.
	 */
	CheckersPiece captured;

	/**
	 * Создание хода представляющего взятие одной фигуры.
	 * @param isPromotion - было ли превращение в дамку.
	 * @param captured - захваченная фигура. 
	 * @param squares - откуда и куда пошла фигура.
	 */
	public Capture(boolean isPromotion, CheckersPiece captured, Square... squares) {
		super(isPromotion, squares);
		
		this.captured = captured;
	}
	
	@Override
	public void doMove() {
		// TODO Checkers Сделать ход на доске, переставив фигуры 
	}

	@Override
	public void undoMove() {
		// TODO Checkers Вернуть фигуры в исходное состояние
	}
}
