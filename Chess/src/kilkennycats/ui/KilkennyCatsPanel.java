package kilkennycats.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import game.core.CubesPanel;
import game.ui.GamePanel;
import game.ui.ScorePanel;
import kilkennycats.KilkennyCats;

public class KilkennyCatsPanel extends GamePanel {
	public KilkennyCatsPanel(Composite parent) {
		super(parent, new  KilkennyCats());

		KilkennyCatsBoardPanel gameBoard = new KilkennyCatsBoardPanel(this, game);
		insertSquares(gameBoard);
		
		GridData data = new GridData(SWT.FILL, SWT.BOTTOM, false, true);
		data.widthHint = 100;

		ScorePanel sp = new ScorePanel(control, game);
		sp.setLayoutData(data);

		CubesPanel cp = new CubesPanel(control, (KilkennyCats) game);
		cp.setLayoutData(data);
	}
}
