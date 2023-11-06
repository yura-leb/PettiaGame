package renju.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.players.PutPiecePlayer;
import renju.moves.RenjuMove;

/**
 * Реализовать алгоритм игры в рендзю (крестики - нолики).
 * TODO Синёв  Олег
 *
 */
public class Turtle extends PutPiecePlayer {
	private int MAX_MOVES = 80;
	final Comparator<? super Move> brain = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);
	
	public Turtle(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	private int getMoveWeight(Move m) {
		RenjuMove move = (RenjuMove) m;

		Piece piece = move.getPiece();
		Square square = move.getSquare();

		return getNearFriendsCount(piece, square);
	}

	private int getNearFriendsCount(Piece piece, Square square) {
		List<Square> allSquares = square.getBoard().getSquares();

		int k = 0;
		for (Square s : allSquares) {
			if (s.isEmpty())
				continue; // пустая клетка.
			if (s.getPiece().getColor() != piece.getColor())
				continue; // На клетке фигура другого цвета.
			if (s.isHorizontal(square)) k++; // проверяем свои фигуры по горизонтали
				//continue;
			if (s.isDiagonal(square)) k++; // проверяем свои фигуры по диагонали
			if (s.isVertical(square)) k++; // проверяем свои фигуры по вертикали
			if (s.isNear(square)) k++; // проверяем ближайший ход
		}
		return k;
	}

	@Override
	public String getName() {
		return "Тортила";
	}

	@Override
	public String getAuthorName() {
		return "Синёв С. Олег";
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);

		if (correctMoves.isEmpty())
			throw new GameOver(GameResult.DRAWN);

		Collections.shuffle(correctMoves);

		correctMoves.sort(brain);
		Move bestMove;
		bestMove = correctMoves.get(0);

		try {
			bestMove.doMove();
		} catch (GameOver e) {

			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.addMove(bestMove);
			board.history.setResult(e.result);

			// Просим обозревателей доски показать
			// положение на доске, сделанный ход и
			// результат игры.
			board.setBoardChanged();

			throw new GameOver(GameResult.DRAWN);
		}

		// Сохраняем ход в истории игры.
		board.history.addMove(bestMove);

		// Просим обозревателей доски показать
		// положение на доске, сделанный ход и
		// результат игры.
		board.setBoardChanged();

		// Для отладки ограничим количество ходов в игре.
		// После этого результат игры ничья.
		if (board.history.getMoves().size() > MAX_MOVES ) {
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);

			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}

	}
}
