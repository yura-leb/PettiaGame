package lines.moves;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.Square;
import lines.pieces.Stone;

/**
 * Ход должен случайным образом разбрасывать на пустые клетки 3 цветных фишки.
 * Если все пустые клетки заполнены игрок Dropper выиграл.
 */
public class StoneDrop implements Move {
	private Stone piece;
	private Square target;

	public StoneDrop(Stone stone, Square[] squares) {
		piece = stone;
		target = squares[0];
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public void doMove() throws GameOver {
		target.setPiece(piece);
		
		// TODO Белякова
		// Алгоритм должен случайным образом разбрасывать на пустые клетки 
		// 3 цветных фишки.
		
		if (isGameOver())
			throw new GameOver( GameResult.win(piece) );
	}

	private boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void undoMove() {
		target.removePiece();
	}

	@Override
	public String toString() {
		String color = piece.getColor().toString();
		return "" + color.substring(0,1) + target;
	}
}
