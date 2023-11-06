package tools.tourney;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

import java.util.List;

import game.core.Game;
import game.players.IPlayer;

/**
 * Турнир алгоритмов по круговой системе.
 */
public class RoundTourney extends Competition {
	private final Game[][] table = new Game[players.size()][players.size()];

	{
		for (int row = 0; row < players.size(); row++)
			for (int col = 0; col < players.size(); col++)
				table[row][col] = newGame(gameClass);
	}

	private final double[] scores;
	private final List<Integer> sorted;

	public RoundTourney(Class<? extends Game> gameClass) {
		super(gameClass);
		scores = new double[players.size()];
		sorted = rangeClosed(0, scores.length - 1).boxed().collect(toList());
	}

	@Override
	public void run() {
		for (int row = 0; row < players.size(); row++)
			for (int col = 0; col < players.size(); col++)
				if (row != col) {
					Game instance = newGame(gameClass);  
					table[row][col] = play(instance, players.get(row), players.get(col));
				}
		for (int row = 0; row < players.size(); row++)
			scores[row] = playerScore(row);

		sorted.sort((a, b) -> (int) (scores[b] - scores[a]));
	}

	/**
	 * Игра между игроками в заданых строке и колонке табицы турнира.
	 */
	public Game get(int row, int col) {
		return table[sorted.get(row)][sorted.get(col)];
	}

	/**
	 * Игрок в заданной строке табицы турнира.
	 */
	public IPlayer get(int row) {
		return players.get(sorted.get(row));
	}

	/**
	 * Сколько очков у игрока в заданной строке табицы турнира.
	 */
	public Double score(int row) {
		return scores[sorted.get(row)];
	}

	/**
	 * Сколько очков у игрока в строке с номером [row]
	 */
	private double playerScore(int row) {
		double score = 0.0;

		// Подсчет партий выигранных белыми.
		// В строке таблицы с номером row партии
		// игрока с номером row которые он играл белыми.
		for (int col = 0; col < players.size(); col++)
			switch (getResult(get(row, col))) {
			case WHITE_WIN:
				score++; // Выигрыш белыми.
				break;
			case DRAWN:
				score += 0.5;
			default:
			}
		// Подсчет партий выигранных черными.
		// В КОЛОНКЕ таблицы с номером row партии
		// игрока с номером row которые он играл черными.
		for (int col = 0; col < players.size(); col++)
			switch (getResult(get(col, row))) {
			case BLACK_WIN:
				score++; // Выигрыш черными.
				break;
			case DRAWN:
				score += 0.5;
			default:
			}
		return score;
	}
}
