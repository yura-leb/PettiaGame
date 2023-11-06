package kalah;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import game.core.Board;
import game.core.Square;

public class KalahBoard extends Board {
	public List<Square> whiteWay;
	public List<Square> blackWay;

	public void initWays() {
		whiteWay = new LinkedList<>();

		Collections.addAll(whiteWay,
			getSquare(1, 1), 
			getSquare(2, 1), 
			getSquare(3, 1), 
			getSquare(4, 1),
			getSquare(5, 1), 
			getSquare(6, 1),

			// Калах белых внизу справа.
			getWhiteKalah(), 

			getSquare(6, 0), 
			getSquare(5, 0), 
			getSquare(4, 0), 
			getSquare(3, 0),
			getSquare(2, 0), 
			getSquare(1, 0)
		);
		
		blackWay = new LinkedList<>();

		Collections.addAll(blackWay,
			getSquare(6, 0), 
			getSquare(5, 0), 
			getSquare(4, 0), 
			getSquare(3, 0),
			getSquare(2, 0), 
			getSquare(1, 0),

			// Калах черных вверху слева.
			getBlackKalah(), 

			getSquare(1, 1), 
			getSquare(2, 1), 
			getSquare(3, 1), 
			getSquare(4, 1),
			getSquare(5, 1), 
			getSquare(6, 1)
		);
	}
	
	/**
	 * На какой стороне расположена клетка.
	 * @param square - клетка
	 * @return на белой ли стороне
	 */
	static
	public boolean isWhiteSide(Square square) {
		return square.h == 1;
	}
	
	static
	public boolean isBlackKalah(Square square) {
		return (square.h == 0) && (square.v == 0);
	}

	static
	public boolean isWhiteKalah(Square square) {
		return (square.h == 1) && (square.v == 7);
	}
	
	public Square getBlackKalah() {
		return getSquare(0, 0);
	}

	public Square getWhiteKalah() {
		return getSquare(7, 1);
	}
}
