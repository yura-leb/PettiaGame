package mingmang;

import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import mingmang.pieces.Stone;
import mingmang.players.ShenZhaolong;
import mingmang.players.XiaZekai;

/**
 * <a href="http://www.cyningstan.com/game/328/ming-mang">MingMang</a>
 *
 */
public class MingMang extends Game {
	private static final int BOARD_SIZE = 17;

	static {
		Game.addPlayer(MingMang.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(MingMang.class, new Neznaika());
		Game.addPlayer(MingMang.class, new XiaZekai());
		Game.addPlayer(MingMang.class, new ShenZhaolong());
	}

	public MingMang() {
		initBoardDefault();

		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		board.setBlackPlayer(new Neznaika());
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(BOARD_SIZE, BOARD_SIZE);

		for (int k = 0; k < BOARD_SIZE - 1; k++) {
			new Stone(board.getSquare(k, 0), PieceColor.WHITE);
			new Stone(board.getSquare(0, 1 + k), PieceColor.WHITE);
			new Stone(board.getSquare(1 + k, BOARD_SIZE - 1), PieceColor.BLACK);
			new Stone(board.getSquare(BOARD_SIZE-1, k), PieceColor.BLACK);
		}
	}
}
