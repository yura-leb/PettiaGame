package breakthrough.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import chess.ui.images.ChessImages;
import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;
import game.ui.listeners.MovePieceListener;

public class BreakThroughBoardPanel extends EuropeBoard {

	public BreakThroughBoardPanel(Composite composite, Game game) {
		super(composite, game.board);
		
		listener = new MovePieceListener(this);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return color == PieceColor.WHITE 
				? ChessImages.imagePawnWhite
				: ChessImages.imagePawnBlack;	
	}
}
