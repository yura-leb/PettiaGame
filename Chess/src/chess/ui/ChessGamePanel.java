package chess.ui;

import org.eclipse.swt.widgets.Composite;

import chess.Chess;
import game.ui.GamePanel;

/**
 * Панель для игры в шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChessGamePanel extends GamePanel {
	public ChessGamePanel(Composite parent) {
		super(parent, new Chess());
		
		insertSquares( new ChessBoardPanel(this, game) );
	}
}
