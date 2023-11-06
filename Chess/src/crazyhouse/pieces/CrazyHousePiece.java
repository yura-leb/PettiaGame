package crazyhouse.pieces;

import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

// Базовый класс для всех фигур игры CrazyHouse.
public abstract class CrazyHousePiece extends Piece {
	// находится ли данная фигура в резерве (ящике)
	private boolean isBoxPiece = false;
	
	// является ли текущая фигура, получившейся из пешки
	private boolean isTransformedPiece = false;
	
	public CrazyHousePiece(Square square, PieceColor color) {
		super(square, color);
	}

    public CrazyHousePiece() {
        super();
    }
    
    public void setBoxPiece(boolean flag) {
    	isBoxPiece = flag;
    }
    
    public boolean getBoxPiece() {
    	return isBoxPiece;
    }
    
    public void setTransformedPiece(boolean flag) {
    	isTransformedPiece = flag;
    }
    
    public boolean getTransformedPiece() {
    	return isTransformedPiece;
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
    public void moveTo(Square target) {
		if (square == null) {
			System.out.println("" + target);
		}
		else
			square.piece = null; // говорим: текущая фигурка больше не стоит на клетке
		
		target.setPiece(this);
		square = target;
    }
}
