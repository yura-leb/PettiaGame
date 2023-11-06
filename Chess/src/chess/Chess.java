package chess;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import chess.players.*;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;

/**
 * Класс представляющий игру шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Chess extends Game {
	
	static {
		addPlayer(Chess.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(Chess.class, new Neznaika());
		addPlayer(Chess.class, new Poet());
		addPlayer(Chess.class, new Doctor());
		addPlayer(Chess.class, new Painter());
		addPlayer(Chess.class, new Founder());
		addPlayer(Chess.class, new TinTack());

		addPieces();
	}

	static
	protected  void addPieces() {
		allPieces.clear();
		
		addPiece(PieceColor.WHITE, new Pawn());
		addPiece(PieceColor.WHITE, new Rook());
		addPiece(PieceColor.WHITE, new Knight());
		addPiece(PieceColor.WHITE, new Bishop());
		addPiece(PieceColor.WHITE, new Queen());
		addPiece(PieceColor.WHITE, new King());

		addPiece(PieceColor.BLACK, new Pawn());
		addPiece(PieceColor.BLACK, new Rook());
		addPiece(PieceColor.BLACK, new Knight());
		addPiece(PieceColor.BLACK, new Bishop());
		addPiece(PieceColor.BLACK, new Queen());
		addPiece(PieceColor.BLACK, new King());
	}

	/**
	 * Расстановка шахматных фигур в начальную позицию.
	 */
	public Chess() {
		initBoardDefault();
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(8, 8);
		
		// Расставляем пешки.
		for (int v = 0; v < board.nV; v++) {
			new Pawn(board.getSquare(v, 1), PieceColor.BLACK);
			new Pawn(board.getSquare(v, 6), PieceColor.WHITE);
		}

		// Расставляем ладьи.
		new Rook(board.getSquare(0, 0), PieceColor.BLACK);
		new Rook(board.getSquare(7, 0), PieceColor.BLACK);
		new Rook(board.getSquare(0, 7), PieceColor.WHITE);
		new Rook(board.getSquare(7, 7), PieceColor.WHITE);

		// Расставляем коней.
		new Knight(board.getSquare(1, 0), PieceColor.BLACK);
		new Knight(board.getSquare(6, 0), PieceColor.BLACK);
		new Knight(board.getSquare(1, 7), PieceColor.WHITE);
		new Knight(board.getSquare(6, 7), PieceColor.WHITE);

		// Расставляем слонов.
		new Bishop(board.getSquare(2, 0), PieceColor.BLACK);
		new Bishop(board.getSquare(5, 0), PieceColor.BLACK);
		new Bishop(board.getSquare(2, 7), PieceColor.WHITE);
		new Bishop(board.getSquare(5, 7), PieceColor.WHITE);

		// Расставляем ферзей.
		new Queen(board.getSquare(3, 0), PieceColor.BLACK);
		new Queen(board.getSquare(3, 7), PieceColor.WHITE);

		// Расставляем королей.
		new King(board.getSquare(4, 0), PieceColor.BLACK);
		new King(board.getSquare(4, 7), PieceColor.WHITE);
	}
}
