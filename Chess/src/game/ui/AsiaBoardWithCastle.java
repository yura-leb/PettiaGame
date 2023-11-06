package game.ui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;

/**
 * Азиатская доска в крепостью у каждого из противников.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public abstract class AsiaBoardWithCastle extends AsiaBoard {

	public AsiaBoardWithCastle(Composite parent, Board board) {
		super(parent, board);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		super.drawSquare(gc, v, h, squareWidth, squareHeight);
		
		int vCastle = 4;
		int hCastleBlack = 1;
		int hCastleWhite = board.nH - 2;
		
		boolean isCastleV = (v == vCastle);
		boolean isCastleH = (h == hCastleBlack) || (h == hCastleWhite);
		
		if (isCastleV && isCastleH) {
			int dv = squareWidth  / 2;
			int dh = squareHeight / 2;
			
			int x = v * squareWidth  + dv;
			int y = h * squareHeight + dh;
			
			gc.drawLine(x, y, x - squareWidth, y - squareHeight);
			gc.drawLine(x, y, x + squareWidth, y + squareHeight);
			gc.drawLine(x, y, x - squareWidth, y + squareHeight);
			gc.drawLine(x, y, x + squareWidth, y - squareHeight);
		}
	}
}
