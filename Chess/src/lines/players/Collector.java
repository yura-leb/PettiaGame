package lines.players;

import game.core.Board;
import game.core.GameOver;
import game.core.PieceColor;
import game.players.Neznaika;

/**
 * Игрок Collector перемещает фишку на пустое поле
 * выстраивая фишки одного цвета в линию.
 * Выстроенные в линию 4 фишки снимаются с доски.
 * Если все клетки пустые, Collector выиграл.
 */
public class Collector extends Neznaika {
	@Override
	public String getName() {
		return "Гена";
	}

	@Override
	public String getAuthorName() {
		return "???";
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		super.doMove(board, color);
		
		// Алгоритм должен отбирать из всех допустимых ходов ходы StoneMove.
		// Затем он сортирует оставшиеся ходы в зависимости от рейтинга хода
		// и делает лучший ход.
	}
}
