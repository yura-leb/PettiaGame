package threem.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import threem.ThreeMusketeers;

public class ThreeMusketeersGamePanel extends GamePanel {

	public ThreeMusketeersGamePanel(Composite parent) {
		super(parent, new ThreeMusketeers());
		
		insertSquares( new ThreeMusketeersBoardPanel(this, game) );
	}
}
