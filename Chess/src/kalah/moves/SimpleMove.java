package kalah.moves;

import game.core.GameOver;
import game.core.Square;
import kalah.pieces.Heap;

public class SimpleMove extends KalahMove {
	private int nBalls = 0;

	public SimpleMove(Heap heap) {
		super(heap);
	}

	@Override
	public void doMove() throws GameOver {
		// Раскладываем шарики по кругу по клеткам на пути 
		// заданном для этой клетки доски.
		nBalls = heap.nBalls;
		
		// Клетка шарики из которой начинаем раскладывать.
		Square start = heap.square;

		// Номер клетки с шариками.
		int kSquare = way.indexOf(start);
		
		int length = way.size();
		
		for (int k = 0; k < nBalls; k++) {
			kSquare++;
			if (kSquare >= length)
				kSquare = 0; // Возврат в начало пути.
			
			Heap nextHeap = (Heap) way.get(kSquare).getPiece();
			nextHeap.nBalls++;
		}
		
		heap.nBalls = 0;
	}

	@Override
	public void undoMove() {
		// Клетка шарики из которой начинали раскладывать.
		Square start = heap.square;

		// Номер клетки с шариками.
		int kSquare = way.indexOf(start);
		
		int length = way.size();
		
		for (int k = 0; k < nBalls; k++) {
			kSquare++;
			if (kSquare >= length)
				kSquare = 0; // Возврат в начало пути.
			
			Heap nextHeap = (Heap) way.get(kSquare).getPiece();
			nextHeap.nBalls--;
		}
		
		heap.nBalls = nBalls;
	}
	
	@Override
	public String toString() {
		return "" + heap.square;
	}
}
