package game.ui.fx;

import game.core.Board;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GamesNotebookFX extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Board board = new Board();
		board.reset(8, 8);

		Group root = new Group();

		GameBoard eBoard = new EuropeBoard(root, board);
		eBoard.setWidth(400);
		eBoard.setHeight(400);
		eBoard.draw();

		GameBoard aBoard = new AsiaBoard(root, board);
		aBoard.setLayoutX(400);
		aBoard.setWidth(400);
		aBoard.setHeight(400);
		aBoard.draw();

		primaryStage.setTitle("GamesNotebook with JavaFX");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
