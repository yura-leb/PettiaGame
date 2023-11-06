package lines.players;

import game.core.Board;
import game.core.GameOver;
import game.core.IPieceProvider;
import game.core.PieceColor;
import game.players.Vinni;

/**
 * Игрок Dropper бросает случайнам образом несколько разноцветных фишек 
 * на пустые клетки доски.  
 */
public class Dropper extends Vinni {
	public Dropper(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	public Dropper(IPieceProvider pieceProvider, int n) {
		super(pieceProvider);
	}

	@Override
	public String getName() {
		return "Чебурашка";
	}

	@Override
	public String getAuthorName() {
		return "????";
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		super.doMove(board, color);
		
		// Алгоритм должен отбирать из всех допустимых ходов ходы StoneDrop.
	}
}
