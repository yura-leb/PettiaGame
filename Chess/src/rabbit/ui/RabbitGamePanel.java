package rabbit.ui;

import org.eclipse.swt.widgets.Composite;

import game.ui.GamePanel;
import rabbit.RabbitWolfs;

public class RabbitGamePanel extends GamePanel {

    public RabbitGamePanel(Composite parent) {
        super(parent, new RabbitWolfs());

        RabbitBoardPanel gameBoard = new RabbitBoardPanel(this, game);
        insertSquares(gameBoard);
    }
}
