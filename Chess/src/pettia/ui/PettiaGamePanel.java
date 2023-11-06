package pettia.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import pettia.Pettia;

public class PettiaGamePanel extends GamePanel {

	public PettiaGamePanel(Composite parent) {
		super(parent, new Pettia());

		PettiaBoardPanel gameBoard = new PettiaBoardPanel(this, game);
		insertSquares(gameBoard);
	}
}
