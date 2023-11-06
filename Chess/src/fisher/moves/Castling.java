package fisher.moves;

import chess.moves.SimpleMove;
import chess.pieces.Rook;
import game.core.Board;
import game.core.GameOver;
import game.core.Square;


public class Castling extends SimpleMove {

	private Square rookSource;
	private Square rookTarget;

	public Castling(Square[] squares) {
		super(squares);

		Board board = source.getBoard();
		if (source.v < target.v) {
			//Короткая рокировка
			for (int i = source.v + 1; i < 8; i++) {
				if (board.getSquare(i, source.h).getPiece() instanceof Rook) {
					rookSource = board.getSquare(i, source.h);
					rookTarget = board.getSquare(5, source.h);
					break;
				}
			}
		} else  {
			//Длинная рокировка
			for (int i = source.v - 1; i >= 0; i--) {
				if (board.getSquare(i, source.h).getPiece() instanceof Rook) {
					rookSource = board.getSquare(i, source.h);
					rookTarget = board.getSquare(3, source.h);
					break;
				}
			}
		}
	}

	/*
	 * Переставить короля и ладью.
	 * Ищем пустую клетку и переставляем ладью через нее потому что может быть ситуация, когда ладье и королю нужно обменяться клетками
	 */
	@Override
	public void doMove() throws GameOver {
		Board board = source.getBoard();
		for (int i = 0; i < board.nV-1; i++) {
			for (int j = 0; j < board.nH-1; j++) {
				Square square = board.getSquare(i, j);
				if (square.isEmpty()) {
					rookSource.movePieceTo(square);
					super.doMove();
					square.movePieceTo(rookTarget);
					return;
				}
			}
		}
	}

	/*
	 * Вернуть короля и ладью в исходной состояние.
	 * Возврат делаем в обратном порядке от прямых действий
	 */
	@Override
	public void undoMove() {
		Board board = source.getBoard();
		for (int i = 0; i < board.nV; i++) {
			for (int j = 0; j < board.nH; j++) {
				if (board.getSquare(i, j).isEmpty()) {
					rookTarget.movePieceTo(board.getSquare(i, j));
					super.undoMove();
					board.getSquare(i, j).movePieceTo(rookSource);
					return;
				}
			}
		}
	}

	@Override
	public String toString() {
		return source.v < target.v ? "O-O" : "O-O-O";
	}
}
