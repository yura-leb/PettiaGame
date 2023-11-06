package vikings.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.GreenBoard;
import game.ui.listeners.MovePieceListener;
import vikings.pieces.Cyning;
import vikings.pieces.Viking;
import vikings.ui.images.VikingImages;

/**
 * Доска для игры 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A5%D0%BD%D0%B5%D1%84%D0%B0%D1%82%D0%B0%D1%84%D0%BB">Викинги (Хнефатафл, Тавлеи) </a>.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class VikingsBoardPanel extends GreenBoard {
	private static final Color COLOR = new Color(null, 0,   255, 0);

	public VikingsBoardPanel(Composite parent, Game game) {
		super(parent, game.board);
		
		
		listener = new MovePieceListener(this);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		if (piece instanceof Viking)
			return color == PieceColor.WHITE 
				? VikingImages.imageVikingWhite
				: VikingImages.imageVikingBlack;
		
		if (piece instanceof Cyning)
			return color == PieceColor.WHITE 
				? VikingImages.imageCyningWhite
				: VikingImages.imageCyningBlack;
		
		return null;
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int sw, int sh) {
		super.drawSquare(gc, v, h, sw, sh);
	
		int h1 = 0;           
		int v1 = 0;
		int hc = board.nH/2; 
		int vc = board.nV/2;
		int h2 = board.nH-1;
		int v2 = board.nV-1;
		
		gc.setBackground(COLOR);
		drawMark(gc, v1, h1, sw, sh);
		drawMark(gc, v1, h2, sw, sh);
		drawMark(gc, v2, h1, sw, sh);
		drawMark(gc, v2, h2, sw, sh);
		
		drawMark(gc, vc, hc, sw, sh);
	}

	private void drawMark(GC gc, int v, int h, int sw, int sh) {
		gc.fillGradientRectangle(1+v*sw, 1+h*sh, sw-1, sh-1, true);
	}
}