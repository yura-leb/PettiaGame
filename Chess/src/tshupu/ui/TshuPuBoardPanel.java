package tshupu.ui;

import static java.lang.Math.abs;

import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.WoodBoard;
import game.ui.images.GameImages;
import tshupu.pieces.TshuPuPiece;

public class TshuPuBoardPanel extends WoodBoard {
    private Color textColor = new Color(null, 255, 0, 0);
    
	public TshuPuBoardPanel(Composite composite, Game game) {
		super(composite, game.board, GameImages.woodMedium);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		switch (color) {
		case BLACK:
			return GameImages.stoneBlack;
		case WHITE:
			return GameImages.stoneWhite;
		case GREEN:
			return GameImages.stoneGreen;
		case BLUE:
			return GameImages.stoneBlue;
		default:
			return GameImages.stoneRed;
		}
	}
	
	protected void drawPiece(GC gc, int v, int h, int squareWidth, int squareHeight) {
		super.drawPiece(gc, v, h, squareWidth, squareHeight);
		
		// TODO draw piece count too.
		TshuPuPiece piece = (TshuPuPiece) board.getSquare(v, h).getPiece();
		if (piece == null) return;
		
		int dx = squareWidth  /8;
		int dy = squareHeight /8;
		
		int x = v * squareWidth  + 4 * dx;
		int y = h * squareHeight + 3 * dy;	
		gc.setForeground(textColor);
		gc.drawString("" + piece.count, x, y, true);

	}

	@Override
	public void drawSquare(GC gc, int v, int h, int sw, int sh) {
		Square theSquare = board.getSquare(v, h);

		if (isCorner(theSquare))
			return;

		if (isCenter(theSquare))
			return;

		gc.setForeground(LINE_COLOR);
		gc.drawRectangle(v * sw, h * sh, sw, sh);

		gc.setBackground(LINE_COLOR);
	}

	private boolean isCorner(Square theSquare) {
		List<Square> corners = board.getCorners();

		return corners.stream().anyMatch(s -> isAngle(s, theSquare));
	}

	private boolean isCenter(Square theSquare) {
		if (theSquare.v < 3)
			return false;
		if (theSquare.h < 3)
			return false;

		if (theSquare.v > 5)
			return false;
		if (theSquare.h > 5)
			return false;

		return true;
	}

	boolean isAngle(Square s1, Square s2) {
		return abs(s1.v - s2.v) < 3 && abs(s1.h - s2.h) < 3;
	}
}
