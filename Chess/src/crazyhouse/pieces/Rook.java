package crazyhouse.pieces;

import crazyhouse.moves.BoxMove;
import crazyhouse.moves.Capture;
import crazyhouse.moves.SimpleMove;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

public class Rook extends CrazyHousePiece {
	public Rook(Square square, PieceColor color) {
		super(square, color);
	}

    public Rook() {
    }

    @Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		// если производится ход фигурой, которая находится в резерве
		if (getBoxPiece()) // если игрок пытается поставить фигуру на свободную клетку
			return target.isEmpty(); // => возвращаем true, иначе false
		
		// иначе: используем обычную логику проверки хода для ладьи
		
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		if (square.isEmptyVertical(target))
			return true;
		
		if (square.isEmptyHorizontal(target))
			return true;

		return false;
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
		return "R";
	}
}
