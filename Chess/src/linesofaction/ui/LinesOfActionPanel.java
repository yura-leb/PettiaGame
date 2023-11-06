package linesofaction.ui;

import game.ui.GamePanel;
import linesofaction.LinesOfAction;
import org.eclipse.swt.widgets.Composite;

/**
 * Правила игры "Линии в действии"
 */
public class LinesOfActionPanel extends GamePanel {
    public LinesOfActionPanel(Composite parent) {
        super(parent, new LinesOfAction());

        LinesOfActionBoardPanel gameBoard = new LinesOfActionBoardPanel(this, game);
        insertSquares(gameBoard);
    }
}
