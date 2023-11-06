package breakthrough.players;

import static java.lang.Math.abs;

import breakthrough.moves.Capture;
import breakthrough.moves.SimpleMove;
import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

public class Poseidon extends BreakThroughPlayer {

	@Override
	public String getName() {
		return "Посейдон";
	}

	@Override
	public String getAuthorName() {
		return "Романов В.Ю";
	}

	@Override
	protected int getWeight(Move m) {
		SimpleMove move = (SimpleMove) m;

		// Ход с захватом вражеских фигур лучше.
		if (move instanceof Capture)
			return 100;

		Piece piece = move.getPiece();
		Board board = piece.square.getBoard();
		Square target = move.getTarget();

		// Расстояние до последней горизонтали (цели).
		int dh = abs(board.nH - 1 - target.h);
		int distance = piece.isBlack() ? dh : -dh; 

		return -distance; // Чем меньше расстояние до цели тем лучше ход.
	}
}
