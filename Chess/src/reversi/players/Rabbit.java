package reversi.players;

import game.core.IPieceProvider;
import game.core.Move;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.IPutMove;

import java.util.Comparator;

/**
 * Кролик - самый умный.
 * Знает что фигуры в углах доски окружить невозможно.
 * Знает что фигуры на краях окружить сложнее чем в центре доски.
 * Выбирает ход с захватом максимального количества фигур врага.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Rabbit extends ReversiPlayer {
	final Comparator<? super Move> brain
		= (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

	@Override
	public String getName() {
		return "Кролик";
	}

	@Override
	public String getAuthorName() {
		return "Степан Медведенко";
	}

	@Override
	public Comparator<? super Move> getComparator() {
		return brain;
	}

	/**
	 * Кролик - игрок в реверси. Знает что фигуры в углах доски окружить
	 * невозможно. Знает что фигуры на краях окружить сложнее чем в центре
	 * доски. Выбирает ход с захватом максимального количества фигур врага.
	 */
	public Rabbit(IPieceProvider pieceProvider) {
		super(pieceProvider);
		this.pieceProvider = pieceProvider;
	}
	
	private int getMoveWeight(Move move) {
		IPutMove putMove = (IPutMove) move;
		
		Square target = putMove.getTarget();
		
		if (isCorner(target))
			return 1000; // Встали в угол.

		if (isBorder(target))
			return 900; // Встали на край доски.
		
		if (move instanceof ICaptureMove) {
			// Ход - взятие фигур врага.
			ICaptureMove capture = (ICaptureMove) move;
			
			// Цена хода - сколько взяли фигур.
			return capture.getCaptured().size();
		}
		
		return 0; 
	}
}