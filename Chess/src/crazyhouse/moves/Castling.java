package crazyhouse.moves;

import game.core.Board;
import game.core.Square;

/**
 * Ход европейских шахмат - рокировка.
 */
public class Castling extends SimpleMove {

	private final Square rookSource;
	private final Square rookTarget;

	public Castling(Square[] squares) {
		super(squares);
//		super(history, king, source, target);
		
		Board board = source.getBoard();

		if (source.v < target.v) {
			// Короткая рокировка.
			rookSource = board.getSquare(source.v+3, source.h);
			rookTarget = board.getSquare(source.v+1, source.h);
		}
		else {
			// Длинная рокировка.
			rookSource = board.getSquare(source.v-4, source.h);
			rookTarget = board.getSquare(source.v-1, source.h);
		}
	}

	/* 
	 * Переставить короля и ладью.
	 */
	@Override
	public void doMove() {
		rookSource.movePieceTo(rookTarget);
		super.doMove();
	}

	/* 
	 * Вернуть короля и ладью в исходное состояние.
	 */
	@Override
	public void undoMove() {
		rookTarget.movePieceTo(rookSource);
		super.undoMove();
	}
	
	@Override
	public String toString() {
		return source.v < target.v ? "O-O" : "O-O-O"; 
	}
}
