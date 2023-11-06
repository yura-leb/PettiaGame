package breakthrough;

import breakthrough.players.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import breakthrough.pieces.Pawn;
import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.players.IPlayer;
import game.players.Neznaika;
import game.ui.GreenBoard;
import game.ui.listeners.MovePieceListener;
import reversi.pieces.Stone;
import reversi.ui.images.ReversiImages;

/**
 * Правила:
 * https://www.chessprogramming.org/Breakthrough_(Game)
 */
public class BreakThrough extends Game {
	static {
		addPlayer(BreakThrough.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(BreakThrough.class, new Neznaika());
		addPlayer(BreakThrough.class, new Aesculapius());
		addPlayer(BreakThrough.class, new Artemis());
		addPlayer(BreakThrough.class, new Athene());
		addPlayer(BreakThrough.class, new Hephaistos());
		addPlayer(BreakThrough.class, new Poseidon());
		addPlayer(BreakThrough.class, new Hermes());
	}

	/**
	 * Расстановка фигур пешек в начальную позицию.
	 */
	public BreakThrough() {
		initBoardDefault();

		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		board.setBlackPlayer(new Neznaika());
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(5, 5);
		
		// Расставляем пешки.
		for (int v = 0; v < board.nV; v++) {
			new Pawn(board.getSquare(v, 0), PieceColor.BLACK);
			new Pawn(board.getSquare(v, 1), PieceColor.BLACK);
			new Pawn(board.getSquare(v, 3), PieceColor.WHITE);
			new Pawn(board.getSquare(v, 4), PieceColor.WHITE);
		}
	}
}

class BreakThroughBoardPanel extends GreenBoard {
	public BreakThroughBoardPanel(Composite composite, Game game) {
		super(composite, game.board);

		listener = new MovePieceListener(this);
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
