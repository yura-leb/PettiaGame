package chinachess;

import chinachess.pieces.Bishop;
import chinachess.pieces.Guardian;
import chinachess.pieces.Gun;
import chinachess.pieces.King;
import chinachess.pieces.Knight;
import chinachess.pieces.Pawn;
import chinachess.pieces.Rook;
import chinachess.players.Confucious;
import chinachess.players.Laozi;
import chinachess.players.QinShiHuang;
import chinachess.players.SunTzu;
import chinachess.players.ZhangDaoling;
import chinachess.players.ZhangLu;
import game.core.Board;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;

/**
 * Игра <a href=
 * "https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 *
 */
public class ChinaChess extends Game {
	
	static {
		addPlayer(ChinaChess.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(ChinaChess.class, new Neznaika());
		addPlayer(ChinaChess.class, new SunTzu());
		addPlayer(ChinaChess.class, new Confucious());
		addPlayer(ChinaChess.class, new QinShiHuang());
		addPlayer(ChinaChess.class, new Laozi());
		addPlayer(ChinaChess.class, new ZhangLu());
		addPlayer(ChinaChess.class, new ZhangDaoling());
	}

	public ChinaChess() {
		initBoardDefault();

		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setWhitePlayer( new Neznaika() );
		board.setBlackPlayer( new QinShiHuang() );
	}

	private static void putPieces(Board board, PieceColor color) {
		int hPiece = (color == PieceColor.BLACK ? 0 : board.nH - 1);
		int hGun   = (color == PieceColor.BLACK ? 2 : board.nH - 3);
		int hPawn  = (color == PieceColor.BLACK ? 3 : board.nH - 4);
		
		// Расставляем пешки через одну, на все четные линии
		for (int v = 0; v < board.nV; v += 2) 
			new Pawn(board.getSquare(v, hPawn), color);
		
		// Guns on positions
		new Gun(board.getSquare(1, hGun), color);
		new Gun(board.getSquare(7, hGun), color);

		// Kings in the castle
		new King(board.getSquare(4, hPiece), color);

		// Guardians with it's king
		new Guardian(board.getSquare(3, hPiece), color);
		new Guardian(board.getSquare(5, hPiece), color);
		
		// Bishops in positions
		new Bishop(board.getSquare(2, hPiece), color);
		new Bishop(board.getSquare(6, hPiece), color);

		// Knights on positions
		new Knight(board.getSquare(7, hPiece), color);
		new Knight(board.getSquare(1, hPiece), color);
		
		// Rooks in positions
		new Rook(board.getSquare(0,  hPiece), color);
		new Rook(board.getSquare(8, hPiece), color);
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(9, 10);
		
		putPieces(board, PieceColor.BLACK);
		putPieces(board, PieceColor.WHITE);
	}
}
