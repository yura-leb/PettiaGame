package checkers.moves;

import java.util.HashMap;
import java.util.Map;

import checkers.pieces.King;
import game.core.GameOver;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ITransferMove;

/**
 * Простой ход шашкой вперед без взятия фигуры противника.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class SimpleMove implements ITransferMove {
	/**
	 * Откуда пошла фигура.
	 */
	protected final Square source;

	private final Map<Piece, Piece> kings = new HashMap<>();

	/**
	 * Куда пошла фигура.
	 */
	protected final Square target;

	/**
	 * Бало ли превращение шащки в дамку?
	 */
	protected final boolean isPromotion;

	/**
	 * Какая фигура пошла.
	 */
	protected Piece piece;

	public SimpleMove(boolean isPromotion, Square... squares) {
		this.isPromotion = isPromotion;

		source = squares[0];
		target = squares[1];

		piece = source.getPiece();
	}

	public SimpleMove(boolean isPromotion, Piece piece, Square... squares) {
		this.isPromotion = isPromotion;

		source = squares[0];
		target = squares[1];

		this.piece = piece;
	}

	@Override
	public Square getTarget() {
		return target;
	}

	@Override
	public Square getSource() {
		return source;
	}

	@Override
	public void doMove() throws GameOver {
		piece.moveTo(target);

		if (isPromotion)
			putKing(target);
	}

	@Override
	public void undoMove() {
		if (isPromotion)
			removeKing(target);

		piece.moveTo(source);
	}

	/**
	 * Заменить на поле s простую шашку на дамку.
	 * 
	 * @param s
	 */
	private void putKing(Square s) {
		if (isPromotion)
			piece = s.getPiece();
		
		PieceColor kingColor = piece.getColor();
		Piece exMan = piece;
		s.removePiece();
		
		King kingFromMan = new King(s, kingColor);
		kings.put(kingFromMan, exMan);
		piece = kingFromMan;
	}

	/**
	 * Заменить на поле s на дамку простую шашку.
	 * 
	 * @param s
	 */
	private void removeKing(Square s) {
		s.removePiece();

		piece = kings.get(piece);
		s.setPiece(piece);
	}

	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
}