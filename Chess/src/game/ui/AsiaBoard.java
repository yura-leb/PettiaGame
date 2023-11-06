package game.ui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;
import game.ui.images.GameImages;

/**
 * Доска для азиатских игр. 
 * В центре клетки пересекаются линии. Клетки не закрашиваются.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public abstract class AsiaBoard extends GameBoard {
	public AsiaBoard(Composite parent, Board board) {
		super(parent, board);
	}

	@Override
	protected void drawBackground(GC gc, Rectangle area) {
		Rectangle bounds = GameImages.woodLight.getBounds();
		
		gc.drawImage(GameImages.woodLight, 
				0, 0, bounds .width, bounds.height, 
				area.x, area.y, area.width, area.height);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		int dv = squareWidth/2;
		int dh = squareHeight/2;
		
		int x = v * squareWidth  + dv;
		int y = h * squareHeight + dh;

		if (v !=          0) gc.drawLine(x, y, x - dv, y);
		if (v != board.nV-1) gc.drawLine(x, y, x + dv, y);

		if (h !=          0) gc.drawLine(x, y, x, y - dh);
		if (h != board.nH-1) gc.drawLine(x, y, x, y + dh);
	}
}
