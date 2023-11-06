package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;

/**
 * Управляющая панель для настойки параметров игр
 * и показа текущего состояния игры.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameControlPanel extends Composite {
	private static final Color CONTROL_COLOR = new Color(null, 0, 192, 80);
	
	public GameControlPanel(Composite parent, Game game) {
		super(parent, SWT.NONE);
		setBackground(CONTROL_COLOR);
		setLayout( new GridLayout(1, true) );

		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);

		PlayersPanel players = new PlayersPanel(this, game);
		players.setLayoutData(data);
	}
}
