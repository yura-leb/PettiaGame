package camelot.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

public class SimpleMove implements ITransferMove {
	private Piece piece;
	private Square source;
	private Square target;

	public SimpleMove(Piece piece, Square source, Square target) {
		this.source = source;
		this.target = target;
		this.piece = piece;
	}

	@Override
	public Piece getPiece() {
		return piece;
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
	public Square getSource() {
		return source;
	}

	@Override
	public Square getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}
}


