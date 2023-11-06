package shogi.moves;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import game.core.GameOver;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ITransferMove;
import game.players.IPlayer;
import shogi.pieces.ShogiPiece;

/**
 * 
 */
public class SimpleMove implements ITransferMove {
	/**
	 * Откуда пошла фигура.
	 */
	protected final Square source;

	/**
	 * Куда пошла фигура.
	 */
	protected final Square target;

	/**
	 * Какая фигура пошла.
	 */
	protected ShogiPiece piece;
	
	private boolean wasTransformed = false; //Была ли трансформирована фигура, используется для хождения по истории

	public SimpleMove(Square... squares) {
		source = squares[0];
		target = squares[1];

		piece = (ShogiPiece) source.getPiece();
	}

	public SimpleMove(ShogiPiece piece, Square... squares) {
		source = squares[0];
		target = squares[1];

		this.piece = piece;
	}
	
	private boolean isTransformable() {
		if (piece.isTransformable == true && piece.isTransformed == false) {
			if (piece.getColor() == PieceColor.WHITE && (target.h < 3 || source.h < 3)) {
				return true;
			} else if (piece.getColor() == PieceColor.BLACK && (target.h > 5 || source.h > 5)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isLastHorizontal() {
		switch (piece.getColor()) {
			case WHITE: return piece.square.h == 0;
			case BLACK: return piece.square.h == 8;
			default: return false;
		}
	}
	
	private boolean isRequiredTransformation() {
		String className = piece.getClass().getName();
		
		switch (className) {
			case "shogi.pieces.Pawn": return isLastHorizontal();
			case "shogi.pieces.Lance": return isLastHorizontal();
			case "shogi.pieces.Knight": return false;
			default: return false;
		}
	}
	
	private void confirmTransformation()
    {
		PieceColor pieceColor = piece.getColor();
		IPlayer blackPlayer = piece.square.getBoard().getBlackPlayer();
		IPlayer whitePlayer = piece.square.getBoard().getWhitePlayer();
		Boolean show = false;
		
		if (whitePlayer == IPlayer.HOMO_SAPIENCE && pieceColor == PieceColor.WHITE) {
			show = true;
		} else if (blackPlayer == IPlayer.HOMO_SAPIENCE && pieceColor == PieceColor.BLACK) {
			show = true;
		} 
		
		if (show && !isRequiredTransformation()) {
			Display display = Display.getCurrent();
			int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
			final Shell shell = new Shell(display);
			
			MessageBox messageBox = new MessageBox (shell, style);
	        messageBox.setText ("Подтверждение превращения");
	        messageBox.setMessage ("Трансформировать фигуру?");
	        int code = messageBox.open();
	        
	        switch (code) {
		        case SWT.YES : {piece.isTransformed = true;
		        				wasTransformed = true;
		        				piece.transformSquare = target;}
		        default : break;
	        }
	        
	        shell.dispose();
		} else if(isRequiredTransformation()) {
			piece.isTransformed = true;
			wasTransformed = true;
			piece.transformSquare = target;
		}
		
    }

	@Override
	public Square getTarget() {
		return target;
	}

	@Override
	public Square getSource() {
		return source;
	}

	@Override
	public void doMove() throws GameOver {
		piece.moveTo(target);
		
		if(isTransformable() && !wasTransformed) 
			confirmTransformation();
		if(wasTransformed && piece.transformSquare == target) {
			piece.isTransformed = true;
		}
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
		
		if(piece.isTransformed == true && piece.transformSquare == target) {
			piece.isTransformed = false;
		}
	}

	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
}