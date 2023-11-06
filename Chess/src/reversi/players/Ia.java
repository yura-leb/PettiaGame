package reversi.players;

import game.core.IPieceProvider;
import game.core.Move;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.IPutMove;

import java.util.Comparator;

/**
 * Ослик Иа - игрок в реверси:<br>
 */
public class Ia extends ReversiPlayer {
	final Comparator<? super Move> brain
			= (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

	@Override
	public String getName() {
		return "Иа";
	}

	@Override
	public String getAuthorName() {
		return "???";
	}

	/**
	 * Иа - игрок в реверси.
	 */
	public Ia(IPieceProvider pieceProvider) {
		super(pieceProvider);
		this.pieceProvider = pieceProvider;
	}
	
	@Override
	public Comparator<? super Move> getComparator() {
		return brain;
	}

	/**
	 * Определить стоимость хода.
	 * 
	 * @param move
	 *            - ход который оцениваем.
	 * @return стоимость хода.
	 */
	private int getMoveWeight(Move move) {
		IPutMove putMove = (IPutMove) move;
		
		Square target = putMove.getTarget();
		
		if (isCorner(target))
			return 1000; // Встали в угол.
		
		if (isBorder(target))
			return 900; // Встали на край доски.
		
		if (isBigCross(target))
			return 800;
		
		if (isSquareX(target))
			return -900;
		
		if (isSquareC(target))
			return -800;
		
		if (move instanceof ICaptureMove) {
			// Ход - взятие фигур врага.
			ICaptureMove capture = (ICaptureMove) move;
			
			// Правило реверси - брать меньше фигур!
			// В результате - больший выбор ходов потом.
			// Фигуры противника заберем в конце игры.
			return 64 - capture.getCaptured().size();
		}
		
		return 0; 
	}
	
	/**
	 * Если вы сыграете на это поле, ваш противник легко займет угол.
	 * 
	 * @param target
	 *            - проверяемая клетка.
	 * @return
	 */
	public boolean isSquareX(Square target) {
		// http://othello.gomel.by/stepanov/x-pole/
		return false;
	}

	/**
	 * Вторым наиболее плохим полем является C-поле. Оно само уже находится на
	 * стороне и для того чтобы через него попасть в угол, надо постараться
	 * немного больше.
	 * 
	 * @param target
	 *            - проверяемая клетка.
	 * @return
	 */
	public boolean isSquareC(Square target) {
		// TODO Лобанова Ангелина
		// http://othello.gomel.by/stepanov/c-pole/
		return false;
	}

	/**
	 * Эта клетка на большом кресте? Ход на крестдаёт возможность делать
	 * минимальные ходы и в будущем, а не только сейчас. Ваши фишки будут все
	 * время внутри соперника, а его снаружи, что очень приветствуется в Отелло.
	 * 
	 * @param target
	 *            - проверяемая клетка.
	 * @return
	 */
	public boolean isBigCross(Square target) {
		// http://othello.gomel.by/stepanov/bolwoy-krest/
		return false;
	}
}