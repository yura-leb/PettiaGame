package mingmang.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import mingmang.MingMang;

public class MingMangPanel extends GamePanel {

	public MingMangPanel(Composite parent) {
		super(parent, new  MingMang());

		MingMangBoardPanel gameBoard = new MingMangBoardPanel(this, game);
		insertSquares(gameBoard);
	}
}
