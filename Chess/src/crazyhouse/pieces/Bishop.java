package crazyhouse.pieces;

import crazyhouse.moves.BoxMove;
import crazyhouse.moves.Capture;
import crazyhouse.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

public class Bishop extends CrazyHousePiece {
	public Bishop(Square square, PieceColor color) {
		super(square, color);
	}

    public Bishop() {

    }

    @Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		// если производится ход фигурой, которая находится в резерве
		if (getBoxPiece()) // если игрок пытается поставить фигуру на свободную клетку
			return target.isEmpty(); // => возвращаем true, иначе false
		
		// иначе: используем обычную логику проверки хода для слона
		
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
    	
		return square.isEmptyDiagonal(target);
	}
	
	@Override
	public Move makeMove(Square... squares) {
		// если производится ход фигурой из резерва
		if (getBoxPiece())
			return new BoxMove(squares[0], this);
		
		Square target = squares[1];
		
		if (!target.isEmpty())
			return new Capture(squares);
		
		return new SimpleMove(squares);
	}
	
	@Override
	public String toString() {
		return "B";
	}
}