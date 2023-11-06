package chess.pieces;

import chess.moves.Capture;
import chess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Класс представляет шахматную ладью.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Rook extends ChessPiece {
	public Rook(Square square, PieceColor color) {
		super(square, color);
	}

    public Rook() {
    }

    @Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square target = squares[0];
		
		if (square.isEmptyVertical(target))
			return true;
		
		if (square.isEmptyHorizontal(target))
			return true;

		return false;
	}
	
	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[1];
		
		if (!target.isEmpty())
			return new Capture(squares);
		
		return new SimpleMove(squares);
	}
	
	@Override
	public String toString() {
		return "R";
	}
}
