package crazyhouse;

import crazyhouse.pieces.Bishop;
import crazyhouse.pieces.King;
import crazyhouse.pieces.Knight;
import crazyhouse.pieces.Pawn;
import crazyhouse.pieces.Queen;
import crazyhouse.pieces.Rook;
import game.core.BoardWithBoxes;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;

/**
 * TODO Макаров, Идрисов - <a href="https://ru.wikipedia.org/wiki/Crazyhouse">CrazyHouse</a>
 *  с одним игроком каждого цвета.
 */
public class CrazyHouse extends Game {
	static {
		addPlayer(CrazyHouse.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(CrazyHouse.class, new Neznaika());

		addPieces();
	}
	
	/**
	 * Расстановка шахматных фигур в начальную позицию.
	 */
	public CrazyHouse() {
		// используем доску, содержащую два ящика для фигур
		super(new BoardWithBoxes());
		initBoardDefault();
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	static protected  void addPieces() {
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

		// Расставляем королей.
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


