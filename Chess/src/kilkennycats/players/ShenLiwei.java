package kilkennycats.players;

import java.util.Collections;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.PieceColor;
import game.players.MovePiecePlayer;

/**
 */
public class ShenLiwei extends MovePiecePlayer {
	private final int maxMoves;

	public ShenLiwei() {
		this(80);
	}

	public ShenLiwei(int maxMoves) {
		this.maxMoves = maxMoves;
	}

	@Override
	public String getName() {
		return "Xia Zekai";
	}

	@Override
	public String getAuthorName() {
		return "Xia Zekai";
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);

		if (correctMoves.isEmpty())
			return;

		Collections.shuffle(correctMoves);
		Move randomMove = correctMoves.get(0);

		try {
			randomMove.doMove();
		} catch (GameOver e) {
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.addMove(randomMove);
			board.history.setResult(e.result);

			// Просим обозревателей доски показать
			// положение на доске, сделанный ход и
			// результат игры.
			board.setBoardChanged();

			throw new GameOver(e.result);
		}

		// Сохраняем ход в истории игры.
		board.history.addMove(randomMove);

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

	@Override
	public String toString() {
		return getName();
	}
}