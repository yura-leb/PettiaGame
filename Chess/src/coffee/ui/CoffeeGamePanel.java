package coffee.ui;

import org.eclipse.swt.widgets.Composite;

import coffee.Coffee;
import game.ui.GamePanel;

public class CoffeeGamePanel extends GamePanel {

    public CoffeeGamePanel(Composite parent) {
        super(parent, new Coffee());

        CoffeeBoardPanel gameBoard = new CoffeeBoardPanel(this, game);
        insertSquares(gameBoard);
    }
}
