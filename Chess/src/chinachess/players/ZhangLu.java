package chinachess.players;

import java.util.Comparator;

import game.core.Move;

public class ZhangLu extends ChinaChessPlayer {
	private final Comparator<? super Move> brain = (m1, m2) -> getWeight(m2) - getWeight(m1);

	@Override
	public String getName() {
		return "Чжан Лу";
	}

	@Override
	public String getAuthorName() {
		return "Чжан Сянжуй";
	}
	
	@Override
	public Comparator<? super Move> getComparator() {
		return brain;
	}
	
	private int getWeight(Move move) {
		return 0;
	}
}
