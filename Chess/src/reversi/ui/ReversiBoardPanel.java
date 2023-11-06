package reversi.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GreenBoard;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import reversi.pieces.Stone;
import reversi.ui.images.ReversiImages;

/**
 * Доска для игры в <a href=
 * "https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">
 * Реверси</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiBoardPanel extends GreenBoard implements IPieceProvider {
	/**
	 * Создать доску для игры в реверси.
	 * 
	 * @param composite
	 *            - составной элемент содержащий доску.
	 * @param game 
	 * @param nHoles
	 *            - количество случайно расположенных отверстий в доске.
	 */
	public ReversiBoardPanel(Composite composite, Game game) {
		super(composite, game.board);

		// Слушатель мыши для постановки новой фигуры на доску.
		listener = new PutPieceListener(this);

		// Слушатель мыши для отрисовки подсказки на доске - 
		// можно ли ставить фигуру на клетку на доски.
		mouseMoveListener = new PutPiecePromptListener(this);
	}

	@Override
	public Piece getPiece(Square square, PieceColor color) {
		return new Stone(square, color);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return color == PieceColor.WHITE 
				? ReversiImages.imageStoneWhite 
				: ReversiImages.imageStoneBlack;
	}
}

