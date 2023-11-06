package chinachess.players;

import java.util.Comparator;

import game.core.Move;
import game.players.IPlayer;

public class ZhangDaoling extends ChinaChessPlayer implements IPlayer {
	private final Comparator<? super Move> brain = (m1, m2) -> getWeight(m2) - getWeight(m1);

	@Override
	public String getName() {
		return "Чжан Даолин";
	}

	@Override
	public String getAuthorName() {
		return "Ляо Юхао";
	}

	@Override
	Comparator<? super Move> getComparator() {
		return brain;
	}

	private int getWeight(Move m) {
		return 0;
	}
}
