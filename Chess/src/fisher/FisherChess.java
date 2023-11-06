package fisher;

import chess.pieces.Bishop;
import fisher.piece.King; // Crashes game.ui.AdornedBoard with NullPointerException if replaced with "import chess.pieces.King;" works fine
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import fisher.players.Magnus;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import java.util.Random;

public class FisherChess extends Game {
	static {
		addPlayer(FisherChess.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(FisherChess.class, new Neznaika());
		addPlayer(FisherChess.class, new Magnus());

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
	public FisherChess() {
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
		int []used = {0, 0, 0, 0, 0, 0, 0, 0};
		Random rng = new Random();
		int blackBishopPosition = rng.nextInt(4);
		new Bishop(board.getSquare(blackBishopPosition * 2, 0), PieceColor.BLACK);
		new Bishop(board.getSquare(blackBishopPosition * 2, 7), PieceColor.WHITE);
		used[blackBishopPosition * 2] = 1;
		int whiteBishopPosition = rng.nextInt(4);
		new Bishop(board.getSquare(whiteBishopPosition * 2 + 1, 0), PieceColor.BLACK);
		new Bishop(board.getSquare(whiteBishopPosition * 2 + 1, 7), PieceColor.WHITE);
		used[whiteBishopPosition * 2 + 1] = 1;
		int firstKnightPosition = rng.nextInt(6);
		int counter = 0;
		for (int i = 0; i < 8; i++) {
			if (used[i] == 0) {
				if (firstKnightPosition == counter) {
					new Knight(board.getSquare(i, 0), PieceColor.BLACK);
					new Knight(board.getSquare(i, 7), PieceColor.WHITE);
					used[i] = 1;
				}
				counter++;
			}
		}
		int secondKnightPosition = rng.nextInt(5);
		counter = 0;
		for (int i = 0; i < 8; i++) {
			if (used[i] == 0) {
				if (secondKnightPosition == counter) {
					new Knight(board.getSquare(i, 0), PieceColor.BLACK);
					new Knight(board.getSquare(i, 7), PieceColor.WHITE);
					used[i] = 1;
				}
				counter++;
			}
		}
		int queenPosition = rng.nextInt(4);
		counter = 0;
		for (int i = 0; i < 8; i++) {
			if (used[i] == 0) {
				if (queenPosition == counter) {
					new Queen(board.getSquare(i, 0), PieceColor.BLACK);
					new Queen(board.getSquare(i, 7), PieceColor.WHITE);
					used[i] = 1;
				}
				counter++;
			}
		}
		counter = 0;
		for (int i = 0; i < 8; i++) {
			if (used[i] == 0) {
				switch (counter){
					case 0:
					case 2:
						new Rook(board.getSquare(i, 0), PieceColor.BLACK);
						new Rook(board.getSquare(i, 7), PieceColor.WHITE);
						counter++;
						break;
					case 1:
						new King(board.getSquare(i, 0), PieceColor.BLACK);
						new King(board.getSquare(i, 7), PieceColor.WHITE);
						counter++;
						break;
				}
			}
		}
	}
}
