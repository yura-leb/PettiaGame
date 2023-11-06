package pettia.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.PieceColor;

/**
 * TODO Веремчук Алексей
 */
public class Pythagoras extends PettiaPlayer {
	private int MAX_MOVES = 80;
	final Comparator<? super Move> brain = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);
	
	private int getMoveWeight(Move m) {
		return 0;
	}

	@Override
	public String getName() {
		return "Пифагор";
	}

	@Override
	public String getAuthorName() {
		return "Веремчук Алексей";
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

	@Override
	protected Comparator<? super Move> getComparator() {
		return brain;
	}
}
