package game.ui.fx;

import game.core.Board;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EuropeBoard extends GameBoard {

	public EuropeBoard(Group parent, Board board) {
		super(parent, board);
	}

	@Override
	protected void drawBackground(GraphicsContext gc, Rectangle area) {
		gc.setFill(Color.GREEN);
		gc.fillRect(area.getX(), area.getY(), area.getWidth(), area.getHeight());
	}

	@Override
	public void drawSquare(GraphicsContext gc, int v, int h, int squareWidth, int squareHeight) {
		gc.setStroke(Color.BLACK);
		gc.strokeRect(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
		
		// TODO Зацепин
		// Отрисовать клетки европейской доски
	}
}
