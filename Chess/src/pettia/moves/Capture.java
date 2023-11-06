package pettia.moves;

import static java.util.stream.Collectors.toList;

import java.util.List;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;

public class Capture extends SimpleMove implements ICaptureMove {
	/**
	 * Захваченные фигуры противника.
	 */
	private final List<Piece> captured;

	public Capture(List<Piece> captured, Square[] squares) {
		super(null, squares);
		this.captured = captured;
	}

	@Override
	public List<Square> getCaptured() {
		return captured
			.stream()
			.map(p -> p.square)
			.collect( toList() );
	}

	public List<Piece> getCapturedPieces() {
		return captured;
	}

	@Override
	public void doMove() throws GameOver {
		super.doMove();
	}
	
	@Override
	public void undoMove() {
		super.undoMove();
	}

	public String toString() {
		return "" + piece + source + "x" + target;
	}
}
