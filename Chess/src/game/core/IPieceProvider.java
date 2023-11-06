package game.core;

/**
 * Интерфейс для игр в которых на доску в процессе игры ставятся фигуры.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IPieceProvider {
	/**
	 * Предоставить фигуру заданного цвета стоящую на заданной клетке доски.
	 *  
	 * @param square - клетка где должна встать фигура.
	 * @param color - цвет фигуры.
	 * @return - предоставляемая фигура.
	 */
	Piece getPiece(Square square, PieceColor color);
}
