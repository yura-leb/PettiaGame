package points.moves;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import points.pieces.Stone;

public class PointMove implements Move {
	
	//добавлен код сюда.
	static int[][]masc=new int[20][20];
	private void initMasc() {
	PieceColor color=	piece.getColor();
		for(int i=0; i<20; i++)
		{
			for (int j=0;j<20;j++) {	
				if (target.getBoard().isEmpty(i, j)||target.getBoard().getSquare(i, j).getPiece().getColor()!=color)//если клетка пустая или нетого цвета
				{
					masc[i][j]=0;
					continue;
				}
				masc[i][j]=1;
			}
		}
	} 
	
	void getcount() // печать в консоль маски
	{
		
		for (int i=0; i<20; i++)
		{
			for (int j=0;j<20;j++)
			{
			  System.out.print(masc[i][j]);
			}
			System.out.print("\n");
		}
		
		System.out.print("\n\n");
	}
	private void wave(int a, int b) // волна точка
	{
		if (a<0||a>19||b<0||b>19) return; // вышли за границу
		if (masc[a][b]==1) return; // волна наткнулась на препятствие и дальше не распространица
		masc[a][b]=1;
		wave(a-1,b);
		wave(a+1,b);
		wave(a,b-1);
		wave(a,b+1);		
	}
	private void wave()// пустить волны от краев, почти все волны затухнут сразу
	{
		for (int i=0;i<20;i++)
		{
			wave(0,i);
			wave(19,i);
			wave(i,19);
			wave(i,0);
		}
	}
	private int getSurrond() 
	{
		PieceColor color=	piece.getColor();
		int a=0;
		for (int i=0;i<20;i++)
		{
			for(int j=0; j<20;j++)
			{
				if (masc[i][j]==1) continue;
				if(!target.getBoard().isEmpty(i, j)&&target.getBoard().getSquare(i, j).getPiece().getColor()!=color)
				{
					target.getBoard().getSquare(i, j).getPiece().setColor(PieceColor.BLUE);
					a++;
				}
				
			}
		}
		return a;
	}
	
	
	private Stone piece;
	private Square target;
	
	public PointMove(Stone stone, Square[] squares) {
		piece = stone;
		target = squares[0];
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	// еще код дописан тут
	public void doMove() throws GameOver {
		target.setPiece(piece);
		System.out.print(" \n");
		initMasc();
		wave();
		//getcount();
		System.out.print(getSurrond());
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

	private boolean isGameOver() {
		// TODO Илья Гневашев. Проверить завершение игры (5 фишек в ряд).
		if (getSurrond()>9) 
			{
			System.out.print("Конец!");
				return true;
			}
		return false;
	}
}
