package chess.moves;

import java.util.Collections;
import java.util.List;

import chess.pieces.King;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;

/**
 * Ход европейских шахмат - перемещение фигуры на клетку 
 * с захватом фигуры противника.
 *  
 * @author <a href="mailto:vla	dimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove 
	implements ICaptureMove, ICapture 
{
	private Piece capturedPiece;
	private Square capturedSquare;

	/**
	 * Ход - захват фигуры.
	 * squares[0] - откуда идет фигура.
	 * squares[1] - куда идет фигура.
	 * Захваченая фигура стоит на squares[1].
	 * 
	 * @param squares - клетки хода.
	 */
	public Capture(Square[] squares) {
		super(squares);
		
		capturedSquare = squares[1];
		capturedPiece = capturedSquare.getPiece();
	}
	
	@Override
	public List<Square> getCaptured() {
		return Collections.singletonList(capturedSquare);
	}

	/**
	 * Ход - захват фигуры. <br>
	 * squares[0] - откуда идет фигура. <br>
	 * squares[1] - куда идет фигура. <br>
	 * Захваченая фигура - это первый параметр. <br>
	 * Используется при взятии пешки на проходе.
	 * 
	 * @param captured
	 *            - захваченная фигура.
	 * @param squares
	 *            - клетки хода.
	 */
	public Capture(Piece captured, Square[] squares) {
		super(squares);
		
		capturedPiece  = captured;
		capturedSquare = captured.square;
	}

	@Override
	public void doMove() throws GameOver {
		capturedPiece.remove();
		super.doMove();

		if (capturedPiece instanceof King)
			throw new GameOver(GameResult.lost(capturedPiece));
	}


	@Override
	public void undoMove() {
		super.undoMove();
		capturedSquare.setPiece(capturedPiece);
	}

	@Override
	public void removePiece() {
		// TODO Auto-generated method stub
	}

	@Override
	public void restorePiece() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public String toString() {
		return "" + piece + source + "x" + target;
	}
	
	public Piece getCapturedPiece() {
		return capturedPiece;
	}
	public Square getCapturedSquare() {
		return capturedSquare;
	}
	public void setCapturedPiece(Piece capturedPiece) {
		this.capturedPiece = capturedPiece;
	}
	public void setCapturedSquare(Square capturedSquare) {
		this.capturedSquare = capturedSquare;
	}
}
