package threem.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;
import game.ui.images.GameImages;
import game.ui.listeners.MovePieceListener;

public class ThreeMusketeersBoardPanel extends  EuropeBoard  {
	public ThreeMusketeersBoardPanel(Composite composite, Game game) {
		super(composite, game.board);
		
		listener = new MovePieceListener(this);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return color == PieceColor.WHITE 
				? GameImages.stoneWhite
				: GameImages.stoneBlack;	
	}
}
