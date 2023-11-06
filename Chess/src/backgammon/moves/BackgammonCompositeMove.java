package backgammon.moves;

import java.util.ArrayList;
import java.util.List;

import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Серия ходов в нардах.
 */
public class BackgammonCompositeMove implements Move {
	/**
	 * Значение на 1-ом кубике.
	 */
	int cube1Value;
	
	/**
	 * Значение на 2-ом кубике.
	 */
	int cube2Value;

	/**
	 * Серия ходов сделанная с использованием значений на кубиках.
	 */
	public List<Move> moves = new ArrayList<>();
	
	public BackgammonCompositeMove(int cube1, int cube2, Square square, Square target) {
		this.cube1Value = cube1;
		this.cube2Value = cube2;
		
		this.moves.add(new SimpleMove(square, target));
	}

	@Override
	public Piece getPiece() {
		return null;
	}

	@Override
	public void doMove() throws GameOver {
		moves.forEach(m -> {
			try {
				m.doMove();
			} catch (GameOver e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void undoMove() {
		moves.forEach(m -> m.undoMove());
	}
	
	@Override
	public String toString() {
		String txt = String.format("(%d,%d) ", cube1Value, cube2Value);
		
		for(Move m : moves)
			txt += "" + m + " ";
		
		return txt;
	}
}