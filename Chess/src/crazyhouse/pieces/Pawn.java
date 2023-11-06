package crazyhouse.pieces;

import java.util.List;

import crazyhouse.moves.BoxMove;
import crazyhouse.moves.Capture;
import crazyhouse.moves.SimpleMove;

import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

public class Pawn extends CrazyHousePiece {
	public Pawn(Square square, PieceColor color) {
		super(square, color);
	}
	private boolean flgMakeDoubleMove;

	public Pawn() {
		super();
	}

	public Square GetEnemyPieceForEnPassantMove(Square source, Square target)
	{
		int dv = Math.abs(target.v - source.v);
		//Firstly, we have to ensure whether either our or opponent's piece is situated on the right position
		if((source.h == 3 && isWhite() || source.h == 4 && isBlack()) && dv == 1)
		{
			//Then we should find appropriate figure in the collection of enemies
			List<Piece> listEnemies = getEnemies();
			
			//Lets find the instance of enemy Pawn object, which satisfies the following requirements.
			for(Piece enemy : listEnemies)
			{
				if(enemy.getClass() == Pawn.class)
				{
					flgMakeDoubleMove = ((Pawn) enemy).IfMakeDoubleMove();
				}
				
				if(
						//Cases which must be done for white piece
						//1) The found piece is black
						//2) Beginning from the top to bottom area, several subcases could be found, which are essential for successful executing of enpassant move. 
							// - The black pawn is on the third row AND 
							// - It makes double move simultaneously AND
							// - Horizontal subcase: h-axes between black pawn and target point differs by exactly one square AND
							// - Vertical subcase: v-axe position of black pawn and target point must be absolutely equal with each other
						(isWhite() && enemy.isBlack() && enemy.square.h == 3 && flgMakeDoubleMove && enemy.square.h-1 == target.h && enemy.square.v == target.v) 
						
						||
						
						//This case is a reverse pattern of aforementioned one, because the same rules should be shared for the opponent.
						(isBlack() && enemy.isWhite() && enemy.square.h == 4 && flgMakeDoubleMove && enemy.square.h+1 == target.h && enemy.square.v == target.v))
				{
					//If at least one case is true, then we return instance of the current object.
					return enemy.square;
				}
			}
			
		}
		
		//Do we have the lack of instance of Pawn object after the unsuccessful loop execution mentioned before? In this case, we have to return null.
		return null;
	}

	
	/* Shortly speaking, this method returns the possible ways where exactly could move a selected chess piece.
	 * Class MovePiecePromptListener has a method called "mouseMove", which is declared in interface IMouseMoveListener.
	 *By focusing on some chess piece on the board, each square on the board will be checked (namely 64 times) through the loop, which is implemented in the method mentioned before. 
	 */
	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		// если производится ход фигурой, которая находится в резерве
		if (getBoxPiece()) {
			// если игрок пытается поставить фигуру на занятую клетку
			if (!target.isEmpty())
				return false; // => возвращаем false, так как по правилам CrazyHouse это запрещено
			
			// иначе: дополнительная проверка для пешки
			
			// если производится ход пешкой на первый или последний ряд, то
			// возвращаем false, так как по правилам CrazyHouse это запрещено,
			// иначе ход является корректным => возвращаем true
			return !(target.h == 0 || target.h == 7);
		}
		
		// иначе: используем обычную логику проверки хода для пешки
		
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square source = square;
		
		// добавляем проверку хода пешки по правилам CrazyHouse
		
		// если производится ход пешкой, которая находится в резерве, то:
		// - если клетка target находится в первом или последнем ряду => возвращаем false,
		//   так как по правилам CrazyHouse запрещено делать ход на эту клетку
		// - в остальных случаях ход является корректным => возвращаем true
		if (this.getBoxPiece())
			return !(target.h == 0 || target.h == 7); // определяем находится ли клетка target в первом или последнем ряду

		// иначе: если это обычный ход пешки
		
		int dv = Math.abs(target.v - source.v);
		int dh = isWhite()
				? source.h - target.h 
				: target.h - source.h;
		
		
		
		if (dv != 0) {
			// Есть смещение пешки по вертикали. 
			// Возможно это взятие пешкой вражеской фигуры.
						
			if (dv > 1)
			   return false; // Смещение больше, чем на 1 клетку
			
			if(GetEnemyPieceForEnPassantMove(source, target) != null)
				return true;
			
			
			
			if (target.isEmpty())
				return false; // Бить некого.
			
			if (dh <= 0)      // Смещение пешки назад.
				return false; // Назад пешки не бьют.
			
			if (dh > 1)       // Так далеко пешки не бьют.
				return false;  
			
			
			
			
			return true;
		}
		
		// По вертикали пешка фигуры не бьет.
		if (!target.isEmpty())
			return false;
		
		// Пошла ли пешка с начальной позиции.
		boolean isStartPosition = isWhite() ? source.h == 6 : source.h == 1; 
		
		
		
		// Насколько клеток может пойти пешка.
		int upper = isStartPosition ? 2 : 1; 
		
		if (isStartPosition && (dh == 2)) {
			// Пешка прыгает с начальной позиции.
			
			// Не пытается ли пешка перепыгнуть через фигуру (барьер)?
			int barierV = source.v;
			int barierH = (source.h + target.h) / 2;
			
			Board board = source.getBoard();
			if (!board.isEmpty(barierV, barierH))
				return false; // Перепрыгивать нельзя.
		}
		
		if ((1 <= dh) && (dh <= upper))
			return true;
		
		return false;
	}
	
	@Override
	public Move makeMove(Square... squares) {
		// если производится ход фигурой из резерва
		if (getBoxPiece())
			return new BoxMove(squares[0], this);
		
		flgMakeDoubleMove = false;
		
		Square source = squares[0];
		Square target = squares[1];
		
		int dv = Math.abs(target.v - source.v);
		
		boolean isLastHorizontal = isWhite()
					? target.h == 0 : target.h == 7;
		
		if (isLastHorizontal) // Ход на последнюю горизонталь.
			return new crazyhouse.moves.Promotion(squares);	
		
		if (dv == 1) // Ход по диагонали.
		{
			Piece target_location = squares[1].getPiece();
			Square enemy_square = GetEnemyPieceForEnPassantMove(source, target);
			if(target_location == null && enemy_square != null && enemy_square.getPiece() != null)	
			{
				return new crazyhouse.moves.EnPassant(squares, enemy_square);
			}
			else
			{
				return new Capture(squares);
			}
		}
		
		//Empassant
		if (Math.abs(target.h - source.h) == 2) // Ход по вертикали на 2 позиции
		{
			flgMakeDoubleMove = true;
		}
		
		return new SimpleMove(squares);
	}
	
	@Override
	public String toString() {
		return "";
	}
	
	public boolean IfMakeDoubleMove()
	{
		return flgMakeDoubleMove;
	}
}
