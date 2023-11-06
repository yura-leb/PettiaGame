package viruswars.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GamePanel;
import game.ui.GreenBoard;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import viruswars.Viruswars;
import viruswars.pieces.Virus;
import viruswars.ui.images.ViruswarsImages;

/**
 * 
 */
public class ViruswarsPanel extends GamePanel {
	private static final Color GREEN = new Color(null, 0, 192, 0);

	public ViruswarsPanel(Composite composite) {
		super(composite, new Viruswars());
		setBackground(GREEN);

		ViruswarsBoardPanel gameBoard = new ViruswarsBoardPanel(this, game);
		insertSquares(gameBoard);
	}

	public static class ViruswarsBoardPanel extends GreenBoard implements IPieceProvider {
		/**
		 * Создать доску для игры в реверси.
		 * 
		 * @param composite составной элемент содержащий доску.
		 * @param game
		 */
		public ViruswarsBoardPanel(Composite composite, Game game) {
			super(composite, game.board);

			// Слушатель мыши для постановки новой фигуры на доску.
			listener = new PutPieceListener(this);

			// Слушатель мыши для отрисовки подсказки на доске -
			// можно ли ставить фигуру на клетку на доски.
			mouseMoveListener = new PutPiecePromptListener(this);
		}

		@Override
		public Piece getPiece(Square square, PieceColor color) {
			return new Virus(square, color);
		}

		@Override
		public Image getPieceImage(Piece piece, PieceColor color) {
			Virus virus = (Virus) piece;
			
			switch (color) {
			case WHITE:
				if (virus.isZombi)
					return ViruswarsImages.imageZombiBlue;
				else
					return ViruswarsImages.imageVirusBlue;
			default:
				if (virus.isZombi)
					return ViruswarsImages.imageZombiRed;
				else
					return ViruswarsImages.imageVirusRed;
			}
		}
	}
}
