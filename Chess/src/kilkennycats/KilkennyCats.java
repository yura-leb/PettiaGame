package kilkennycats;

import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import kilkennycats.players.JiangXueyuan;
import kilkennycats.players.ShenLiwei;
import mingmang.pieces.Stone;

/**
 * <a href="">KilkennyCats</a>
 */
public class KilkennyCats extends Game {
	private static final int BOARD_SIZE = 16;

	static {
		Game.addPlayer(KilkennyCats.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(KilkennyCats.class, new Neznaika());
		Game.addPlayer(KilkennyCats.class, new JiangXueyuan());
		Game.addPlayer(KilkennyCats.class, new ShenLiwei());
	}

	public KilkennyCats() {
		super(new KilkennyCatsBoard());

		initBoardDefault();

//		board.setColorPlayer(PieceColor.RED, new JiangXueyuan());
//		board.setColorPlayer(PieceColor.GREEN, new JiangXueyuan());
//		board.setColorPlayer(PieceColor.BLUE, new ShenLiwei());
//		board.setColorPlayer(PieceColor.MAGENTA, new ShenLiwei());
		
		board.setColorPlayer(PieceColor.RED, IPlayer.HOMO_SAPIENCE);
		board.setColorPlayer(PieceColor.GREEN,  IPlayer.HOMO_SAPIENCE);
		board.setColorPlayer(PieceColor.BLUE, new Neznaika());
		board.setColorPlayer(PieceColor.MAGENTA, new Neznaika());
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(BOARD_SIZE, BOARD_SIZE);
		board.moveColor = PieceColor.RED;

		for (int k = 0; k < 4; k++) {
			new Stone(board.getSquare(6 + k, 3), PieceColor.RED);
			new Stone(board.getSquare(6 + k, 4), PieceColor.RED);

			new Stone(board.getSquare(6 + k, BOARD_SIZE - 4), PieceColor.GREEN);
			new Stone(board.getSquare(6 + k, BOARD_SIZE - 5), PieceColor.GREEN);

			new Stone(board.getSquare(3, 6 + k), PieceColor.BLUE);
			new Stone(board.getSquare(4, 6 + k), PieceColor.BLUE);

			new Stone(board.getSquare(BOARD_SIZE - 4, 6 + k), PieceColor.MAGENTA);
			new Stone(board.getSquare(BOARD_SIZE - 5, 6 + k), PieceColor.MAGENTA);
		}
	}
}
