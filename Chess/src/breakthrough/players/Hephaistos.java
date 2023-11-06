package breakthrough.players;

import breakthrough.moves.Capture;
import game.core.Move;
import game.core.Piece;

/**
 * TODO Пришлецов Дмитрий
 * <a href=
 * "https://ru.wikipedia.org/wiki/%D0%93%D0%B5%D1%84%D0%B5%D1%81%D1%82">
 * Гефест</a>
 */
public class Hephaistos extends BreakThroughPlayer {
	@Override
	public String getName() {
		return "Гефест";
	}

	@Override
	public String getAuthorName() {
		return "Пришлецов Дмитрий";
	}

	protected int getWeight(Move m2) {
		Piece i = m2.getPiece();
		// Игра "от обороны"
		int firstLine = (i.isBlack() ? 0 : 4); // первая линия
		int secondLine = (i.isBlack() ? 1 : 3); // вторая линия
		int winLine = (i.isBlack() ? 3 : 1); // предпоследняя линия
		if (i.square.h == winLine) {
			return 3; // ура! выиграли!
		}
		if (i.square.h == firstLine) {
			if (m2 instanceof Capture) { // бить фигурами первой линии - высший приоритет
				return 2;
			}
			else
				return -1; // ходить фигурами первой линии - наименьший приоритет
		}
		if (i.square.h == secondLine && m2 instanceof Capture) {
			return 1; // бить фигурами второй линии - средний приоритет
		}
		return 0;
	}
}
