package crazyhouse.moves;

import java.util.List;

import crazyhouse.pieces.CrazyHousePiece;
import game.core.BoardWithBoxes;
import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

public class BoxMove implements Move {
	/**
	 * Какая фигура перемещается.
	 */
	protected final Piece piece;
	
	/**
	 * На какую клетку ставим фигуру из ящика.
	 */
	protected final Square target;
	
	public BoxMove(Square s, Piece p) {
		target = s;
		piece = p;
	}
	
	@Override
	public String toString() {
		return "" + piece + "@" + "-" + target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public void doMove() throws GameOver {
		// удаляем фигуру из ящика
		BoardWithBoxes board = (BoardWithBoxes)target.getBoard();
		List<Piece> pieceBox = piece.isWhite() ? board.bottomBox : board.topBox;
		pieceBox.remove(piece);
		
		// ставим фигуру на target клетку
		piece.moveTo(target);
		
		// помечаем данную фигуру, как фигуру, НЕ стоящую в ящике
		CrazyHousePiece chcp = (CrazyHousePiece)piece;
		chcp.setBoxPiece(false);
	}

	@Override
	public void undoMove() {
		// удаляем фигуру с клетки 'target'
		piece.remove();
		
		// добавляем фигуру в ящик
		BoardWithBoxes board = (BoardWithBoxes)target.getBoard();
		List<Piece> pieceBox = piece.isWhite() ? board.bottomBox : board.topBox;
		pieceBox.add(piece);
		
		// помечаем данную фигуру, как фигуру, стоящую в ящике
		CrazyHousePiece chcp = (CrazyHousePiece)piece;
		chcp.setBoxPiece(true);
	}
}
