package tools.tourney;

import game.core.Game;
import game.core.GameResult;
import game.players.IPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Абстрактное соревнование между игроками
 */
public abstract class Competition {
	public game.core.Game game;

	public List<IPlayer> players;
	protected List<IPlayer> winners;

	public Competition(List<IPlayer> players) {
		this.players = players;
	}

	public int size() {
		return players.size();
	}

	abstract void run();

	/**
	 * Выдать список победителей
	 *
	 * @return победители
	 */
	List<IPlayer> getWinners() {
		return winners;
	}

	Class<? extends Game> gameClass;

	public Competition(Class<? extends Game> gameClass) {
		this.gameClass = gameClass;

		Game game = newGame(gameClass);
		players = game.getPlayers(gameClass).stream()
				.filter(it -> it != IPlayer.HOMO_SAPIENCE)
				.collect(Collectors.toList());
	}

	public Game newGame(Class<? extends Game> gameClass) {
		try {
			return gameClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	static public GameResult getResult(Game game) {
		return game.board.history.getResult();
	}

	static public Game play(Game game, IPlayer white, IPlayer black) {
		game.initBoardDefault();

		game.board.setWhitePlayer(white);
		game.board.setBlackPlayer(black);
		game.board.startGame();

		return game;
	}

	/**
	 * Сколько очков дается за этот результат партии.
	 */
	static double getScore(GameResult r) {
		switch (r) {
		case WHITE_WIN:
			return 1.0;
		case DRAWN:
			return 0.5;
		default:
			return 0.0;
		}
	}

	static public String getText(GameResult r) {
		switch (r) {
		case WHITE_WIN:
			return "1-0";
		case BLACK_WIN:
			return "0-1";
		case DRAWN:
			return "1/2";
		case UNKNOWN:
			return " * ";
		default:
			return "";
		}
	}
}
