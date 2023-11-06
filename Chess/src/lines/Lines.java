package lines;

import java.util.Random;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.PieceColor;
import lines.pieces.Stone;
import lines.players.Collector;
import lines.players.Dropper;

/**
 * Игрок Dropper случайным образом разбрасывает цветные фишки 
 * на пустые клетки доски.
 * Игрок Collector или человек передвигает фишку на пустую клетку доски
 * выстраивая фишки одного цвета в линию. 
 * 4 фишки выстроенные в одну линию снимаются с доски. 
 *
 */
public class Lines extends Game {
	private static final int BOARD_SIZE = 8;

	private static final IPieceProvider pieceProvider = (square, color) -> new Stone(square, randomColor());

	static {
//		Game.addPlayer(Lines.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Lines.class, new Dropper(pieceProvider, BOARD_SIZE * BOARD_SIZE));
		Game.addPlayer(Lines.class, new Collector());
	}

	public Lines() {
		initBoardDefault();

//		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		board.setWhitePlayer(new Collector());
		board.setBlackPlayer(new Dropper(pieceProvider, BOARD_SIZE * BOARD_SIZE));
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(BOARD_SIZE, BOARD_SIZE);

		new Stone(board.getSquare(BOARD_SIZE / 2, BOARD_SIZE / 2), randomColor());
	}

	static 
	public PieceColor randomColor() {
		Random r = new Random();
		int i = r.nextInt(7);
		
		switch (i) {
		case 0:
			return PieceColor.BLACK;
		case 1:
			return PieceColor.BLUE;
		case 2:
			return PieceColor.GREEN;
		case 3:
			return PieceColor.YELLOW;
		case 4:
			return PieceColor.MAGENTA;
		case 5:
			return PieceColor.RED;
		default:
			return PieceColor.WHITE;
		}
	}
}
