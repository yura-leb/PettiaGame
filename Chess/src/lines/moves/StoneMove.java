package lines.moves;

import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;
import lines.pieces.Stone;

public class StoneMove implements ITransferMove {

	public StoneMove(Stone stone, Square[] squares) {
		source = squares[0];
		target = squares[1];
		
		piece = source.getPiece();	}

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

	@Override
	public Square getTarget() {
		return target;
	}
	
	@Override
	public Square getSource() {
		return source;
	}

	@Override
	public void doMove() {
		piece.moveTo(target);
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
	}
	
	@Override
	public String toString() {
		return "" + source + "-" + target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
}
