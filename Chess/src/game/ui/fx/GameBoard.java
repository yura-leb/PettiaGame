package game.ui.fx;

import game.core.Board;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

abstract public class GameBoard extends Canvas {
	public Board board;

	/**
	 * Базовый класс для всех панелей отрисовывающих доски для игр.
	 * 
	 * @param parent - куда встраивается панель изображающая доску.
	 * @param board  - доска с фигурами для игры, которая будет отрисовываться на
	 *               этой панели.
	 */
	public GameBoard(Group parent, Board board) {
//		super(parent, SWT.DOUBLE_BUFFERED);
		parent.getChildren().add(this);

		this.board = board;
	}

	/**
	 * Отрисовка фона для доски.
	 * 
	 * @param gc   - графический контекст для рисования фона доски.
	 * @param area - область для рисования фона доски.
	 */
	abstract protected void drawBackground(GraphicsContext gc, Rectangle area);

	/**
	 * Отрисовка клетки доски.
	 * 
	 * @param gc           - графический контекст в котором рисуется клетка доски.
	 * @param v            - вертикаль клетки.
	 * @param h            - горизонталь клетки.
	 * @param squareWidth  - ширина клетки.
	 * @param squareHeight - высота клетки.
	 */
	abstract public void drawSquare(GraphicsContext gc, int v, int h, int squareWidth, int squareHeight);

	void draw() {
		GraphicsContext gc = getGraphicsContext2D();

		Rectangle clientArea = new Rectangle(getWidth(), getHeight());

		drawBackground(gc, clientArea);

		int squareWidth = getSquareWidth();
		int squareHeight = getSquareHeight();

		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++)
				drawSquare(gc, v, h, squareWidth, squareHeight);
	}
	
	/**
	 * @return высота клетки.
	 */
	protected int getSquareHeight() {
		return (int) (getHeight() / board.nH);
	}

	/**
	 * @return ширина клетки.
	 */
	protected int getSquareWidth() {
		return (int) (getWidth() / board.nH);
	}
}
