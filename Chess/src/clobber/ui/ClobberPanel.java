package clobber.ui;

import clobber.Clobber;
import game.ui.GamePanel;
import org.eclipse.swt.widgets.Composite;

public class ClobberPanel extends GamePanel {
    public ClobberPanel(Composite parent) {
        super(parent, new Clobber());

        ClobberBoardPanel gameBoard = new ClobberBoardPanel(this, game);
        insertSquares(gameBoard);
    }
}
