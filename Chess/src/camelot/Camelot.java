package camelot;

import camelot.pieces.Knight;
import camelot.pieces.Pawn;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;

public class Camelot extends Game {

	static {
		addPlayer(Camelot.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(Camelot.class, new Neznaika());
	}

	public Camelot() {
		initBoardDefault();

		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		board.setBlackPlayer(new Neznaika());
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(12, 16);

		new Pawn(board.getSquare(5, 8), PieceColor.WHITE);
		new Pawn(board.getSquare(6, 7), PieceColor.BLACK);

		new Knight(board.getSquare(5, 7), PieceColor.WHITE);
		new Knight(board.getSquare(6, 8), PieceColor.BLACK);
	}
}
