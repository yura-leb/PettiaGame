package kalah.moves;

import game.core.GameOver;
import kalah.pieces.Heap;

/**
 * TODO Игорь Владимиров. Реализовать ход с захватом фигур противника.
 * 
 */
public class Capture extends SimpleMove {
	public Capture(Heap heap) {
		super(heap);
	}

	@Override
	public void doMove() throws GameOver {

	}

	@Override
	public void undoMove() {
	}

}
