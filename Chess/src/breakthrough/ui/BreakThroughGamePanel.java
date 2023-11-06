package breakthrough.ui;

import org.eclipse.swt.widgets.Composite;

import breakthrough.BreakThrough;
import game.ui.GamePanel;

/**
 * Панель для игры в Прорыв.
 * 
 */
public class BreakThroughGamePanel extends GamePanel {
	public BreakThroughGamePanel(Composite parent) {
		super(parent, new BreakThrough());
		
		insertSquares( new BreakThroughBoardPanel(this, game) );
	}
}
