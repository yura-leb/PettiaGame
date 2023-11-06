package fisher.ui;

import org.eclipse.swt.widgets.Composite;

import fisher.FisherChess;
import game.ui.GamePanel;

public class FisherGamePanel extends GamePanel {
	public FisherGamePanel(Composite parent) {
		super(parent, new FisherChess());
		
		insertSquares( new FisherBoardPanel(this, game) );
	}
}
