package mingmang.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.AsiaBoard;
import game.ui.images.GameImages;
import game.ui.listeners.MovePieceListener;
import game.ui.listeners.MovePiecePromptListener;
import mingmang.pieces.Stone;

public class MingMangBoardPanel extends AsiaBoard {
	public MingMangBoardPanel(Composite composite, Game game) {
		super(composite, game.board);

        listener = new MovePieceListener(this);
        
		// Слушатель мыши для отрисовки подсказки на доске -
		// можно ли ставить фигуру на клетку на доски.
		mouseMoveListener = MovePiecePromptListener.EMPTY;
	}

	@Override
	public Piece getPiece(Square square, PieceColor color) {
		return new Stone(square, color);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return color == PieceColor.WHITE ? GameImages.wStone : GameImages.bStone;
	}
}
