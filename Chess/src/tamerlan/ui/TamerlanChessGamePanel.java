package tamerlan.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import tamerlan.TamerlanChess;

/**
 * Панель для игры в шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TamerlanChessGamePanel extends GamePanel {

	public TamerlanChessGamePanel(Composite parent) {
		super(parent, new TamerlanChess());
		
		insertSquares( new TamerlanChessBoardPanel(this, game) );
	}
}

