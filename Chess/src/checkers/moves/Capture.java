package checkers.moves;

import java.util.Collections;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.ITrackPiece;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITrackMove;

/**
 * Ход шашкой с взятием одной фигуры противника.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove 
	implements ICaptureMove, ITrackMove 
{
	/**
	 * Захваченая фигура.
	 */
	final Piece captured;
	
	/**
	 * Клетка где стоит захваченная фигура. 
	 */
	private final Square capturedSquare;

	/**
	 * Создание хода представляющего взятие одной фигуры.
	 * 
	 * @param isPromotion - было ли превращение в дамку.
	 * @param captured - захваченная фигура. 
	 * @param squares - откуда и куда пошла фигура.
	 */
	public Capture(boolean isPromotion, Piece captured, Square... squares) {
		super(isPromotion, squares);
		
		this.captured = captured;
		capturedSquare = captured.square;
	}
	
	public Capture(boolean isPromotion, Piece piece, Piece captured,  Square... squares) {
		super(isPromotion, piece, squares);
		
		this.captured = captured;
		capturedSquare = captured.square;
	}

	@Override
	public boolean hasNext() {
		if (!(piece instanceof ITrackPiece))
			return false;
		
		ITrackPiece trackPiece = (ITrackPiece) piece;
		return trackPiece.hasCorrectMoveFrom(target);
	}
	
	@Override
	public void doMove() throws GameOver {
		super.doMove();
		captured.remove();
		
		Board board = source.getBoard();
		PieceColor enemyColor = captured.getColor();
		List<Piece> enemies = board.getPieces(enemyColor);
		
		if (enemies.isEmpty())
			throw new GameOver( GameResult.lost(captured) );
	}

	@Override
	public void undoMove() {
		super.undoMove();
		capturedSquare.setPiece(captured);
	}
	
	@Override
	public String toString() {
		return "" + piece + source + "x" + target;
	}

	@Override
	public List<Square> getCaptured() {
		return Collections.singletonList(capturedSquare);
	}
}
