package game.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;
import game.ui.images.GameImages;

/**
 * Европейская доска с двухцветными клетками.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public abstract class EuropeBoard extends GameBoard {
	public EuropeBoard(Composite composite, Board board) {
		super(composite, board);
	}

	@Override
	protected void drawBackground(GC gc, Rectangle area) {
		Image image = GameImages.woodMedium;
		Rectangle bounds = image.getBounds();
		
		gc.drawImage(image, 
				0, 0, bounds .width, bounds.height, 
				area.x, area.y, area.width, area.height);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		boolean isWhiteSquare = ((v + h) % 2 == 0);

		Rectangle bounds = GameImages.woodDark.getBounds();
		
		if (!isWhiteSquare)
			gc.drawImage(GameImages.woodDark, 
			             0, 0, bounds.width, bounds.height, 
				         v * squareWidth, h * squareHeight, 
				         squareWidth, squareHeight);

		gc.setForeground(new Color(null, 0, 0, 0));
		gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	}
}
