package points.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.AsiaBoard;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import points.pieces.Stone;
import reversi.ui.images.ReversiImages;

public class PointsBoardPanel extends AsiaBoard implements IPieceProvider  {
	public PointsBoardPanel(Composite composite, Game game) {
		super(composite, game.board);

		// Слушатель мыши для постановки новой фигуры на доску.
		listener = new PutPieceListener(this);

		// Слушатель мыши для отрисовки подсказки на доске - 
		// можно ли ставить фигуру на клетку на доски.
		mouseMoveListener = PutPiecePromptListener.EMPTY;
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
