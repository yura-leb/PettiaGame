package renju.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.Dirs;
import game.core.GameOver;
import game.core.GameResult;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.PutPiecePlayer;
import renju.moves.RenjuMove;

/**
 * Карабас ставит фишку в клетку где у фишки будет максимальное число фигур по всем направлениям 
 * и такого же цвета, как и цвет фишки которую ставят.
 * TODO Багров. Реализовать алгоритм игры в рендзю.
 */
public class Karabas extends PutPiecePlayer implements IPlayer {
	private int MAX_MOVES = 20 * 20;
	final Comparator<? super Move> brain = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

	private Board local_board;
	PieceColor local_color;

	@Override
	public String getName() {
		return "Карабас";
	}
	
	@Override
	public String getAuthorName() {
		return "Багров";
	}
	
	public Karabas(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}


	
	private int getMoveWeight(Move m1) {
		
		//Получим координаты хода.
		int hPiece=((RenjuMove)m1).getSquare().h;//горизонталь (x)
		int vPiece=((RenjuMove)m1).getSquare().v;//вертикаль (y)
		Piece piece=((RenjuMove)m1).getPiece();
		int PieceCount_max=0;
		
		
		for (Dirs[] dir : RenjuMove.allDirs) {
			
			for (Dirs d : dir) {// Две стороны одного направления.
				int PieceCount = 0;
				int v = vPiece;
				int h = hPiece;
						
				while(local_board.onBoard(v + d.dv, h + d.dh) && (Math.abs(vPiece-v)<=4) && (Math.abs(hPiece-h)<=4)) {
					v += d.dv;
					h += d.dh;
					
//					if (local_board.isEmpty(v, h))
//						break; // Дошли до пустого поля.
					
					
					Piece p = local_board.getSquare(v, h).getPiece();
					if (p!=null&&p.isFriend(piece))
						PieceCount++; 
				}
				if (PieceCount_max<PieceCount)
					PieceCount_max=PieceCount;
			}
			
		}
		return PieceCount_max;
	}
	

	

	
	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		
		local_board=board;
		local_color=color;
		
		List<Move> correctMoves = getCorrectMoves(board, color);

		if (correctMoves.isEmpty())
			throw new GameOver(GameResult.DRAWN);

		Collections.shuffle(correctMoves);

		// Буратино выбирает лучший ход.
		correctMoves.sort(brain);
		
		Move bestMove = correctMoves.get(0);
		
		try {
			bestMove.doMove();
		} catch (GameOver e) {
			
			
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.addMove(bestMove);
			board.history.setResult(e.result);

			// Просим обозревателей доски показать
			// положение на доске, сделанный ход и
			// результат игры.
			board.setBoardChanged();

			throw new GameOver(GameResult.DRAWN);
		}

		// Сохраняем ход в истории игры.
		board.history.addMove(bestMove);

		// Просим обозревателей доски показать
		// положение на доске, сделанный ход и
		// результат игры.
		board.setBoardChanged();

		// Для отладки ограничим количество ходов в игре.
		// После этого результат игры ничья.
		if (board.history.getMoves().size() > MAX_MOVES) {
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);

			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}
}
