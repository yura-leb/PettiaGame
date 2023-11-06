package game.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.CompositeMove;
import game.core.moves.ICaptureMove;
import game.core.moves.IPutMove;
import game.core.moves.ITransferMove;
import game.ui.listeners.IGameListner;
import game.ui.listeners.IMouseMoveListener;
import game.ui.listeners.MovePiecePromptListener;

/**
 * Базовый класс для отрисовки досок всех настольных игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
@SuppressWarnings("deprecation")
abstract
public class GameBoard extends Canvas 
	implements PaintListener, MouseListener, MouseMoveListener, Observer  
{
    /**
     * Цвет для отрисовки последнего хода.
     */
    private final Color lastMoveColor = new Color(null, 255, 0, 0);

    /**
     * Цвет для отрисовки захвата фигур.
     */
    private final Color lastCaptureColor = new Color(null, 0, 0, 255);

    /**
     * Цвет для подсказок правильных ходов.
     */
    private Color promptColor = new Color(null, 0, 255, 0);
    
	/**
	 * Доска с фигурами для игры отрисовываемая на этой панели.
	 */
	public Board board;


	/**
	 * Базовый класс для всех панелей отрисовывающих доски для игр.
	 * 
	 * @param parent
	 *            - куда встраивается панель изображающая доску.
	 * @param board
	 *            - доска с фигурами для игры, которая будет отрисовываться на
	 *            этой панели.
	 */
	public GameBoard(Composite parent, Board board) {
		super(parent, SWT.DOUBLE_BUFFERED);
		
		this.board = board;

		addPaintListener(this);
		
		addMouseListener(this);
		addMouseMoveListener(this);
		
		board.addObserver(this);
		
		// !Что бы доска получала фокус добавим слушателя клавиатуры.
		// После этого доска начнет получать события от колеса мыши.
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		// Добавим слушателя колеса мыши.
		addMouseWheelListener(e -> {
			if (e.count > 0)
				 board.history.toPrevMove();
			else board.history.toNextMove();

			board.setBoardChanged();
		});

		board.setBoardChanged();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		update();
		redraw();
	}

	@Override
	public void paintControl(PaintEvent e) {
		GC gc = e.gc;

		Rectangle clientArea = getClientArea();
	
		drawBackground(gc, clientArea);

		int squareWidth  = getSquareWidth();
		int squareHeight = getSquareHeight();
	
		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++)
				drawSquare(gc, v, h, squareWidth, squareHeight);

		markLastTransferMove(gc);

		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++)
				drawPiece(gc, v, h, squareWidth, squareHeight);
		
		markLastPutMove(gc);

		drawSquaresPrompt(gc, squareWidth, squareHeight);
	}

	/**
	 * @return высота клетки.
	 */
	protected int getSquareHeight() {
		return getClientArea().height / board.nH;
	}

	/**
	 * @return ширина клетки.
	 */
	protected int getSquareWidth() {
		return getClientArea().width  / board.nV;
	}

	Cursor boardCursor = new Cursor( Display.getCurrent(), SWT.CURSOR_ARROW);
	
	/**
	 * Сделать заданное изображение изображением курсора.
	 * 
	 * @param image
	 *            - новое изображение курсора.
	 */
	public void imageToCursor(Image image) {
		int sw = getPieceWidth();
		int sh = getPieceHeight();
		
		int pw = sw - sw/8; // Ширина фигуры в клетке.
		int ph = sh - sh/8; // Высота фигуры в клетке.
		
		ImageData imageDate = image.getImageData().scaledTo(pw,	ph);
	
		boardCursor.dispose();
		
		Display display = Display.getCurrent();
		
		boardCursor = new Cursor(display, imageDate, sw/2, sh/2);
		setCursor(boardCursor);
	}
	
	/**
	 * Сделать изображение фигуры изображением курсора.
	 * 
	 * @param piece
	 *            - фигура изображение которой "перемешается" в курсор.
	 */
	public void pieceToCursor(Piece piece) {
		Image image = getPieceImage(piece, piece.getColor());
		imageToCursor(image);
	}
	
	/**
	 * Задать цвет маркера которым будут помечаться допустимые для хода клетки.
	 * 
	 * @param color
	 *            - цвет маркера.
	 */
	public void setPromptColor(Color color) {
		promptColor = color;
	}
	
	/**
	 * Пометить на доске маркером последний ход для игр с перемещаемыми
	 * фигурами.
	 * 
	 * @param gc
	 *            - графический контекст для отрисовки маркера.
	 */
	protected void markLastTransferMove(GC gc) {
		List<Move> moves = board.history.getMoves();
		if (moves.isEmpty()) return;
		
		Move move = board.history.getCurMove();
		
		if (move == null) return;
		
		if (move instanceof CompositeMove) {
			@SuppressWarnings("unchecked")
			CompositeMove<ITransferMove> cm = (CompositeMove<ITransferMove>) move;
			for (ITransferMove m : cm.getMoves())
				markLastTransferMove(gc, m);
		}
			
		if (move instanceof ITransferMove) {
			ITransferMove m = (ITransferMove) move;
			markLastTransferMove(gc, m);
		}
	}

	private void markLastTransferMove(GC gc, ITransferMove m) {
		Square source = m.getSource();
		Square target = m.getTarget();
		
		gc.setLineWidth(3);
		markLine(gc, source, target, lastMoveColor);
		
		if (m instanceof ICaptureMove) {
			ICaptureMove capture = (ICaptureMove) m;
			
			for (Square s : capture.getCaptured())
				markCross(gc, s, lastMoveColor);
		}
	}

	/**
	 * Пометить на доске маркером последний ход для игр с фигурами которые
	 * ставятся на доску.
	 * 
	 * @param gc
	 *            - графический контекст для отрисовки маркера.
	 */
	private void markLastPutMove(GC gc) {
		List<Move> moves = board.history.getMoves();
		if (moves.isEmpty()) return;
		
		Move move = board.history.getCurMove();
		
		if (move == null) return;
			
		if (move instanceof IPutMove) {
			IPutMove m = (IPutMove) move;
			Square target = m.getTarget();
			
			gc.setLineWidth(3);
			markSquare(gc, target, lastMoveColor);
			
			if (move instanceof ICaptureMove) {
				ICaptureMove capture = (ICaptureMove) move;
				
				for (Square s : capture.getCaptured())
					markSquare(gc, s, lastCaptureColor);
			}
		}
	}

	/**
	 * Нарисовать подсказку для клеток на которые фигура может сделать очередной ход.
	 * 
	 * @param gc
	 *            - графический контекст для отрисовки подсказки.
	 * @param sw
	 *            - ширина клетки.
	 * @param sh
	 *            - высота клетки.
	 */
	void drawSquaresPrompt(GC gc, int sw, int sh) {
		if (prompted.isEmpty())
			return;
		
		gc.setLineWidth(3);
		gc.setForeground(promptColor);
		
		for (Square s : prompted)  
			markSquare(gc, s, promptColor);
//			gc.drawRectangle(s.v * sw, s.h * sh, sw, sh);
	}

	/**
	 * Выдать клетку над которой было произошло событие мыши.
	 * 
	 * @param e
	 *            - событие от мыши.
	 * @return клетка под мышкой
	 */
	private Square getSquare(MouseEvent e) {
		int squareW = getSquareWidth();
		int squareH = getSquareHeight();

		int selectedV = e.x / squareW;
		int selectedH = e.y / squareH;
		
		if (!board.onBoard(selectedV, selectedH))
			return null;
		
		return board.getSquare(selectedV, selectedH);
	}
	
	/**
	 * Отрисовать фигуру стоящую на клетке доски.
	 * 
	 * @param gc
	 *            - графический контекст для рисования клетки
	 * @param v
	 *            - вертикаль клетки
	 * @param h
	 *            - горизонталь клетки
	 * @param squareWidth
	 *            - ширина клетки
	 * @param squareHeight
	 *            - высота клетки
	 */
	protected void drawPiece(GC gc, int v, int h, int squareWidth, int squareHeight) {
		Piece piece = board.getSquare(v, h).getPiece();
		if (piece == null) return;

		int dx = squareWidth  /8;
		int dy = squareHeight /8;
		
		int x = v * squareWidth  + dx;
		int y = h * squareHeight + dy;
		
		Image image = getPieceImage(piece, piece.getColor());
		Rectangle bounds = image.getBounds();
		gc.drawImage(image, 
				0, 0, bounds.width, bounds.height, 
				x, y, squareWidth - 2*dx, squareHeight - 2*dy);
	}
	
	/**
	 * Пометить клетку цветным маркером.
	 * 
	 * @param gc
	 *            - графический контекст.
	 * @param square
	 *            - помечаемая клетка.
	 * @param markColor
	 *            - цвет маркера.
	 */
	public void markSquare(GC gc, Square square, Color markColor) {
		int v = square.v;
		int h = square.h;
		int sw = getSquareWidth();
		int sh = getSquareHeight();
	
		gc.setBackground(markColor);
		int d = 10;
		gc.fillOval(v*sw + (sw-d)/2, h*sh  + (sh-d)/2, d, d);
	}

	/**
	 * Соединить линией центры двух клеток.
	 * 
	 * @param gc
	 *            - графический контекст.
	 * @param source
	 *            - откуда линия.
	 * @param target
	 *            - куда линия.
	 * @param color
	 *            - цвет линии.
	 */
	public void markLine(GC gc, Square source, Square target, Color color) {
		int sw = getSquareWidth();
		int sh = getSquareHeight();

		int v1 = sw * source.v + sw/2;
		int h1 = sh * source.h + sh/2;

		int v2 = sw * target.v + sw/2;
		int h2 = sh * target.h + sh/2;
		
		gc.setForeground(color);
		gc.drawLine(v1, h1, v2, h2);
	}
	
	/**
	 * Нарисовать на клетке перекрестье.
	 * 
	 * @param gc
	 *            - графический контекст.
	 * @param source
	 *            - откуда линия.
	 */
	public void markCross(GC gc, Square source, Color color) {
		int sw = getSquareWidth();
		int sh = getSquareHeight();

		int v1 = sw * source.v;
		int h1 = sh * source.h;

		int v2 = sw * source.v + sw;
		int h2 = sh * source.h + sh;
		
		gc.setForeground(color);
		gc.drawLine(v1, h1, v2, h2);
		gc.drawLine(v2, h1, v1, h2);
	}
	
	/**
	 * Пометить две клетки рамками заданного цвета.
	 * 
	 * @param gc
	 *            - графический конеткст для рисования.
	 * @param source
	 *            - клетка откуда идет фигура.
	 * @param target
	 *            - клетка куда идет фигура.
	 * @param color
	 *            - цвет рамки.
	 */
	public void markSquares(GC gc, Square source, Square target, Color color) {
		int sw = getPieceWidth();
		int sh = getPieceHeight();

		int v1 = sw * source.v + sw/2;
		int h1 = sh * source.h + sh/2;

		int v2 = sw * target.v + sw/2;
		int h2 = sh * target.h + sh/2;
		
		gc.setForeground(color);
		gc.drawRectangle(v1 * sw, h1 * sh, sw, sh);
		gc.drawRectangle(v2 * sw, h2 * sh, sw, sh);
	}

	protected int getPieceHeight() {
		return getSquareHeight();
	}

	protected int getPieceWidth() {
		return getSquareWidth();
	}

	/**
	 * Отрисовка фона для доски.
	 * 
	 * @param gc - графический контекст для рисования фона доски.
	 * @param area - область для рисования фона доски.
	 */
	abstract
	protected void drawBackground(GC gc, Rectangle area);

	/**
	 * Отрисовка клетки доски.
	 * 
	 * @param gc - графический контекст в котором рисуется клетка доски.
	 * @param v - вертикаль клетки.
	 * @param h - горизонталь клетки.
	 * @param squareWidth - ширина клетки.
	 * @param squareHeight - высота клетки.
	 */
	abstract 
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight);
	
	/**
	 * Выдать изображение для заданной фигуры клетке доски.<br>
	 * <b> !!! Этот метод должен быть переопределен для игр<br>
	 * !!! в которых фигуры ставятся на доску. </b>
	 * 
	 * @param color
	 *            - цвет фигуры.
	 * @return - изображение фигуры.
	 */
	public Piece getPiece(Square square, PieceColor color) 
		{  return null; }

	/**
	 * Выдать изображение фигуры заданного цвета.
	 * 
	 * @param piece
	 *            - фигура для которой выдается изображение.
	 * @param color
	 *            - цвет фигуры.
	 * @return изображение фигуры.
	 */
	abstract
	public Image getPieceImage(Piece piece, PieceColor color);

	// ------------------------------------------------------
	// ------ Обработка событий нажатия на кнопки мыши ------
	// ------------------------------------------------------

	/**
	 * Слушатель нажатий кнопок мыши над клетками доски.
	 */
	public IGameListner listener = IGameListner.EMPTY;
	
	@Override
	public void mouseDown(MouseEvent e) {
		Square s = getSquare(e);
		
		if (s != null)
			listener.mouseDown(s, e.button);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		Square s = getSquare(e);
		
		if (s != null)
			listener.mouseUp(s, e.button);
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent e) {}
	
	// ------------------------------------------------
	// ------ Обработка событий перемещения мыши ------
	// ------------------------------------------------
	
	/**
	 * Клетки на которые допустим очередной ход фигурой.
	 * Используются для отрисовки на доске подсказок 
	 * для всех допустимых ходов этой фигуры.
	 */
	public final List<Square> prompted = new ArrayList<>();
	
	/**
	 * Слушатель события перемещения мыши.
	 */
	protected IMouseMoveListener mouseMoveListener 
					= new MovePiecePromptListener(this);

	@Override
	public void mouseMove(MouseEvent e) {
		Square s = getSquare(e);
		
		if (s != null)
			mouseMoveListener.mouseMove(s);
	}
}