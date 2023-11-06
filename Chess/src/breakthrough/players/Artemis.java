package breakthrough.players;

import game.core.Move;

/**
 * TODO Ермакова - реализовать алгорим
 * <a href=
 * "https://ru.wikipedia.org/wiki/%D0%90%D1%80%D1%82%D0%B5%D0%BC%D0%B8%D0%B4%D0%B0">
 * Артемида</a> вечно юная богиня охоты, богиня женского целомудрия,
 * покровительница всего живого на Земле, дающая счастье в браке и помощь при родах,
 * позднее богиня Луны
 */
public class Artemis extends BreakThroughPlayer {
	@Override
	public String getName() {
		return "Артемида";
	}

	@Override
	public String getAuthorName() {
		return "Ермакова";
	}

	protected int getWeight(Move m2) {
		// TODO  
		return 0;
	}
}
