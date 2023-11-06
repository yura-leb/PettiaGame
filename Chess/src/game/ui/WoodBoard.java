package game.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;

/**
 * Деревянная доска c клетками в рамке.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class WoodBoard extends GameBoard {
	protected static final Color LINE_COLOR = new Color(null, 0,   0, 0);

	public WoodBoard(Composite composite, Board board, Image wood) {
		super(composite, board);
		setBackgroundImage(wood);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		gc.setForeground(LINE_COLOR);
		gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	}

	@Override
	protected void drawBackground(GC gc, Rectangle area) {
	}
}