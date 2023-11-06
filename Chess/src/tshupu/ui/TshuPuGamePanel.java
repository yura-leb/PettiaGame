package tshupu.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import tshupu.TshuPu;

public class TshuPuGamePanel extends GamePanel {
    public TshuPuGamePanel(Composite parent) {
        super(parent, new TshuPu());

        TshuPuBoardPanel gameBoard = new TshuPuBoardPanel(this, game);
        insertSquares(gameBoard);
    }
}