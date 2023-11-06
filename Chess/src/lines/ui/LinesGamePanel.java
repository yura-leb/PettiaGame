package lines.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import lines.Lines;

public class LinesGamePanel extends GamePanel {

	public LinesGamePanel(Composite parent) {
		super(parent, new Lines());

		LinesBoardPanel gameBoard = new LinesBoardPanel(this, game);
		insertSquares(gameBoard);
	}
}
