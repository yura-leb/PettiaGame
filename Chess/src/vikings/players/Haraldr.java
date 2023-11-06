package vikings.players;

import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;
import vikings.moves.Capture;
import vikings.pieces.Cyning;
import vikings.pieces.VikingsPiece;

/**
 * Haraldr - викинг в Норвегии 1066 год.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Haraldr extends VikingsPlayer {
	final Comparator<? super Move> brain 
		= (m1, m2) -> getWeight(m2) - getWeight(m1);

	@Override
	public String getName() {
		return "Харальд III (Норвегия)";
	}

	@Override
	public String getAuthorName() {
		return "Меркулов";
	}

	protected Comparator<? super Move> getComparator() {
		return brain;
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Задать вес для хода.
	 * 
	 * @param move
	 *            - ход
	 * @return оценка хода.
	 */
	private int getWeight(Move move) {
		ITransferMove transfer = (ITransferMove) move;

		Square source = transfer.getSource();
		Square target = transfer.getTarget();
		
		Piece piece = source.getPiece();
		Board board = source.getBoard();

		Cyning king = getKing(board);
		Square kingSquare = king.square;

		List<Square> exits = VikingsPiece.getExits(board);

		boolean isCaptureMove = (move instanceof ICaptureMove);

		int maxDistance = board.maxDistance();

		// -----------------------------
		// --- Ход черными фигурами. ---
		// -----------------------------
		if (piece.isBlack()) {
			// Ход - захват фигур врага.
			if (isCaptureMove && isKingCapture((Capture) move))  
				// Захват белого короля получает наибольший приоритет.
				return 1000;
			
			// ---------------------------------------------------------
			// --- Если короля захватить нельзя перекроем ему выход. ---
			// ---------------------------------------------------------
			if (isOverlapMove(target, kingSquare))
				return 1000;

			// ---------------------------------
			// --- Ход - захват белых фигур. ---
			// ---------------------------------
			if (isCaptureMove) {
				// Захват вражеского короля получает наибольший приоритет.
				Capture capture = (Capture) move;
				if (isKingCapture(capture))
					return 1000;

				// Приоритет у хода с бОльшим количеством взятых фигур.
				return capture.getCapturedPieces().size();
			}

			// ---------------------------------------------
			// --- Пытаемся приблизится к белому королю. ---
			// ---------------------------------------------
			// Определим расстояние до короля.
			int distance2King = target.distance(kingSquare);
			
			// Чем меньше расстояние до короля, тем лучше ход.
			int moveWeight = maxDistance - distance2King;
			
			// Поиск клетки - ближайшего выхода для короля.
			Square nearstExit = getNearstExit(kingSquare, exits);
			
			// Если фигура встанет между королем и его ближайшим выходом,
			// то ход этой фигурой еще лучше.
			if (nearstExit.distance(target) < nearstExit.distance(kingSquare))
				moveWeight++;
			
			return moveWeight;
		}
		// ----------------------------
		// --- Ход белыми фигурами. ---
		// ----------------------------
		else {
			// --------------------------
			// --- Ход белого короля. ---
			// --------------------------
			if (piece instanceof Cyning) {
				// Поиск ближайшего выхода.
				Square nearsExit = getNearstExit(target, exits);
	
				// Ход королем к ближайшему выходу 
				// получает наибольший приоритет.
				int minDistance = nearsExit.distance(target);
	
				if (minDistance == 0)
					return 1000; // Выход короля - наибольший приоритет.

				// Если нет захвата фигур врага, король движется к выходу.
				if (!isCaptureMove)
					return maxDistance - minDistance;
			}
	
			// ----------------------------------------
			// --- Ход белой фигуры - захват врага. ---
			// ----------------------------------------
			if (isCaptureMove) {
				// Ход - захват фигур врага.
				Capture capture = (Capture) move;
	
				// Приоритет у хода с бОльшим количеством 
				// захваченных фигур врага.
				return 10 + capture.getCapturedPieces().size();
			}
	
			// ------------------------------------------------------
			// --- Простой ход белого викинга - поддержка короля. ---
			// ------------------------------------------------------
			if (isSafeMove(piece, target))   return 2;
			if (isAttackMove(piece, target)) return 1;

			return -1; // Мы теряем фигуру и не нападаем на вражескую.
		}
	}

	/**
	 * Это атакующий ход.
	 * 
	 * @param piece
	 *            - какая фигура идет.
	 * @param target
	 *            - куда фигура идет.
	 * @return
	 */
	private boolean isAttackMove(Piece piece, Square target) {
		// TODO Заблоцкий
		// 1. Наша фигура становится рядом с фигурой противника.
		// 2. Есть другая наша фигура, которая следующим ходом 
		//    может встать с другой стороны вражеской фигуры.
		return false;
	}

	/**
	 * Не приведет ли ход фигурой на поле target к потере фигур.
	 * 
	 * @param piece
	 *            - какая фигура идет.
	 * @param target
	 *            - куда фигура идет.
	 * @return
	 */
	private boolean isSafeMove(Piece piece, Square target) {
		// TODO Меркулов.
		// Мы не подставляем свою фигуру.
		// 1. Фигура становится рядом с фигурой противника.
		// 2. Нет вражеской фигуры, которая следующим ходом 
		//    может встать с другой стороны нашей фигуры.
		return true;
	}
}