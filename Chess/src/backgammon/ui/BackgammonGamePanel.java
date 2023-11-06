package backgammon.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import backgammon.Backgammon;
import backgammon.pieces.BackgammonGroup;
import backgammon.pieces.Stone;
import backgammon.ui.images.BackgammonImages;
import game.core.BoardWithCubes;
import game.core.CubesPanel;
import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GameBoard;
import game.ui.GamePanel;
import game.ui.ScorePanel;
import game.ui.images.GameImages;
import game.ui.listeners.MovePieceListener;
import game.ui.listeners.MovePiecePromptListener;

/**
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class BackgammonGamePanel extends GamePanel {
	public BackgammonGamePanel(Composite composite) {
		super(composite, new Backgammon());

		BackgammonBoardPanel gameBoard = new BackgammonBoardPanel(this, game);
		insertSquares(gameBoard);

		GridData data = new GridData(SWT.FILL, SWT.BOTTOM, false, true);
		data.widthHint = 100;

		ScorePanel sp = new ScorePanel(control, game);
		sp.setLayoutData(data);

		CubesPanel cp = new CubesPanel(control, (Backgammon) game);
		cp.setLayoutData(data);
	}
}

/**
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class BackgammonBoardPanel extends GameBoard implements IPieceProvider {
	private final static Color BLACK_COLOR = new Color(null,   0,   0,  0);
	private final static Color DARK_COLOR  = new Color(null, 139,  69, 19);
	private final static Color LIGHT_COLOR = new Color(null, 244, 164, 96);

	/**
	 * Создать доску для игры в нарды.
	 * 
	 * @param composite - составной элемент содержащий доску.
	 * @param game      - игра
	 */
	public BackgammonBoardPanel(Composite composite, Game game) {
		super(composite, game.board);

		// Слушатель мыши для постановки новой фигуры на доску.
		listener = new MovePieceListener(this);

		// Слушатель мыши для отрисовки подсказки на доске -
		// можно ли ставить фигуру на клетку на доски.
		mouseMoveListener = new MovePiecePromptListener(this);
	}

	@Override
	public Piece getPiece(Square square, PieceColor color) {
		return new Stone(square, color);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return color == PieceColor.WHITE 
				? BackgammonImages.imageStoneWhite 
				: BackgammonImages.imageStoneBlack;
	}

	@Override
	protected int getPieceHeight() {
		// Сохраняем пропорции фигуры используемой курсором,
		// поскольку в нардах высота и ширина поля различаются.
		return getPieceWidth();
	}

	@Override
	protected void drawBackground(GC gc, Rectangle area) {
		Rectangle bounds = GameImages.woodLight.getBounds();
		
		gc.drawImage(GameImages.woodLight, 
				0, 0, bounds .width, bounds.height, 
				area.x, area.y, area.width, area.height);

		gc.setForeground(BLACK_COLOR);
		gc.drawRectangle(area.x, area.y, area.width, area.height);
	}

	@Override
	protected void drawPiece(GC gc, int v, int h, int squareWidth, int squareHeight) {
		BackgammonGroup group = (BackgammonGroup) board.getSquare(v, h).getPiece();
		
		if (group == null) return;
		
		boolean isTop = (h == 0);
		int dir = isTop ? +1 : -1;

		int shift = squareWidth/16;
		int pieceSize = squareWidth - 2*shift;
		int pieceSize2 = pieceSize/2;

		int step = pieceSize + shift;
		
		int x = v * squareWidth  + shift;
		int y = h * squareHeight + (isTop ? shift : squareHeight - pieceSize);
		int yStart = y;

		// Фигуры выставляются слоями;
		// 5 фигур на 1-м уровне, 4 фигуры на втором, ...
		int levelSize = 6;

		for (int kPiece = 0; kPiece < group.size(); kPiece++) {
			int level = (1+kPiece) / levelSize;

			Piece piece = group.getPiece(kPiece); 
			Image image = getPieceImage(piece, piece.getColor());
			Rectangle bounds = image.getBounds();
			
			gc.drawImage(image, 
				0, 0, bounds.width, bounds.height, 
				x, y, pieceSize, pieceSize);
			y += dir * step;
			
			boolean isNextLevel = (1+kPiece) % levelSize == 0;
			if (isNextLevel) {
				// Начинаем выкладывать на следующем уровне
				// со сдвигом на половину фигуры.
				int levelShift = dir * pieceSize2 * level;
				
				y = yStart + levelShift;
//				levelSize--;
			}
		}
	}

	int getPieceY(Square square) {
		boolean isTop = (square.h == 0);
		
		BackgammonGroup group = (BackgammonGroup) square.getPiece();
		int nStones = group == null ? 0 : group.size();

		// Вверх или вниз выставляются фигуры в клетке.
		int dir = isTop ? +1 : -1; 
		
		int sw = getSquareWidth();
		int sh = getSquareHeight();

		int shift = sw/16;
		int pieceSize = sw - 2*shift;

		int step = pieceSize + shift;
		
		int y = square.h * sh + (isTop ? pieceSize/2 : sh - pieceSize/2);
		
		y += nStones * dir * step;

		return y;
	}
	
	@Override
	public void markSquare(GC gc, Square square, Color markColor) {
		int v = square.v;
		int sw = getSquareWidth();
	
		gc.setBackground(markColor);
		int d = 10;
		int x = v*sw + (sw-d)/2;
		int y = getPieceY(square);
		gc.fillOval(x, y, d, d);
	}
	
	@Override
	protected void markLastTransferMove(GC gc) {
		// Пометку последнего хода не делаем.
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int sw, int sh) {
		boolean isOdd = (v % 2 == 0);
		
		// Для хранения захваченных фигур противника.
		boolean isBar  = (v == 6);
		
		// Для хранения своих фигур сброшенных с доски.
		boolean isForBearing = (v == 13);
		
		boolean hasLeftBorder   = (v == 0) || isBar || (v == 7);
		boolean hasRightBorder  = isForBearing || (v == board.nV-2);
		boolean hasTopBorder    = (h == 0);
		boolean hasBottomBorder = (h == board.nH-1);
		
		boolean isTopSide    = (h == 0);
		boolean isBottomSide = (h == 1);
		
		boolean topDark    = isTopSide && isOdd;
		boolean bottomDark = isBottomSide && !isOdd;
		boolean isDark     = topDark || bottomDark;
		
		int x = v * sw;
		int y = h * sh;
		
		if (!isBar && !isForBearing) {
			int[] upTriangle   = {x, y,    x+sw, y,    x+sw/2, y+sh+4};
			int[] downTriangle = {x, y+sh, x+sw, y+sh, x+sw/2, y-4};
			
			gc.setBackground(isDark ? DARK_COLOR : LIGHT_COLOR);
			gc.fillPolygon(isBottomSide ? downTriangle : upTriangle);
		}
		
		if (hasLeftBorder)  gc.drawLine(x, y, x, y + sh);
		if (hasRightBorder) gc.drawLine(x + sw, y, x + sw, y + sh);

		if (hasTopBorder)    gc.drawLine(x, y, x + sw, y);
		if (hasBottomBorder) gc.drawLine(x, y + sh, x + sw, y + sh);
	}
	
	@Override
	public void mouseUp(MouseEvent e) {
		super.mouseUp(e);
		
		// Человек сделал ход.
		BoardWithCubes b = (BoardWithCubes)board;
		//b.dropCubes();
		b.setBoardChanged();
	}
}