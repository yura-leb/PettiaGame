package gomoku.ui;

import game.core.*;
import game.ui.*;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import gomoku.ui.images.GomokuImages;
import gomoku.Gomoku;
import gomoku.pieces.Stone;


public class GomokuGamePanel extends GamePanel {

	public GomokuGamePanel(Composite composite,  int boardSize) {
		super(composite, new Gomoku(boardSize));

		GomokuBoardPanel gameBoard = new GomokuBoardPanel(this, game);
		insertSquares(gameBoard);

		GridData data = new GridData(SWT.FILL, SWT.TOP, false, true);
		data.widthHint = 120;

		int[][] sizes = { {19,19}, {15,15} };
		BoardSizePanel bsp = new BoardSizePanel(control, this, sizes);
		bsp.setLayoutData(data);
	}

	@Override
	public void resizeBoard(int nV, int nH) {
		super.resizeBoard(nV, nH);

		// Новые размеры доски и расстановка фигур.
		game.initBoard(nV, nH);
		adorned.resize(nV, nH);
	}



public static class GomokuBoardPanel extends GreenBoard implements IPieceProvider {
		public GomokuBoardPanel(Composite composite, Game game) {
			super(composite, game.board);

			// Слушатель мыши для постановки новой фигуры на доску.
			listener = new PutPieceListener(this, game);

			// Слушатель мыши для отрисовки подсказки на доске - 
			// можно ли ставить фигуру на клетку на доски.
			mouseMoveListener = PutPiecePromptListener.EMPTY;
		}

		@Override
		public Piece getPiece(Square square, PieceColor color) {
			return new Stone(square, color);
		}

		@Override
		public Image getPieceImage(Piece piece, PieceColor color) {
			return color == PieceColor.WHITE 
					? GomokuImages.imageStoneWhite
					: GomokuImages.imageStoneBlack;
		}
	}
}
