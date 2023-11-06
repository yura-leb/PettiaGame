package pettia.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.PieceColor;
import game.players.MovePiecePlayer;

public abstract class PettiaPlayer extends MovePiecePlayer {
	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
			List<Move> correctMoves = getCorrectMoves(board, color);
			
			if (correctMoves.isEmpty())
				return;
	
			Collections.shuffle(correctMoves);
			
			correctMoves.sort( getComparator() );
			Move bestMove = correctMoves.get(0);
			
			try { bestMove.doMove(); } 
			catch (GameOver e) {
				// Сохраняем в истории игры последний сделанный ход 
				// и результат игры.
				board.history.addMove(bestMove);
				board.history.setResult(e.result);
				
				// Просим обозревателей доски показать 
				// положение на доске, сделанный ход и 
				// результат игры.
				board.setBoardChanged();
				
				// Распространяем инфрмацию об окончании игры.
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
		int maxMoves = 180;
		if (board.history.getMoves().size() > maxMoves) {
				// Сохраняем в истории игры последний сделанный ход 
				// и результат игры.
				board.history.setResult(GameResult.DRAWN);
				
				// Сообщаем что игра закончилась ничьей.
				throw new GameOver(GameResult.DRAWN);
			}
		}

	/**
	 * Алгоритм выбора лучшего хода реализуется в клессах - потомках.
	 * @return получить алгоритм сравнеия
	 */
	abstract protected Comparator<? super Move> getComparator();
}