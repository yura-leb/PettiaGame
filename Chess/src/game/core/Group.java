package game.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Группа фигур расположенных на одной клетке.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract 
public class Group<T> extends Piece {
	private List<T> pieces = new ArrayList<>();

	public Group(Square square, PieceColor color) {
		super(square, color);
	}
	
	public T topPiece() {
		return pieces.get(pieces.size()-1);
	}

	public T pushStone() {
		return pieces.remove(pieces.size()-1);
	}

	public boolean isEmpty() {
		return pieces.isEmpty();
	}

	public void add(T stone) {
		pieces.add(stone);
	}

	public int size() {
		return pieces.size();
	}

	public T getPiece(int k) {
		return pieces.get(k);
	}

	public boolean contains(T piece) {
		return pieces.contains(piece);
	}
}