package viruswars.moves;

import java.util.List;
import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.IPutMove;

/**
 */
public class VirusMove implements IPutMove {
	/**
	 * Клетка на которую ставится фигура.
	 */
	final Square target;

	/**
	 * Зомби остаются неизменными на доске до конца партии, 
	 * т.е. они не могут быть "оживлены", "восстановлены" 
	 * или удалены с доски.
	 */
	final List<Square> zombed;

	/**
	 * Фигура которая делает ход.
	 */
	private final Piece piece;

	/**
	 * Создать ход игры в реверси.
	 * 
	 * @param target - клетка на которую идет фигура
	 * @param zombed - клетки на которых стоят зомбированные вирусы.
	 */
	public VirusMove(Piece piece, Square target, List<Square> zombed) {
		this.target = target;
		this.zombed = zombed;

		this.piece = piece;
	}

	@Override
	public void doMove() throws GameOver {
	}

	@Override
	public void undoMove() {
	}

	@Override
	public String toString() {
		return "" + target;
	}

	@Override
	public Square getTarget() {
		return target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
}
