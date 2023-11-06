package camelot.ui;

import org.eclipse.swt.widgets.Composite;

import camelot.Camelot;
import game.ui.GamePanel;

public class CamelotPanel extends GamePanel {
	public CamelotPanel(Composite composite) {
		super(composite, new Camelot());

		insertSquares(new CamelotBoardPanel(this, game));
	}
}

