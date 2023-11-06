package game.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;

/**
 * Зеленая доска c клетками в рамке.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class GreenBoard extends GameBoard {
	private static final Color LINE_COLOR = new Color(null, 0,   0, 0);
	private static final Color FILL_COLOR = new Color(null, 0, 192, 0);

	public GreenBoard(Composite composite, Board board) {
		super(composite, board);
	}

	@Override
	protected void drawBackground(GC gc, Rectangle area) {
		gc.setBackground(FILL_COLOR);
		gc.fillRectangle(area);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		gc.setForeground(LINE_COLOR);
		gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	}
}