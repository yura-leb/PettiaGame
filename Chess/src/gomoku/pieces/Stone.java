package gomoku.pieces;

import java.util.ArrayList;
import java.util.List;

import game.core.Board;
import game.core.Dirs;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import gomoku.moves.GomokuMove;

public class Stone extends Piece {

	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		return target.isEmpty();
	}



	private int numCaptured(Square source, Dirs direction){
		Board board = source.getBoard();

		int sv = source.v + direction.dv;
		int sh = source.h + direction.dh;

		PieceColor myColor = getColor();
		int nCaptured = 0;

		while (board.onBoard(sv, sh)) {
			Square nextSquare = board.getSquare(sv, sh);

			if (nextSquare.isEmpty())
				return nCaptured;

			PieceColor nextColor = nextSquare.getPiece().getColor();

			if (nextColor != myColor)
				return nCaptured;

			nCaptured++;

			sv += direction.dv;
			sh += direction.dh;
		}

		return nCaptured;

	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[0];
		List<Integer> captured = collectCaptured(target);

		return new GomokuMove(this, target, captured);
	}

	private List<Integer> collectCaptured(Square target) {
		List<Integer> numsCaptured = new ArrayList<Integer>();
		Dirs[][] dir = {Dirs.HORIZONTAL, Dirs.VERTICAL, {Dirs.LEFT_DOWN, Dirs.RIGHT_UP},{Dirs.RIGHT_DOWN, Dirs.LEFT_UP}};
		int n;
		for (Dirs[] directions : dir) {
			n = 0;
			for (Dirs direction : directions)
				n += numCaptured(target, direction);
			numsCaptured.add(n + 1);
		}

		return numsCaptured;
	}

	@Override
	public String toString() {
		return "" + square;
	}
}
