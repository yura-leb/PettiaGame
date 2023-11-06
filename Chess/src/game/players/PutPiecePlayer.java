package game.players;

import java.util.ArrayList;
import java.util.List;

import game.core.Board;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для программ-игроков ставящих фигури на доску.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class PutPiecePlayer implements IPlayer {
	/**
	 * Интерфейс для выдачи фигуры заданного цвета.
	 * Это интерфейс должен быть реализованн для каждой игры. 
	 */
	protected IPieceProvider pieceProvider;

	public PutPiecePlayer(IPieceProvider pieceProvider) {
		this.pieceProvider = pieceProvider;
	}

	/**
	 * Фигура ставится на доску. Выдать все корректные ходы на доске фигурой
	 * заданного цвета.
	 * 
	 * @param board
	 *            - доска на которой идет игра.
	 * @param color
	 *            - цвет фигуры которая должна сделать ход.
	 * @return список допустимых ходов.
	 */
	public List<Move> getCorrectMoves(Board board, PieceColor color) {
		List<Move> allCorrectMoves = new ArrayList<>();
	
		// Соберем на доске все пустые клетки.
		List<Square> emptySquares = board.getEmptySquares();
		if (emptySquares.isEmpty())
			return allCorrectMoves; // Пустых клеток нет, ходить некуда.
	
		// Какого цвета фигура должна пойти.
		PieceColor moveColor = board.getMoveColor();
	
		// Получим НЕ стоящую на клетке фигуру заданного цвета.
		Square square = emptySquares.get(0);
		Piece piece = pieceProvider.getPiece(square, moveColor);
		piece.remove(); // Уберем фигуру с клетки доски.
		
		// Соберем пустые клетки, на которые можно поставить 
		// новую фигуру заданного цвета.
		
		for (Square emptySquare : emptySquares)  
			if (piece.isCorrectMove(emptySquare)) {
				Move m = piece.makeMove(emptySquare);
				allCorrectMoves.add(m);
			}
	
		return allCorrectMoves;
	}
}