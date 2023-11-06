package shogi.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import shogi.moves.Capture;
import shogi.moves.SimpleMove;

abstract
public class ShogiPiece extends Piece {
	public boolean isTransformed = false; //Трансформирована ли фигура в данный момент
	public boolean isTransformable = false; //Трансформируема ли фигура
	
	public Square transformSquare;

	public ShogiPiece(Square square, PieceColor color) {
		super(square, color);
	}

    @Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		if (target.isEmpty()) 
			return true;
		
		// Если идем на клетку, занятую фигурой 
		// того же цвета, то ход не корректен.
		return getColor() != target.getPiece().getColor();
	}

	@Override
	public Move makeMove(Square... squares) {
		Square source = squares[0];
		Square target = squares[1];
		
		return target.isEmpty() ? new SimpleMove(this, source, target)
				                : new Capture(squares);
		
	}
}