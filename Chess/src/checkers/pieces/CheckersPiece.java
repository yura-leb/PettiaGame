package checkers.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для всех фигур шашек.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class CheckersPiece extends Piece {

	public CheckersPiece(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// В шашках нельзя ходить на поле занятое фигурой любого цвета.
		
		Square target = squares[0];
		
		return target.isEmpty();
	}

	/**
	 * Имеет ли фигура ход с захватом фигур противника.
	 * @return есть ли захват фигуры.
	 */
	protected abstract boolean hasCapture(Square square);

	/**
	 * Имеются ли на шашечной доске фигуры, 
	 * которые могут захватить фигуры противника.
	 * Тогда простые ходы без захвата недопустимы.
	 * 
	 * @return есть ли захват фигуры.
	 */
	protected boolean hasCaptures() {
		// Получить все фигуры того же цвета.
		return getFriends()
			.stream()
			.map(p -> (CheckersPiece) p)
			.anyMatch(p -> p.hasCapture(p.square));
	}

	abstract
	public Move createMove(CheckersPiece piece, Square square, Square target);
}