package shogi;

import game.core.Board;
import game.core.BoardWithBoxes;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import shogi.pieces.Bishop;
import shogi.pieces.GeneralGold;
import shogi.pieces.GeneralSilver;
import shogi.pieces.King;
import shogi.pieces.Knight;
import shogi.pieces.Lance;
import shogi.pieces.Pawn;
import shogi.pieces.Rook;

public class Shogi extends Game {
	
	static {
		addPlayer(Shogi.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(Shogi.class, new Neznaika());
	}

	public Shogi() {
		super(new BoardWithBoxes());
		initBoardDefault();

		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	private static void putPieces(Board board, PieceColor color) {
		int hPiece      = (color == PieceColor.BLACK ? 0 : board.nH - 1);
		int hRookBishop = (color == PieceColor.BLACK ? 1 : board.nH - 2);
		int hPawn       = (color == PieceColor.BLACK ? 2 : board.nH - 3);
		
		// Расставляем пешки через одну, на все четные линии
		for (int v = 0; v < board.nV; v++) 
			new Pawn(board.getSquare(v, hPawn), color);
		
		new Lance(board.getSquare(0, hPiece), color);
		new Knight(board.getSquare(1, hPiece), color);
		
		new GeneralSilver(board.getSquare(2, hPiece), color);
		new GeneralGold(board.getSquare(3, hPiece), color);
		
		new King(board.getSquare(4, hPiece), color);
		
		new GeneralGold(board.getSquare(5, hPiece), color);
		new GeneralSilver(board.getSquare(6, hPiece), color);
		
		new Knight(board.getSquare(7, hPiece), color);
		new Lance(board.getSquare(8, hPiece), color);
		
		new Bishop(board.getSquare(1, hRookBishop), color);
		new Rook(board.getSquare(7, hRookBishop), color);
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(9, 9);
		
		putPieces(board, PieceColor.BLACK);
		putPieces(board, PieceColor.WHITE);
	}
}