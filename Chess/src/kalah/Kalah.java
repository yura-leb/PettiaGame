package kalah;

import game.core.Game;
import game.core.PieceColor;
import game.core.Square;
import game.players.IPlayer;
import game.players.Neznaika;
import kalah.pieces.Heap;

/**
 * Правила игры здесь:
 * http://kalah.ru/rules
 */
public class Kalah extends Game {
	static {
		Game.addPlayer(Kalah.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Kalah.class, new Neznaika(100));
	}

	public Kalah() {
		super(new KalahBoard());
		
		initBoardDefault();
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(8, 2);
		
		KalahBoard kalahBoard = (KalahBoard)board;
		kalahBoard.initWays();

		for (int h = 0; h < 2; h++)
			for (int v = 1; v < 7; v++)
				new Heap(kalahBoard.getSquare(v, h), 6);
		
		new Heap(kalahBoard.getWhiteKalah(), 0);
		new Heap(kalahBoard.getBlackKalah(), 0);
	}
	
	/**
	 * Выдать счет для фигур заданного цвета.
	 *
	 * @param color - цвет фигур.
	 * @return счет для заданного цвета фигур.
	 */
	public int getScore(PieceColor color) {
		KalahBoard kalahBoard = (KalahBoard) board;
		
		Square kalah = color == PieceColor.WHITE 
				? kalahBoard.getWhiteKalah()
				: kalahBoard.getBlackKalah();
		
		Heap heap = (Heap) kalah.getPiece();		
		
		return heap.nBalls;
	}

}