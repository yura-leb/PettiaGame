package halma.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.AsiaBoard;
import game.ui.BoardSizePanel;
import game.ui.GamePanel;
import game.ui.ScorePanel;
import game.ui.listeners.MovePieceListener;
import halma.Halma;
import halma.ui.images.HalmaImages;

/**
 * Панель для игры в уголки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class HalmaGamePanel extends GamePanel {

	public HalmaGamePanel(Composite parent, int boardSize) {
		super(parent, new Halma(boardSize));
		
		insertSquares( new HalmaBoardPanel(this, game) );
		
		// 
		// Возможен выбор размера доски.
		//
		GridData data = new GridData(SWT.FILL, SWT.TOP, false, true);
		data.widthHint = 100;
		
		int[][] sizes = { {8,8}, {10,10}, {16,16} };
		BoardSizePanel bsp = new BoardSizePanel(control, this, sizes);
		bsp.setLayoutData(data);
		
		data = new GridData(SWT.FILL, SWT.BOTTOM, false, true);
		data.widthHint = 100;

		ScorePanel sp = new ScorePanel(control, game);
		sp.setLayoutData(data);
	}
}

/**
 * Доска для игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a> 
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class HalmaBoardPanel extends AsiaBoard {
	public HalmaBoardPanel(Composite parent, Game game) {
		super(parent, game.board);
		
		listener = new MovePieceListener(this);
		
		setPromptColor( new Color(null, 0, 100, 0) );
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return color == PieceColor.WHITE 
				? HalmaImages.imageStoneWhite
				: HalmaImages.imageStoneBlack;	
	}
}
