package chinachess.players;

import java.util.Comparator;

import game.core.Move;

public class Laozi extends ChinaChessPlayer {
	private final Comparator<? super Move> brain = (m1, m2) -> getWeight(m2) - getWeight(m1);

	@Override
	public String getName() {
		return "ЛаоЦзи";
	}

	@Override
	public String getAuthorName() {
		return "Сюй Юань";
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	Comparator<? super Move> getComparator() {
		return brain;
	}

	private int getWeight(Move move) {
		return 0;
	}
}