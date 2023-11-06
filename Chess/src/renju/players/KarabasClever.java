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
public class KarabasClever extends PutPiecePlayer implements IPlayer {
	private int MAX_MOVES = 20 * 20;
	final int MAX_WEIGHT_THAT_CAN_BE=90;
	private int maxWeight;
	final Comparator<? super Move> brain = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);
	
	private Board local_board;
	 PieceColor local_color;

	@Override
	public String getName() {
		return "Карабас Умный";
	}
	
	@Override
	public String getAuthorName() {
		return "Багров";
	}
	
	public KarabasClever(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}


	
	private int getMoveWeight(Move m1) {

		int hPiece=((RenjuMove)m1).getSquare().h;//горизонталь (x)
		int vPiece=((RenjuMove)m1).getSquare().v;//вертикаль (y)
		Piece piece=((RenjuMove)m1).getPiece();
		int metricsMax=0;
		boolean isNeighborFriend;
		
		for (Dirs[] dir : RenjuMove.allDirs) {
			
			for (Dirs d : dir) {// Две стороны одного направления.
				int metrics = 0;
				int v = vPiece;
				int h = hPiece;
				isNeighborFriend=false;		
				while(local_board.onBoard(v + d.dv, h + d.dh) && (Math.abs(vPiece-v)<=4) && (Math.abs(hPiece-h)<=4)) {
					v += d.dv;
					h += d.dh;
					
					Piece p = local_board.getSquare(v, h).getPiece();
					if (p!=null&&p.isFriend(piece))
					{
						if (isNeighborFriend || ((Math.abs(vPiece-v)<=1) && (Math.abs(hPiece-h)<=1)) && (!((Math.abs(vPiece-v)==0)&&(Math.abs(hPiece-h)==0))))
							isNeighborFriend=true;
						metrics=metrics+10+measureDistance(h,hPiece,vPiece,v); 
					}
				}
				
				if (metricsMax<metrics&&isNeighborFriend)
					metricsMax=metrics;
			}
		}
		if (metricsMax>maxWeight)
			maxWeight=metricsMax;
		return metricsMax;
	}

	private int measureDistance(int x1,int y1,int x2,int y2)
	{
		return Math.max(Math.abs(x1-x2), Math.abs(y1-y2));
	}

	boolean checkWillEnemyWin(PieceColor color)
	{
		
		List<Move> correctMoves = getCorrectMoves(local_board, (color==PieceColor.BLACK)?PieceColor.WHITE:PieceColor.BLACK);
		int moveWeight=0;
		int moveWeightMax=0;
		for(int i=0;i<correctMoves.size();i++)
		{
			moveWeight=getMoveWeight(correctMoves.get(i));
			if (moveWeight>moveWeightMax)
			{
				moveWeightMax=moveWeight;
				correctMoves.get(i);
			}	
	    }
		
		if(moveWeightMax>=MAX_WEIGHT_THAT_CAN_BE)
			return true;
		else
			return false;
			

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
		Move bestMove;
		//if (maxWeight<MAX_WEIGHT_THAT_CAN_BE&&checkWillEnemyWin(color))
		//	bestMove=saveMove;
		//else
			bestMove= correctMoves.get(0);

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
