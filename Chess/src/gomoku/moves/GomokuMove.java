package gomoku.moves;

import game.core.*;
import game.core.Move;

import java.util.List;


public class GomokuMove implements Move {
    final Square target;

    final List<Integer> captured;

	private final Piece piece;

	public GomokuMove(Piece piece, Square target, List<Integer> captured) {
		this.target = target;
		this.captured = captured;
		
		this.piece = piece;
	}

	@Override
	public void doMove() throws GameOver {
		target.setPiece(piece);
		Board board = piece.square.getBoard();

		List<Square> empties = board.getEmptySquares();
		int max = 0;
		boolean countFive = false;
		for (Integer i: this.captured) {
			if (max < i) max = i;
			if (i == 5) countFive = true;
		}
		System.out.println(max);
		if (empties.isEmpty() || countFive) throw new GameOver( GameResult.win(piece));
	}

	@Override
	public void undoMove() {
		piece.remove();
	}


	@Override
	public String toString() {
		return "" + target;
	}


	@Override
	public Piece getPiece() {
		return piece;
	}
}
