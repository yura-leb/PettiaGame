package clobber.moves;

import java.util.List;

import clobber.pieces.Stone;
import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ITransferMove;

public class Capture implements ITransferMove {
    private final Square source;
    private final Square capturedSquare;
    private final Square target;
    private final Piece piece;
    private final Piece captured;

    
    public Capture(Stone stone, Square[] squares) {
    	
        source = squares[0];
        target = squares[1];
        piece = source.getPiece();
        captured = target.getPiece();
        capturedSquare = captured.square;
          
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public void doMove() throws GameOver {
    	
    	captured.remove();
    	piece.moveTo(target);
    	Board board = source.getBoard();
		
		PieceColor enemyColor = captured.getColor();
		List<Piece> enemies = board.getPieces(enemyColor);
		PieceColor sourceColor = piece.getColor();
		List<Piece> pieces = board.getPieces(sourceColor);
		boolean gameOver = true;
		for (Piece enemy : enemies) {
			if (anyPieces(enemy,pieces)) {
				gameOver = false;
			}
		}
		if (gameOver) {
			throw new GameOver(GameResult.win(piece));
		}
	}
    public boolean anyPieces(Piece enemy, List<Piece> pieces) {
    	for (Piece piece : pieces) {
    		if (enemy.square.isNear(piece.square)) {
    			return true;
    		}
    	}
    	return false;
    }

    @Override
    public void undoMove() {
    	piece.moveTo(source);
    	capturedSquare.setPiece(captured);
    }
    @Override
	public String toString() {
		return "" + source + "-" + target;
	}
    @Override
    public Square getSource() {
        return source;
    }

    @Override
    public Square getTarget() {
        return target;
    }
}
