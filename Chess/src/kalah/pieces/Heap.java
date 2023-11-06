package kalah.pieces;

import java.util.List;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import kalah.KalahBoard;
import kalah.moves.SimpleMove;

/**
 *  Кучка шариков на клетке доски.
 */
public class Heap extends Piece {
	public int nBalls = 0;

	public Heap(Square square, int n) {
		super(square, PieceColor.WHITE);
		nBalls = n;
	}
	
	/**
	 * В калахе цвет фигуры (какому игроку фигуры принадлежат)
	 * определяется тем на какой стороне фигура.
	 * @return вернуть цвет фигуры.
	 */
	public PieceColor getColor() {
		return KalahBoard.isWhiteSide(square) 
				? PieceColor.WHITE : PieceColor.BLACK;
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		if (KalahBoard.isWhiteKalah(square))
			return false; // Из калаха фигуры не ходят
		
		if (KalahBoard.isBlackKalah(square))
			return false; // Из калаха фигуры не ходят

		// Из пустой клетки шарики не раскладывают.
		if (nBalls == 0)
			return false;
		
		Square checkedSquare = squares[0];

		KalahBoard board = (KalahBoard) square.getBoard();
		
		// По каким клеткам раскладываем шарики
		// из текущей клетки.
		List<Square> way = KalahBoard.isWhiteSide(square) 
				                ? board.whiteWay : board.blackWay;
		
		// Позиция текущей клетки с шариками.
		int curPos = way.indexOf(square);
		
		// Клетка куда положат последний шарик из кучи.
		int nextPos = (curPos + nBalls) % way.size();
		Square target = way.get(nextPos);

		// Попадет ли последний шарик в клетку checkedSquare.
		return target == checkedSquare;
	}

	@Override
	public Move makeMove(Square... squares) {
		return new SimpleMove(this);
	}
}
