package go;

import game.core.Game;
import game.core.IPieceProvider;
import game.players.IPlayer;
import game.players.Vinni;
import go.pieces.GoPiece;

/**
 * Игра <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Go extends Game {
	private static final IPieceProvider pieceProvider = (square, color) -> new GoPiece(square, color);
	
	static {
		Game.addPlayer(Go.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Go.class, new Vinni(pieceProvider) );
	}
	
	public Go(int boardSize) {
		super.initBoard(boardSize, boardSize);

		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Vinni(pieceProvider) );
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(10, 10);	
	}
}
