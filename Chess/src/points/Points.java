package points;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Vinni;
import points.pieces.Stone;

/**
 * Игра на окружение фигур противника.
 */
public class Points extends Game {
	private static final int BOARD_SIZE = 20;
	
	private static final IPieceProvider pieceProvider = (square, color) -> new Stone(square, color);

	static {
		Game.addPlayer(Points.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Points.class, new Vinni(pieceProvider, BOARD_SIZE * BOARD_SIZE));
	}

	public Points() {
		initBoardDefault();

		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		board.setBlackPlayer(new Vinni(pieceProvider, BOARD_SIZE * BOARD_SIZE));
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(BOARD_SIZE, BOARD_SIZE);

		new Stone(board.getSquare(BOARD_SIZE/2, BOARD_SIZE/2), PieceColor.BLACK);
	}
}
