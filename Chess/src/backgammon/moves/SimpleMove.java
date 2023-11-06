package backgammon.moves;

import backgammon.BackgammonBoard;
import backgammon.pieces.BackgammonGroup;
import backgammon.pieces.Stone;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

public class SimpleMove implements ITransferMove {
	/**
	 * Фигура которая делает ход.
	 */
	protected BackgammonGroup piece;
	
	protected Square source;
	protected Square target;
	
	public SimpleMove(Square source, Square target) {
		this.source = source;
		this.target = target;

		piece = (BackgammonGroup) source.getPiece();
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
	public Piece getPiece() {
		return piece;
	}

	@Override
	public void doMove() throws GameOver {
		doMove(source, target);
	}
	
	@Override
	public void undoMove() {
		try {
			doMove(target, source);
		} catch (GameOver e) { }
	}

	static
	public void doMove(Square source, Square target) throws GameOver {
		BackgammonGroup sourceGroup = (BackgammonGroup) source.getPiece();
		BackgammonBoard board = (BackgammonBoard) target.getBoard();
		
		Stone stone = sourceGroup.pushStone();

		if (sourceGroup.isEmpty())
			sourceGroup.remove();
		
		BackgammonGroup targetGroup;

		if (target.isEmpty())
			targetGroup = new BackgammonGroup(target, stone);
		else {
			targetGroup = (BackgammonGroup) target.getPiece();
			targetGroup.add(stone);
		}
		target.setPiece(targetGroup);
		
		if (board.isForBearing(target, target.getPiece().getColor())) {
			if (targetGroup.size() == 15) {
				throw new GameOver( GameResult.win(target.getPiece().getColor()) );
			}
		}
	}

	@Override
	public String toString() {
		return "" + source + "-" + target;
	}
}