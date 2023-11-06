package kilkennycats;

import game.core.Board;
import game.core.BoardWithCubes;
import game.core.PieceColor;

public class KilkennyCatsBoard extends BoardWithCubes {
	public KilkennyCatsBoard() {
		moveColor = PieceColor.RED;
	}
	
	/**
	 * Дать цвет фигуры делающей ход ход посде фигуры заданного цвета.
	 * 
	 * @param color - заданный цвет фигуры.
	 * @return цвет фигур противника.
	 */
	public PieceColor getNextColor(PieceColor color) {
		switch (color) {
		case RED:
			return PieceColor.MAGENTA;
		case MAGENTA:
			return PieceColor.GREEN;
		case GREEN:
			return PieceColor.BLUE;
		default:
			return PieceColor.WHITE;
		}
	}
	
	
	static public boolean isWall(Board board, int v, int h) {
		int nH = board.nH / 2 - 1;
		int nV = board.nV / 2 - 1;

		if ((v == 0) || (v == board.nV - 1))
			return h < nH || h >= nH + 2;

		if ((h == 0) || (h == board.nH - 1))
			return v < nV || v >= nV + 2;

		return false;
	}
}
