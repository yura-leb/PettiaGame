package crazyhouse.moves;

/**
 * Интерфейс для ходов и обязательным или возможным захватом фигуры противника.
 * 
 * @author <a href="mailto:artmakar01@mail.ru">Makarov A.V.</a>
 */
public interface ICapture {
	/**
	 * Действительно ли происходит захват фигуры противника?
	 * @return
	 */
	default boolean isCapture() {
		return true;
	}
	
	/**
	 * Удалить с доски фигуру противника.
	 */
	void removePiece();
	
	/**
	 * Восттановить на доске фигуру противника.
	 */
	void restorePiece();
}