package pettia;

import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import pettia.pieces.Stone;
import pettia.players.Archimedes;
import pettia.players.Pythagoras;

public class Pettia extends Game {
	private static final int BOARD_SIZE = 10;

	static {
		Game.addPlayer(Pettia.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Pettia.class, new Neznaika());
		Game.addPlayer(Pettia.class, new Pythagoras());
		Game.addPlayer(Pettia.class, new Archimedes());
	}

	public Pettia() {
		initBoardDefault();

		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		board.setBlackPlayer(new Neznaika());
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(BOARD_SIZE, BOARD_SIZE);

		for (int v = 0; v < BOARD_SIZE; v++) {
			new Stone(board.getSquare(v, 0), PieceColor.BLACK);
			new Stone(board.getSquare(v, 1), PieceColor.BLACK);

			new Stone(board.getSquare(v, BOARD_SIZE-2), PieceColor.WHITE);
			new Stone(board.getSquare(v, BOARD_SIZE-1), PieceColor.WHITE);
		}
	}
}
