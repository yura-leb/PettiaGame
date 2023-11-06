package crazyhouse.moves;

import java.util.Collections;
import java.util.List;

import crazyhouse.pieces.CrazyHousePiece;
import crazyhouse.pieces.Pawn;
import game.core.BoardWithBoxes;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ICaptureMove;

public class Capture extends SimpleMove implements ICaptureMove, ICapture {
	private Piece capturedPiece;
	private Square capturedSquare;
	private Pawn capturedPawn;
	
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
	
	// [TODO]: ДОДЕЛАТЬ ЭТИ ДВА МЕТОДА: doMove() и undoMove()
	@Override
	public void doMove() {
		// отвязываем фигуру capturedPiece от клетки target
		// и клетку target от фигуры capturedPiece
		capturedPiece.remove();
		
		// связываем фигуру source piece с клеткой target
		// и клетку target с фигурой source piece
		super.doMove();
		
		// дополнительная логика для CrazyHouse: добавляем захваченную фигуру в ящик
		
		CrazyHousePiece chcp = (CrazyHousePiece)capturedPiece;
		BoardWithBoxes board = (BoardWithBoxes)capturedSquare.getBoard();
		
		PieceColor newColor = capturedPiece.isWhite() ? PieceColor.BLACK : PieceColor.WHITE;
		List<Piece> pieceBox = capturedPiece.isWhite() ? board.topBox : board.bottomBox;
		
		// если данная фигура была получена из пешки
		if (chcp.getTransformedPiece()) {
			// => по правилам CrazyHouse в ящик необходимо положить пешку, а не саму фигуру
			
			// создаём объект пешки
			capturedPawn = new Pawn();
			
			// перекрасим захваченную в плен фигуру в свой цвет
			capturedPawn.setColor(newColor);
			
			// добавляем пешку в ящик
			pieceBox.add(capturedPawn);
			
			// помечаем данную фигуру, как фигуру, стоящую в ящике		
			capturedPawn.setBoxPiece(true);
		} else {
			// перекрасим захваченную в плен фигуру в свой цвет
			chcp.setColor(newColor);
			
			// положим захваченную фигуру в свой ящик
			pieceBox.add(capturedPiece);
			
			// помечаем данную фигуру, как фигуру, стоящую в ящике		
			chcp.setBoxPiece(true);
		}
	}
	
	@Override
	public void undoMove() {
		// если захваченная фигура была получена из пешки
		if (capturedPawn != null) {
			// перекрасим пешку в противоположный цвет
			PieceColor newColor = capturedPawn.isWhite() ? PieceColor.BLACK : PieceColor.WHITE;
			capturedPawn.setColor(newColor);
			
			BoardWithBoxes board = (BoardWithBoxes)capturedSquare.getBoard();

			// вместо пешки, вернем на доску фигуру, в которую данная пешка была преобразована
			List<Piece> pieceBox = capturedPawn.isWhite() ? board.topBox : board.bottomBox;
			
			// удаляем пешку из ящика
			pieceBox.remove(capturedPawn);
			
			// на этом этапе: перекрасили пешку обратно в ее цвет и удалили из ящика
			
			// теперь на её место нужно вернуть CapturedPiece (а не саму пешку)
			
			// связываем фигуру source piece с клеткой source
			// и клетку source с фигурой source piece
			super.undoMove();
			
			// связываем фигуру capturedPiece с клеткой target
			// и клетку target с фигурой captured piece
			capturedSquare.setPiece(capturedPiece);
			
			// помечаем, что пешка больше не находится в ящике (резерве)
			CrazyHousePiece chcp = (CrazyHousePiece)capturedPawn;
			chcp.setBoxPiece(false);
			
			capturedPawn = null;
			
			return;
		}
		
		// иначе: если захваченная фигура не была получена из пешки
		
		// перекрасим захваченную в плен фигуру обратно в её исходный цвет
		PieceColor newColor = capturedPiece.isWhite() ? PieceColor.BLACK : PieceColor.WHITE;
		capturedPiece.setColor(newColor);
		
		BoardWithBoxes board = (BoardWithBoxes)capturedSquare.getBoard();

		// вернем фигуру из ящика на доску
		List<Piece> pieceBox = capturedPiece.isWhite() ? board.topBox : board.bottomBox;
		pieceBox.remove(capturedPiece);
		
		// связываем фигуру source piece с клеткой source
		// и клетку source с фигурой source piece
		super.undoMove();
		capturedSquare.setPiece(capturedPiece);
		
		// помечаем, что данная фигура больше не находится в ящике (резерве)
		CrazyHousePiece chcp = (CrazyHousePiece)capturedPiece;
		chcp.setBoxPiece(false);
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
