package chinachess.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import chinachess.pieces.Bishop;
import chinachess.pieces.Guardian;
import chinachess.pieces.Gun;
import chinachess.pieces.King;
import chinachess.pieces.Knight;
import chinachess.pieces.Pawn;
import chinachess.pieces.Rook;
import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.players.MovePiecePlayer;

/**
 * Базовый класс для всех игроков в китайские шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract 
public class ChinaChessPlayer extends MovePiecePlayer {
	private static final int maxMoves = 180;

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

	/**
	 * Алгоритм сравнения ходов для выбора лучшего хода
	 * будет реализован в классах - потомках.
	 * 
	 * @return - алгоритм сравнения ходов.
	 */
	abstract Comparator<? super Move> getComparator();
	
	/**
	 * Максимальное расстояние между клетками доски:
	 * ширина доски + длина доски.
	 */
	protected static final int MAX_DISTANCE = 9 + 10;

	/**
	 * Ценность (вес) поля на доске. 
	 * Чем ближе поле к центру, тем оно лучше.
	 * 
	 * @param s
	 *            - поле
	 * @return вес поля.
	 */
	static	public  int getSquareWeight(final Square s) {
		Board board = s.getBoard();
		final int tv = s.v;
		final int th = s.h;
		
		final double dv = Math.abs(tv - 0.5 * (board.nV-1) );
		final double dh = Math.abs(th - 0.5 * (board.nH-1) );
		int distance = (int) (dv + dh);
		
		return MAX_DISTANCE - distance;
	}

	/**
	 * Найти короля у фигур - врагов для фигуры piece.
	 * 
	 * @param piece
	 *            - фигура для которой ищем врага-короля.
	 * @return вражеский король.
	 */
	protected King getEnemyKing(Piece piece) {
		return piece.getEnemies()
		.stream()
		.filter(enemy -> enemy instanceof King)
		.map(enemy -> (King) enemy)
		.findFirst()
		.get();
	}

	/**
	 * Дать вес фигуры
	 * 
	 * @param p
	 *            - измеряемая фигура.
	 * @return ценность фигуры.
	 */
	protected int getWeight(Piece p) {
		if (p instanceof King)   return 1000;
		if (p instanceof Guardian)  return  900;
		if (p instanceof Gun)    return  800;
		if (p instanceof Rook)   return  700;
		if (p instanceof Bishop) return  600;
		if (p instanceof Knight) return  500;
		if (p instanceof Pawn)   return  400;
		
		return 0;
	}
}