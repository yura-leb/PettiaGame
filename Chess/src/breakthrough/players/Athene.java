package breakthrough.players;

import game.core.Move;

/**
 * <a href=
 * "https://ru.wikipedia.org/wiki/%D0%90%D1%84%D0%B8%D0%BD%D0%B0">
 * Афина</a>
 *
 */
public class Athene extends BreakThroughPlayer {
	@Override
	public String getName() {
		return "Афина";
	}

	@Override
	public String getAuthorName() {
		return "???";
	}

	protected int getWeight(Move m2) {
		// TODO  
		return 0;
	}
}
