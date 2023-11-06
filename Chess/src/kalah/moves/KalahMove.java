package kalah.moves;

import java.util.List;

import game.core.Move;
import game.core.Piece;
import game.core.Square;
import kalah.KalahBoard;
import kalah.pieces.Heap;

/**
 * Базовый класс для всех ходов в калахе.
 */
abstract public class KalahMove implements Move {
	/**
	 * Кучка шариков на клетке доски.
	 */
	protected Heap heap;
	
	/**
	 * Путь - клетки для раскладывания шариков из текущей клетки.
	 */
	protected List<Square> way;
	
	public KalahMove(Heap heap) {
		this.heap = heap;
		
		KalahBoard board = (KalahBoard) heap.square.getBoard();
		
		way = (isWhiteSide(heap) ? board.whiteWay : board.blackWay);
	}

	/**
	 * На какой стороне расположена кучка шариков.
	 * @param heap фигура (кучка шариков).
	 * @return на стороне белых или нет.
	 */
	static
	public boolean isWhiteSide(Heap heap) {
		return KalahBoard.isWhiteSide(heap.square);
	}

	@Override
	public Piece getPiece() {
		return heap;
	}
}