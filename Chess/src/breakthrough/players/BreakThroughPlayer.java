package breakthrough.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.PieceColor;
import game.players.MovePiecePlayer;

public abstract class BreakThroughPlayer extends MovePiecePlayer {

	private final Comparator<? super Move> brain = (m1, m2) -> getWeight(m2) - getWeight(m1);
	private static final int maxMoves = 180;

	public BreakThroughPlayer() {
		super();
	}

	protected abstract int getWeight(Move m);

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);
	
		if (correctMoves.isEmpty())
			return;
	
		Collections.shuffle(correctMoves);
	
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
	
			throw new GameOver(e.result);
		}
	
		// Сохраняем ход в истории игры.
		board.history.addMove(bestMove);
	
		// Просим обозревателей доски показать
		// положение на доске, сделанный ход и
		// результат игры.
		board.setBoardChanged();
	
		// Для отладки ограничим количество ходов в игре.
		// После этого результат игры ничья.
		if (board.history.getMoves().size() > maxMoves) {
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);
	
			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}

}