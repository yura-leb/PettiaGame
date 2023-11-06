package game.core;

import java.util.ArrayList;
import java.util.List;

/**
 * История игры (список ходов сделанных фигурами на доске).
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class History {
	/**
	 * Номер текущего хода на доске.
	 */
	int curMove = -1;
	
	/**
	 * Список ходов сделанных на доске.
	 */
    final List<Move> moves = new ArrayList<>();

	/**
	 * Доска на которой идет игра.
	 */ 
	private Board board;

	/**
	 * Результат игры.
	 */
	private GameResult result = GameResult.UNKNOWN;

	public History(Board board) {
		setBoard(board);
	}
	
	public void clear() {
		curMove = -1;
		moves.clear();
		result = GameResult.UNKNOWN;
	}

	/**
	 * Добавить ход в историю игрыs.
	 * @param move
	 */
	public void addMove(Move move) {
		moves.add(move);
		curMove++;
	}
	
	/**
	 * Вернуть ходы сделанные в игре.
	 * @return
	 */
	public List<Move> getMoves() {
		return moves;
	}

	/**
	 * Вернуть номер текущего хода.
	 * @return
	 */
	public int getCurMoveNumber() {
		return curMove;
	}
	
	/**
	 * Вернуть текущий ход.
	 * @return
	 */
	public Move getCurMove() {
		return curMove == -1 ? null : moves.get(curMove);
	}
	
	/**
	 * Сместиться на первый ход.
	 */
	public void toFirstMove() {
		for (; curMove >=1; curMove--)
			moves.get(curMove).undoMove();
	}
	
	/**
	 * Сместиться на последний ход.
	 */
	public void toLastMove() {
		for (; curMove < moves.size()-1; curMove++)
			try {
				moves.get(curMove).doMove();
			} catch (GameOver e) {
				setResult(e.result);
			}
	}
	
	/**
	 * Сместиться на следующий ход.
	 */
	public void toNextMove() {
		if (curMove < moves.size()-1)
			try { moves.get(++curMove).doMove(); } 
			catch (GameOver e) { setResult(e.result); }
	}
	
	/**
	 * Сместиться на предыдущий ход.
	 */
	public void toPrevMove() {
		if (curMove >= 0) 
			moves.get(curMove--).undoMove();
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @return Вернуть последний ход.
	 */
	public Move getLastMove() {
		if (moves.isEmpty()) return null;
		
		return moves.get( moves.size()-1 );
	}

	/**
	 * Выдать номер хода.
	 * @param move - для какого хода выдается номер.
	 */
	public int getMoveNumber(Move move) {
		return moves.indexOf(move);
	}

	/**
	 * Сместиться на ход с номером <b>n</b>.
	 * @param n - номер хода
	 */
	public void toMove(int n) {
		if (n < curMove)
			while (n < curMove)
				toPrevMove();
		
		if (curMove < n)
			while (curMove < n)
				toNextMove();
	}

	/**
	 * Задать результат игры.
	 * 
	 * @param result
	 */
	public void setResult(GameResult result) {
		this.result = result;
	}

	/**
	 * Получить результат игры.
	 * @return
	 */
	public GameResult getResult() {
		return result;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		int k = 0;
		
		for(Move m : moves) {
			boolean odd = ((k % 2) == 0);
			String number = (!odd ? "" : "" + (1+k/2) + ". ");
			String nl = (!odd ? "\n" : "");
			
			s.append(String.format("%s%s %s", number, m, nl));
			k++;
		}
		
		return s.toString();
	}
}
