package game.ui.fx;

import game.core.Board;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AsiaBoard extends GameBoard {

	public AsiaBoard(Group parent, Board board) {
		super(parent, board);
	}

	@Override
	protected void drawBackground(GraphicsContext gc, Rectangle area) {
		gc.setFill(Color.YELLOW);
		gc.fillRect(area.getX(), area.getY(), area.getWidth(), area.getHeight());
		
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(5);
		gc.strokeRect(area.getX(), area.getY(), area.getWidth(), area.getHeight());
	}

	@Override
	public void drawSquare(GraphicsContext gc, int v, int h, int squareWidth, int squareHeight) {
		// TODO Зацепин
		// Отрисовать клетки азиатской доски
	}
}

