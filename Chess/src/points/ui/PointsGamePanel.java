package points.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import points.Points;

public class PointsGamePanel extends GamePanel {

	public PointsGamePanel(Composite parent) {
		super(parent, new Points());

		PointsBoardPanel gameBoard = new PointsBoardPanel(this, game);
		insertSquares(gameBoard);
	}

}
