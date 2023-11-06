package renju.players;

import java.util.ArrayList;
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
 * When the opponent has more than three pieces on a line, Nancy will block. 
 * And keep Nancy's pieces on the same line as much as possible
 * 
 */
public class Nancy extends PutPiecePlayer implements IPlayer {
	private int MAX_MOVES = 15 * 15;
	final int MAX_WEIGHT_THAT_CAN_BE=90;
	private int maxWeight;
	
	final Comparator<? super Move> brain = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);
	
	
	private int[] humanPoint;
	private Board local_board;
	
	@Override
	public String getName() {
		return "Nancy(очень умнa)";
	}
	
	@Override
	public String getAuthorName() {
		return "Li Ke";
	}
	
	public Nancy(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	private int getMoveWeight(Move m1) {

		int hPiece=((RenjuMove)m1).getSquare().h;//горизонталь (x)
		int vPiece=((RenjuMove)m1).getSquare().v;//вертикаль (y)
		Piece piece=((RenjuMove)m1).getPiece();
		int metricsMax=0;
		boolean isNeighborFriend;
		
		doHumanPositionAnalysis();
		if(humanPoint != null) {
			if(humanPoint[0] == hPiece && humanPoint[1] == vPiece) {
				return Integer.MAX_VALUE;
			}
		}
		
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

	private void doHumanPositionAnalysis() { 
		
		List<int[]> humanMoves = new ArrayList<>(); //Record human's movement position
		int[][] dirt = {{-1,0},{0,-1},{-1,-1},{0,1},{1,1},{1,0},{-1,1},{1,-1}}; //Eight directions
		
		List<Move> allmoves = local_board.history.getMoves(); //Get all current history records
		
		for(int i = 0; i < allmoves.size(); ) {  //Sava human's movement position(x, y)
			int[] arr = new int[2];
			arr[0] = ((RenjuMove)allmoves.get(i)).getSquare().h;
			arr[1] = ((RenjuMove)allmoves.get(i)).getSquare().v;
			
			humanMoves.add(arr);
			i += 2;
		}
		//Judge three pieces on the same line
		if(humanMoves.size() > 2) {
			int[] arr = humanMoves.get(humanMoves.size() - 1); //Get the currently human's movement position
			for(int i = humanMoves.size() - 2; i >= 0; i--) {
				int[] iarr = humanMoves.get(i);
				for(int d = 0; d < 8; d++) {   //Eight directions
					if(arr[0] == iarr[0] + dirt[d][0] && arr[1] == iarr[1] + dirt[d][1]) {
						for(int j = humanMoves.size() - 2; j >= 0; j--) {
							if(i != j) {
								int[] jarr = humanMoves.get(j);
								
								if(iarr[0] == jarr[0] + dirt[d][0] && iarr[1] == jarr[1] + dirt[d][1]) {	
									iarr[1] += 2 * dirt[d][1];
									iarr[0] += 2 * dirt[d][0];
									if(local_board.getSquare(iarr[1], iarr[0]).getPiece() == null) {
										humanPoint = iarr; //Return to intercept position
									}else if(local_board.getSquare(arr[1] - dirt[d][1], arr[0] - dirt[d][0]).getPiece() == null) {
										arr[1] -=  dirt[d][1];
										arr[0] -=  dirt[d][0];
										humanPoint = arr;  //Return to intercept position
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		
		local_board=board;
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
			
			//Game over prompt LIKE add 2020-10-01
//			int mesg = JOptionPane.showConfirmDialog(null, "Nancy win！！！  "+"\n Do you want to play again ?", "Game Over",JOptionPane.YES_NO_OPTION);
//	        if(mesg == 0) {
//	        	board.reset(15, 15);
//	        	new Stone(board.getSquare(15/2, 15/2), PieceColor.BLACK);
//				board.startGame();
//	        }
			
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
