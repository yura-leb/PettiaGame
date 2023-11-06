package backgammon.pieces;

import game.core.Group;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Группа фигур в нардах, которые стоят на одной клетке. 
 */
public class BackgammonGroup extends Group<Stone> {
	/**
	 * В нардах в группе могут быть фигуры только одного цвета.
	 */
	PieceColor color;
	
	public BackgammonGroup(Square square, Stone stone) {
		super(square, stone.getColor());
		
		add(stone);
		color = stone.getColor();
	}

	public BackgammonGroup(Square square, PieceColor color, int pieceCount) {
		super(square, color);

		for (int k = 0; k < pieceCount; k++)
			add( new Stone(square, color) );
		
		square.setPiece(this);
		this.color = color;
	}
	
	@Override
	public void add(Stone stone) {
		super.add(stone);
		
		stone.group = this;
		stone.square = stone.group.square;
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		if (isEmpty())
			return false;
		
		Stone topPiece = topPiece();
		return topPiece.isCorrectMove(squares);
	}

	@Override
	public Move makeMove(Square... squares) {
		return topPiece().makeMove(squares);
	}
	
	@Override
	public String toString() {
		return "G(" + square + ")#" + size();
	}
}