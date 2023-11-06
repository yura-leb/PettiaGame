package chinachess.ui;

import org.eclipse.swt.widgets.Composite;

import chinachess.ChinaChess;
import game.ui.GamePanel;

/**
 * Панель для игры в китайские шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChinaChessGamePanel extends GamePanel {
	public ChinaChessGamePanel(Composite parent) {
		super(parent, new ChinaChess());
		
		insertSquares( new ChinaChessBoardPanel(this, game) );
	}
}

