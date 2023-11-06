package kilkennycats.ui;

import static kilkennycats.KilkennyCatsBoard.isWall;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.EuropeBoard;
import game.ui.images.GameImages;
import game.ui.listeners.MovePieceListener;
import kilkennycats.pieces.Stone;

public class KilkennyCatsBoardPanel extends EuropeBoard {
	public KilkennyCatsBoardPanel(Composite composite, Game game) {
		super(composite, game.board);

		listener = new MovePieceListener(this);
	}

	@Override
	public Piece getPiece(Square square, PieceColor color) {
		return new Stone(square, color);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		switch (color) {
		case BLUE:
			return GameImages.stoneBlue;
		case GREEN:
			return GameImages.stoneGreen;
		case RED:
			return GameImages.stoneRed;
		case MAGENTA:
			return GameImages.stoneMagenta;
		default:
			return GameImages.stoneBlack;
		}
	}

	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		if (isWall(board, v, h)) {
			Rectangle bounds = GameImages.marbl.getBounds();

			gc.drawImage(GameImages.marbl, 0, 0, bounds.width, bounds.height, v * squareWidth, h * squareHeight,
					squareWidth, squareHeight);
		} else
			super.drawSquare(gc, v, h, squareWidth, squareHeight);
	}
}
