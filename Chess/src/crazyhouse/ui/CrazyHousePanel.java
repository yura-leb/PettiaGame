package crazyhouse.ui;

import org.eclipse.swt.widgets.Composite;

import crazyhouse.CrazyHouse;
import game.ui.GamePanel;

public class CrazyHousePanel extends GamePanel {
	public CrazyHousePanel(Composite parent) {
		super(parent, new CrazyHouse());

		insertSquares(new CrazyHouseBoardPanel(this, game), true);
		
		// показываем на экране содержимое ящиком с фигурами
		adorned.updatePieceBoxes();
	}
}
