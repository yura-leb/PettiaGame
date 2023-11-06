package renju.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import renju.Renju;

public class RenjuGamePanel extends GamePanel {

	public RenjuGamePanel(Composite parent) {
		super(parent, new Renju());

		RenjuBoardPanel gameBoard = new RenjuBoardPanel(this, game);
		insertSquares(gameBoard);
	}
}
