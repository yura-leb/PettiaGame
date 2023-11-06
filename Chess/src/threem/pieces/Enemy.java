package threem.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import threem.moves.SimpleMove;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Enemy extends Piece {
	public Enemy(Square s, PieceColor color) {
		super(s, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square origin = this.square;
		List<Square> moves = Arrays.stream(squares)
				.filter(Square::isEmpty)
				.filter(square -> square.isNear(origin))
				.filter(square -> square.isHorizontal(origin) || square.isVertical(origin))
				.collect(Collectors.toList());

		return !moves.isEmpty();
	}

	@Override
	public Move makeMove(Square... squares) {

		return new SimpleMove(squares);
	}
	
	@Override
	public String toString() {
		return "";
	}
}
