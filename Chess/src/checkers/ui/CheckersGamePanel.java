package checkers.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import checkers.Checkers;
import checkers.moves.Capture;
import checkers.pieces.King;
import checkers.pieces.Man;
import checkers.ui.images.CheckersImages;
import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;
import game.ui.GamePanel;
import game.ui.listeners.TrackPieceListener;

/**
 * Панель для игры в шашки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CheckersGamePanel extends GamePanel {

	public CheckersGamePanel(Composite parent) {
		super(parent, new Checkers());
		
		insertSquares( new CheckersBoardPanel(this, game) );
	}
}
/**
 * Доска для игры в шашки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class CheckersBoardPanel extends EuropeBoard {

	private static final Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

	static {
		Map<Class<? extends Piece>, Image> whites = new HashMap<>();
		Map<Class<? extends Piece>, Image> blacks = new HashMap<>();

		pieceImages = new HashMap<>();
		pieceImages.put(PieceColor.WHITE, whites);
		pieceImages.put(PieceColor.BLACK, blacks);

		// Инициализируем карту изображений белых фигур.
		//
		whites.put(Man.class,  CheckersImages.imageManWhite);
		whites.put(King.class, CheckersImages.imageKingWhite);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks.put(Man.class,  CheckersImages.imageManBlack);
		blacks.put(King.class, CheckersImages.imageKingBlack);
	}

	public CheckersBoardPanel(Composite composite, Game game) {
		super(composite, game.board);
		
//		listener = new MovePieceListener(this);
		listener = new TrackPieceListener<Capture>(this);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return pieceImages
				.get(color)
				.get( piece.getClass() );
	}
}
