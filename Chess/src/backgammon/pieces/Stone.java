package backgammon.pieces;

import java.util.List;

import backgammon.BackgammonBoard;
import backgammon.moves.SimpleMove;
import backgammon.moves.Capture;
import game.core.Group;
import game.core.ITrackPiece;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Фигура для игры в нарды.
 */
public class Stone extends Piece implements ITrackPiece {
	Group<Stone> group;
	
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		BackgammonBoard board = (BackgammonBoard) square.getBoard();
		
		int step1 = board.cube1.getValue();
		int step2 = board.cube2.getValue();
		
		List<Square> way = board.getWay(this);
		
		Square bar = board.getBar4Piece(this);
		
		PieceColor color = getColor();
		Square target = squares[0];

		int iSource = way.indexOf(square);
		int iTarget = way.indexOf(target);
		
		if (!bar.isEmpty()) {
			// Есть пленные фигуры. Ход возможен только ими.
			BackgammonGroup barGroup = (BackgammonGroup) bar.getPiece();
			                                                     
			if (!barGroup.contains(this))
				return false;
			
			iSource = -1;
		}
		
		if (color != board.getMoveColor())
			return false;
		
		if (iTarget <= iSource)
			return false;
		
		if (target == square)
			return false; 
		
		
		if (board.isForBearing(target) && 
				board.outPiece(color) &&
				(iTarget < iSource + step1 ||
				 iTarget < iSource + step2)) {
			 return true;
		 }
		
		if (iTarget != iSource + step1 && 
				iTarget != iSource + step2)
			return false;
	
		//
		// Проверяем клетку куда идем.
		//
		// Сама фигура пойти на клетку для хранения захваченных фигур 
		// (сдаться в плен) не может.
		if (board.isBar(target))
			return false;
		
		// Пока все фигуры такого же цвета не дома,
		// сбрасывать фигуру с доски нельзя.
		if (board.isForBearing(target) && !board.outPiece(color))
			return false;
		
		// На пустую клетку пойти можно.
		if (target.isEmpty())
			return true;	

		BackgammonGroup targetPiece = (BackgammonGroup)target.getPiece();

		// На клетку со своими фигурами пойти можно.
		if (targetPiece.isFriend(this))
			return true;	
		
		// Врага-одночку можно захватить в плен.
        if (targetPiece.size() == 1)
        	return true;
		
		return false;	
	}

	@Override
	public Move makeMove(Square... squares) {
		Square square = squares[0];
		Square target = squares[1];
		
		BackgammonGroup targetPiece = (BackgammonGroup)target.getPiece();
		BackgammonBoard board = (BackgammonBoard) square.getBoard();
		
		List<Square> way = board.getWay(this);
		
		int cube1 = board.cube1.getValue();
		int cube2 = board.cube2.getValue();
//		int cube3 = board.cube3.getValue();
//		int cube4 = board.cube4.getValue();
		
		PieceColor color = getColor();
		
		int diff = Math.abs(way.indexOf(square) - way.indexOf(target));
		
		if (board.isForBearing(target) && board.outPiece(color)) {
			if (diff < cube1) {
				board.cube1.setValue(board.cube3.getValue());
				board.cube3.setValue(0);
			} else if (diff < cube2) {
				board.cube2.setValue(board.cube4.getValue());
				board.cube4.setValue(0);
			} 
		}
		
		if (diff == cube1) {
			board.cube1.setValue(board.cube3.getValue());
			board.cube3.setValue(0);
		} else if (diff == cube2) {
			board.cube2.setValue(board.cube4.getValue());
			board.cube4.setValue(0);
		}
		
		if (!target.isEmpty() && targetPiece.isEnemy(this) && targetPiece.size() == 1)
			return new Capture(square, target);
		
		return new SimpleMove(square, target);
	}

	@Override
	public boolean hasCorrectMoveFrom(Square square) {
		return true;
	}
	
	@Override
	public String toString() {
		return "s[" + square + "]";
	}
}