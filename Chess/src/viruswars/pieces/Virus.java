package viruswars.pieces;

import java.util.List;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import viruswars.moves.VirusMove;

public class Virus extends Piece {
	public boolean isZombi = false;

	public Virus(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[0];

		// Соберем захваченные вражеские фигуры.
		List<Square> captured = collectZombed(target);
		
		return new VirusMove(this, target, captured);
	}

	private List<Square> collectZombed(Square target) {
		// TODO Auto-generated method stub
		return null;
	}
}

