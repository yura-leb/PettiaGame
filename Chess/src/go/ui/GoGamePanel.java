package go.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.AsiaBoard;
import game.ui.GamePanel;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import go.Go;
import go.pieces.GoPiece;
import go.ui.images.GoImages;

/**
 * Панель для игры в Го.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class  GoGamePanel extends GamePanel {

	public GoGamePanel(Composite parent, int boardSize) {
		super(parent, new Go(boardSize));
		
		insertSquares( new GoBoardPanel(this, game) );
	}
}
/**
 * Панель для игры <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class GoBoardPanel extends AsiaBoard implements IPieceProvider {
	private static final Color DARK_GREEN = new Color(null, 0, 100, 0);

	public GoBoardPanel(Composite parent, Game game) {
		super(parent, game.board);
		
		listener = new PutPieceListener(this);
//		mouseMoveListener = new NoPromptListeneromptListener(this);
		
		// Слушатель мыши выдающий подсказки для клеток - 
		// можно ли ставить фигуру на клетку доски.
		mouseMoveListener = new PutPiecePromptListener(this);
		
		setPromptColor(DARK_GREEN);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return color == PieceColor.WHITE 
				? GoImages.imageStoneWhite
				: GoImages.imageStoneBlack;
	}
	
	@Override
	public Piece getPiece(Square square, PieceColor color) {
		return new GoPiece(square, color);
	}
}
