package mingmang.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

/**
 * Gеремещение фигуры на пустую клетку.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
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

	public SimpleMove(Square[] squares) {
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
		piece.moveTo(target);
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
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
