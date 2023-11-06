package reversi.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import game.ui.ScorePanel;
import reversi.Reversi;

/**
 * 
 * Доска для игры в <a href=
 * "https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">
 * Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiGamePanel extends GamePanel {
	private static final Color GREEN = new Color(null, 0, 192, 0);

	public ReversiGamePanel(Composite composite, int nHoles) {
		super(composite, new Reversi());
		setBackground(GREEN);

		ReversiBoardPanel gameBoard = new ReversiBoardPanel(this, game);
		insertSquares(gameBoard);

		GridData data = new GridData(SWT.FILL, SWT.BOTTOM, false, true);
		data.widthHint = 100;
		
		ScorePanel sp = new ScorePanel(control, game);
		sp.setLayoutData(data);
	}
}
