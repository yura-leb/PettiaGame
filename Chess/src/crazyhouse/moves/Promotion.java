package crazyhouse.moves;

import java.util.Collections;
import java.util.List;

import crazyhouse.pieces.CrazyHousePiece;
import crazyhouse.pieces.Queen;
import game.core.BoardWithBoxes;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ICaptureMove;

/**
 * Ход европейских шахмат - преврашение пешки на последней горизонтали
 * в новую фигуру с возможным взятием фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Promotion extends SimpleMove 
	implements ICaptureMove
{
	private final Piece pawn;
	private Piece capturedPiece;
	private Queen promotedPiece;

	public Promotion(Square[] squares) {
		super(squares);
		
		pawn = source.getPiece();
		
		if (source.v != target.v)  
			// Ход по диагонали со взятием фигуры.
			capturedPiece = target.getPiece();
	}

	@Override
	public List<Square> getCaptured() {
		return capturedPiece == null
				? Collections.emptyList() : Collections.singletonList(target);
	}

	/* 
	 * Удалить пешку, поставить фигуру.
	 */
	@Override
	public void doMove() {
		if (capturedPiece != null) {
//			target.removePiece();
			// отвязываем фигуру от клетки и клетку от фигуры
			capturedPiece.remove(); // target piece

			BoardWithBoxes board = (BoardWithBoxes)source.getBoard();
			
			// Перекрасим захваченную в плен фигуру в свой цвет.
			PieceColor newColor = capturedPiece.isWhite() ? PieceColor.BLACK : PieceColor.WHITE;
			capturedPiece.setColor(newColor);
			
			// Положим захваченную фигуру в свой ящик.
			List<Piece> pieceBox = capturedPiece.isWhite() ? board.bottomBox : board.topBox;
			pieceBox.add(capturedPiece);
			
			// помечаем данную фигуру, как фигуру, стоящую в ящике
			CrazyHousePiece chcp = (CrazyHousePiece)capturedPiece;
			chcp.setBoxPiece(true);
		}
		
//		source.removePiece();
		// отвязываем source фигуру от клетки и клетку от source фигуры
		piece.remove(); // source piece
		
		if (promotedPiece == null) // связываем Queen с target клеткой и target клетку с Queen
			promotedPiece = new Queen(target, pawn.getColor());
		else
			target.setPiece(promotedPiece);
		
		// указываем, что данная королева была получена из пешки
		promotedPiece.setTransformedPiece(true);
	}

	/* 
	 * Удалить фигуру, поставить пешку.
	 */
	@Override
	public void undoMove() {
		// указываем, что данная королева не была получена из пешки
		promotedPiece.setTransformedPiece(false);
		
		// отвязываем фигуру Queen от клетк target
		target.getPiece().remove();
		//target.removePiece();
		
		// связываем фигуру Pawn с клеткой source и клетку source с фигурой Pawn
		source.setPiece(piece);

		if (capturedPiece != null) {
			// Перекрасим захваченную в плен фигуру.
			PieceColor newColor = capturedPiece.isWhite() ? PieceColor.BLACK : PieceColor.WHITE;
			capturedPiece.setColor(newColor);
			
			BoardWithBoxes board = (BoardWithBoxes)source.getBoard();

			// Вернем фигуру из ящика на доску.
			List<Piece> pieceBox = capturedPiece.isWhite() ? board.topBox : board.bottomBox;
			pieceBox.remove(capturedPiece);
			
			// возвращаем фигуру из ящика на доску
			// связываем фигуру из ящика с клеткой target и клетку target с фигурой из ящика
			target.setPiece(capturedPiece);
			
			// помечаем данную фигуру, как фигуру, НЕ стоящую в ящике
			CrazyHousePiece chcp = (CrazyHousePiece)capturedPiece;
			chcp.setBoxPiece(false);
			
			capturedPiece = null;
		}
	}
	
	@Override
	public String toString() {
		String movekind  = (capturedPiece == null) ? "-" : "x";
		String pieceKind = promotedPiece.toString();
		
		return "" + piece + source + movekind + target + pieceKind;
	}
}
