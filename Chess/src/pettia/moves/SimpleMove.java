package pettia.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;
import pettia.pieces.Stone;

/**
 * Простой ход - перемещение фигуры на пустую клетку.
 */
public class SimpleMove implements ITransferMove {
	/**
	 * Какая фигура перемещается.
	 */
	protected final Piece piece;

	/**
	 * Откуда перемещается.
	 */
	protected final Square source;

	/**
	 * Куда перемещается.
	 */
	protected final Square target;

	/**
	 * Простой ход без захвата фигур противника.
	 * @param stone 
	 * 
	 * @param squares
	 *            - клетки хода. 
	 * 
	 * <pre>
	 * squares[0] откуда идет (source) 
	 * squares[1] куда идет (target)
	 * </pre>
	 */
	public SimpleMove(Stone stone, Square[] squares) {
		source = squares[0];
		target = squares[1];

		piece = source.getPiece();
	}

	@Override
	public Square getTarget() {
		return target;
	}

	@Override
	public Square getSource() {
		return source;
	}

	@Override
	public void doMove() throws GameOver {
	}

	@Override
	public void undoMove() {
	}

	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
}
