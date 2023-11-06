package backgammon.moves;

import backgammon.BackgammonBoard;
import backgammon.pieces.BackgammonGroup;
import backgammon.pieces.Stone;
import game.core.GameOver;
import game.core.Piece;
import game.core.Square;

/**
 * Ход с захватом одинокой вражеской фигуры в плен.
 */
public class Capture extends SimpleMove {
	private BackgammonGroup piece;
	private BackgammonGroup enemy;
	
	public Capture(Square source, Square target) {
		super(source, target);
		
		piece = (BackgammonGroup) source.getPiece();
		enemy = (BackgammonGroup) target.getPiece();
	}
	

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public void doMove() {
		// TODO Мизаушев. Реализовать алгоритм захвата и возвращения фигур
		BackgammonGroup enemyGroup;
		BackgammonBoard board = (BackgammonBoard) source.getBoard();
		Square square4Enemy = board.getBar4Piece(enemy);
		
		Stone enemyStone = enemy.pushStone();
		enemy.remove();
		if (square4Enemy.isEmpty())
			enemyGroup = new BackgammonGroup(square4Enemy, enemyStone);
		else {
			enemyGroup = (BackgammonGroup) square4Enemy.getPiece();
			enemyGroup.add(enemyStone);
		}
		square4Enemy.setPiece(enemyGroup);
		try {
			super.doMove();
		} catch (GameOver e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void undoMove() {
		// TODO Вернуть свою фигуру на место.
		super.undoMove();
		
		// TODO Вернуть захваченную фигуру из клетки для пленных (bar).
	}

	@Override
	public String toString() {
		return "" + source + "x" + target;
	}
}