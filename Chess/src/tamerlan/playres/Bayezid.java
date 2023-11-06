package tamerlan.playres;

import java.util.Comparator;

import game.core.Move;

/**
 * Баязид - турецкий султан.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Bayezid extends TamerlanChessPlayer {
	final Comparator<? super Move> brain 
		= (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

	@Override
	public String getName() {
		return "Баязид (Турция)";
	}

	@Override
	public String getAuthorName() {
		return "???";
	}

	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	Comparator<? super Move> getComparator() {
		return brain;
	}

	/**
	 * Задать вес для хода.
	 * @param move - ход
	 * @return оценка хода.
	 */
	private int getMoveWeight(Move move) {
		return 0;
	}
}