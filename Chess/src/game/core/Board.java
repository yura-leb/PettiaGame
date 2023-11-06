package game.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import game.players.IPlayer;
import game.players.Neznaika;

/**
 * Доска для расстановки фигур.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
@SuppressWarnings("deprecation")
public class Board extends Observable {
	/**
	 * Количество вертикалей на доске.
	 */
	public int nV;

	/**
	 * Количество горизонталей на доске.
	 */
	public int nH;

	/**
	 * Клетки доски.
	 */
	private Square[][] squares;

	/**
	 * История партии (последовательность ходов игры).
	 */
	public History history = new History(this);

	/**
	 * Цвет фигуры которая должна сделать ход.
	 */
	public PieceColor moveColor = PieceColor.WHITE;

	protected final Map<PieceColor, IPlayer> players = new HashMap<>();
	{
		setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		setBlackPlayer(new Neznaika());
	}

	public Board() {
		reset(0, 0);
	}

	/**
	 * Изменить размеры доски и очистить историю игры.
	 * 
	 * @param nV - количество вертикалей доски.
	 * @param nH - количество горизонталей доски.
	 */
	public void reset(int nV, int nH) {
		this.nV = nV;
		this.nH = nH;

		squares = new Square[nV][nH];
		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++)
				squares[v][h] = new Square(this, v, h);

		history.clear();
		moveColor = PieceColor.WHITE;

		setBoardChanged();
	}

	/**
	 * Уведомить обозревателей доски (классы реализующие интерфейс
	 * <b>Observable</b>)<br>
	 * что на доске произошли изменения.
	 * 
	 * @see java.util.Observable
	 * @see java.util.Observer
	 */
	public void setBoardChanged() {
		// Вызвать protected метод базового класса - Observer.
		super.setChanged();
		super.notifyObservers();
	}

	/**
	 * Смена цвета (игрока который должен сделать ход).
	 */
	public void changeMoveColor() {
		for (;;) {
			moveColor = getNextColor(moveColor);

			IPlayer player = players.get(moveColor);
			if (player == IPlayer.HOMO_SAPIENCE)
				break; // Ход сделает человек мышкой.

			try {
				player.doMove(this, moveColor);
			} catch (GameOver e) {
				break;
			}
		}
	}

	/**
	 * Запуск цикла передачи ходов от одного игрока к другому. Выход из цикла при
	 * завершении игры или при передаче хода игроку - человеку.
	 */
	public void startGame() {
		for (;;) {
			IPlayer player = players.get(moveColor);
			if (player == IPlayer.HOMO_SAPIENCE)
				break; // Ход сделает человек.

			try {
				player.doMove(this, moveColor);
			} catch (GameOver e) {
				break;
			}

			moveColor = getNextColor(moveColor);
		}
	}

	/**
	 * Дать цвет фигуры делающей ход ход посде фигуры заданного цвета.
	 * 
	 * @param color - заданный цвет фигуры.
	 * @return цвет фигур противника.
	 */
	public PieceColor getNextColor(PieceColor color) {
		return color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
	}
	
	/**
	 * Дать цвет противника для фигуры заданного цвета.
	 * 
	 * @param color - заданный цвет фигуры.
	 * @return цвет фигур противника.
	 */
	static public PieceColor getEnemyColor(PieceColor color) {
		return color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
	}

	/**
	 * Выдать цвет фигуры, которая должна сделать ход.
	 * 
	 * @return - цвет фигуры.
	 */
	public PieceColor getMoveColor() {
		return moveColor;
	}

	/**
	 * Вернуть клетку доски.
	 * 
	 * @param v - вертикаль клетки.
	 * @param h - горизонталь клетки.
	 * @return клетка с задаными вертикалью и горизонталью.
	 */
	public Square getSquare(int v, int h) {
		return squares[v][h];
	}

	/**
	 * Проверка выхода координат клетки за границы доски.
	 * 
	 * @param v - вертикаль клетки
	 * @param h - горизонталь клетки
	 * @return есть ли клетка с такими координатами на доске.
	 */
	public boolean onBoard(int v, int h) {
		if (v < 0)
			return false;
		if (h < 0)
			return false;

		if (v > nV - 1)
			return false;
		return h <= nH - 1;
	}

	/**
	 * @return - ширина доски
	 */
	public int getWidth() {
		return nV;
	}

	/**
	 * @return - высота доски
	 */
	public int getHeight() {
		return nH;
	}

	/**
	 * Пуста ли клетка с заданными координатами?
	 * 
	 * @param v - вертикаль
	 * @param h - горизонталь
	 * @return пустая или нет
	 */
	public boolean isEmpty(int v, int h) {
		return getSquare(v, h).isEmpty();
	}

	/**
	 * Задать игрока белыми фигурами.
	 * 
	 * @param player - игрок белыми фигурами.
	 * 
	 * @see game.players.IPlayer
	 */
	public void setWhitePlayer(IPlayer player) {
		setColorPlayer(PieceColor.WHITE, player);
	}

	/**
	 * Задать игрока черными фигурами.
	 * 
	 * @param player - игрок черными фигурами.
	 * 
	 * @see game.players.IPlayer
	 */
	public void setBlackPlayer(IPlayer player) {
		setColorPlayer(PieceColor.BLACK, player);
	}

	/**
	 * Указать какими фигурами будет играть игрок.
	 * 
	 * @param color - какими фигурами будет играть игрок.
	 * @param player - игрок фигурами.
	 * 
	 * @see game.players.IPlayer
	 */
	public void setColorPlayer(PieceColor color, IPlayer player) {
		players.put(color, player);
		setBoardChanged();
	}

	/**
	 * Выдать игрока фигурами заданного цвета.
	 * 
	 * @return Игрок фигурами.
	 */
	public IPlayer getColorPlayer(PieceColor color) {
		return players.get(color);
	}

	/**
	 * Выдать игрока белыми фигурами.
	 * 
	 * @return Игрок белыми фигурами.
	 */
	public IPlayer getWhitePlayer() {
		return players.get(PieceColor.WHITE);
	}

	/**
	 * Выдать игрока черными фигурами.
	 * 
	 * @return игрок черными фигурами.
	 */
	public IPlayer getBlackPlayer() {
		return players.get(PieceColor.BLACK);
	}

	/**
	 * Выдать список всех расположенных на доске фигур заданного цвета.
	 * 
	 * @param color - цвет фигуры.
	 * @return - список фигур.
	 */
	public List<Piece> getPieces(PieceColor color) {
		List<Piece> pieces = new ArrayList<>();

		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++) {
				Square s = getSquare(v, h);

				Piece p = s.getPiece();
				if (p == null)
					continue;

				if (p.getColor() != color)
					continue;

				pieces.add(p);
			}

		return pieces;
	}

	/**
	 * Выдать список всех расположенных на доске фигур заданного цвета.
	 * 
	 * @return - список фигур.
	 */
	public List<Piece> getAllPieces() {
		List<Piece> pieces = new ArrayList<>();

		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++) {
				Square s = getSquare(v, h);

				Piece p = s.getPiece();
				if (p == null)
					continue;

				pieces.add(p);
			}

		return pieces;
	}

	/**
	 * Выдать список всех пустых клеток доски.
	 * 
	 * @return - список всех клеток доски.
	 */
	public List<Square> getEmptySquares() {
		List<game.core.Square> emptySquares = new ArrayList<>();

		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++) {
				game.core.Square square = getSquare(v, h);
				if (square.isEmpty())
					emptySquares.add(square);
			}

		return emptySquares;
	}

	/**
	 * Для заданной фигуры найти список клеток, на которые ход данной фигурой
	 * допустим.
	 * 
	 * @param piece - проверяемая фигура.
	 * @return список допустимых для хода клеток.
	 */
	public List<Square> getPieceTargets(Piece piece) {
		List<Square> targets = new ArrayList<>();

		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++) {
				Square target = getSquare(v, h);

				if (piece.isCorrectMove(target))
					targets.add(target);
			}

		return targets;
	}

	/**
	 * Выдать список всех клеток доски.
	 * 
	 * @return - список
	 */
	public List<Square> getSquares() {
		List<Square> allSquares = new ArrayList<>();

		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++)
				allSquares.add(getSquare(v, h));

		return allSquares;
	}

	/**
	 * Максимальное расстояние между клетками доски.
	 * 
	 * @return максимальное расстояние.
	 */
	public int maxDistance() {
		return nH + nV;
	}

	/**
	 * Выдать угловые клетки.
	 * 
	 * @return угловые клетки.
	 */
	public List<Square> getCorners() {
		return Arrays.asList(getSquare(0, 0), getSquare(nV - 1, 0), getSquare(0, nH - 1), getSquare(nV - 1, nH - 1));
	}
}
