package renju.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.PieceColor;
import game.players.PutPiecePlayer;

public abstract class RenjuPlayer extends PutPiecePlayer {

	private static final int MAX_MOVES = 180;
	final Comparator<? super Move> brain = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);
	
		if (correctMoves.isEmpty())
			throw new GameOver(GameResult.DRAWN);
	
		Collections.shuffle(correctMoves);
	
		// Буратино выбирает лучший ход.
		correctMoves.sort(brain);
		
		Move bestMove = correctMoves.get(0);
	
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
		if (board.history.getMoves().size() > MAX_MOVES) {
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);
	
			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}

	protected abstract int getMoveWeight(Move m2);

	public RenjuPlayer(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}
}
