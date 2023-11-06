package go.moves;

import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ICaptureMove;
import go.pieces.GoPiece;

/**
 * Ход с захватом фигуры для <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends GoMove implements ICaptureMove {
	/**
	 * Какая клетка ставится.
	 */
    final Piece piece;

	/**
	 * Клетка куда поставлена фигура.
	 */
    final Square target;
	
	/**
	 * Клетки на которых стоят захваченные в плен вражеские фигуры.
	 * Эти фигуры меняют цвет и воюют на нашей стороне.
	 */
    final List<Square> captured;


	/**
	 * Создать ход игры в реверси.
	 * 
	 * @param piece - фигура, которая ставится на доску.
	 * @param target - клетка на которую идет фигура
	 * @param captured - вражеские клетки который будут сняты с доски.
	 */
	public Capture(Piece piece, Square target, List<Square> captured) {
		this.piece = piece;
		this.target = target;
		this.captured = captured;
	}

	@Override
	public void doMove() throws GameOver {
		// Поставим на доску свою фигуру.
		target.setPiece(piece);
		
		// Удалим с доски вражеские фигуры.
		for (Square s : captured)
			s.getPiece().remove();
		
		checkGameEnd(piece);
	}

	@Override
	public void undoMove() {
		PieceColor myColor = piece.getColor();
		PieceColor enemyColor = Board.getEnemyColor(myColor);
		
		// Удалим с доски свою фигуру.
		piece.remove();

		// Восстановим на доске вражеские фигуры.
		for (Square s : captured) {
			new GoPiece(s, enemyColor);
		}
	}

	@Override
	public List<Square> getCaptured() {
		return captured;
	}
	
	@Override
	public String toString() {
		return "" + target + "x";
	}

	@Override
	public Square getTarget() {
		return target;
	}
}
