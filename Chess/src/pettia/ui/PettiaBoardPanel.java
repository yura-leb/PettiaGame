package pettia.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.AsiaBoard;
import game.ui.images.GameImages;
import pettia.pieces.Stone;

public class PettiaBoardPanel extends AsiaBoard implements IPieceProvider {
		public PettiaBoardPanel(Composite composite, Game game) {
			super(composite, game.board);
		}

		@Override
		public Piece getPiece(Square square, PieceColor color) {
			return new Stone(square, color);
		}

		@Override
		public Image getPieceImage(Piece piece, PieceColor color) {
			return color == PieceColor.WHITE 
					? GameImages.wStone 
					: GameImages.bStone;
		}
}
