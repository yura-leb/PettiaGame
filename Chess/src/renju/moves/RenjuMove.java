package renju.moves;

import game.core.Board;
import game.core.Dirs;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.Square;
import renju.pieces.Stone;

public class RenjuMove implements Move {

	/**
	 * Направление на север.
	 */
	static public final Dirs[] nord = { Dirs.UP, Dirs.DOWN };
	
	/**
	 * Направление на северо-запад.
	 */
	static public final Dirs[] nord_west = { Dirs.LEFT_UP, Dirs.RIGHT_DOWN };
	
	/**
	 * Направление на северо-восток.
	 */
	static public final Dirs[] nord_east = { Dirs.RIGHT_UP, Dirs.LEFT_DOWN };
	
	/**
	 * Направление на запад.
	 */
	static public final Dirs[] west = { Dirs.LEFT, Dirs.RIGHT };
	
    /**
     * Все 4-e направления.
     */
	static public final Dirs[][] allDirs = { nord, nord_west, nord_east, west }; 

	private Stone piece;
	private Square target;

	public RenjuMove(Stone stone, Square[] squares) {
		piece = stone;
		target = squares[0];
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
	
	public Square getSquare()
	{
		return target;
	}

	@Override
	public void doMove() throws GameOver {
		target.setPiece(piece);
		
		if (isGameOver())
			throw new GameOver( GameResult.win(piece) );
	}

	@Override
	public void undoMove() {
		target.removePiece();
	}

	@Override
	public String toString() {
		return "" + target;
	}
	
	/**
	 * Проверить завершение игры (5 фишек в ряд).
	 * @return есть ли 5 фишек в ряд.
	 */
	private boolean isGameOver() {
		Board board = piece.square.getBoard();
		int vPiece = piece.square.v;
		int hPiece = piece.square.h;
		
		for (Dirs[] dir : allDirs) {
//			int lineCount = 0;
			int lineCount = 1; // LIKE add
			
			for (Dirs d : dir) { // Две стороны одного направления.
				int v = vPiece;
				int h = hPiece;
						
				while(board.onBoard(v + d.dv, h + d.dh)) {
					v += d.dv;
					h += d.dh;
					
					if (board.isEmpty(v, h))
						break; // Дошли до пустого поля.
					
					Piece p = board.getSquare(v, h).getPiece();
					if (p.isEnemy(piece))
						break; // Дошли до врага.
					
					// Иначе в выбранном направлении стоит наша фигура.
					// Сосчитаем ее.
					lineCount++;
				}
			}
			
			// В этом  направлении (одном из 4-х) стоит достаточно наших фигур.
			// Мы ставим пятую фигуру.
			if (lineCount >= 5)
				return true;
			if (lineCount >=5) {
				//GameResult.win(piece);
				// System.out.println("Game Over" + Gameover);
				//GameOver(GameResult result);
				//GameResult.GameOver();
				GameResult();
			}
		}
		
		return false;
	}

	private void GameResult() {
		// TODO Auto-generated method stub
		
	}

}
