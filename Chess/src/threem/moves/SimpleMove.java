package threem.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;
import game.core.Board;
import game.core.PieceColor;
import game.core.GameResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleMove implements ITransferMove {
	public Square source;
	public Square target;
	public Piece piece;

	public SimpleMove(Square[] squares) {
		source = squares[0];
		target = squares[1];
		
		piece = source.getPiece();
	}
	
	@Override
	public Piece getPiece() {
		return piece;
	}

	private void checkGameFinished(Board board) throws GameOver {
		List<Piece> musketeers = board.getPieces(PieceColor.BLACK);

		Map<Integer, Integer> horizontalCounter = new HashMap<>();
		Map<Integer, Integer> verticalCounter = new HashMap<>();

		for (Piece piece : musketeers) {
			int h = piece.square.h;
			horizontalCounter.merge(h, 1, Integer::sum);

			int v = piece.square.v;
			verticalCounter.merge(v, 1, Integer::sum);
		}

		if (verticalCounter.containsValue(3) || horizontalCounter.containsValue(3)) {
			throw new GameOver(GameResult.WHITE_WIN);
		}
	}

	@Override
	public void doMove() throws GameOver {
		piece.moveTo(target);
		checkGameFinished(target.getBoard());
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
