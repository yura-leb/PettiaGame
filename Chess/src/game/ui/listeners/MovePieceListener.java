package game.ui.listeners;

import org.eclipse.swt.graphics.Cursor;

import game.core.Board;
import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GameBoard;

/**
 * Слушатель постановки перемещения фигуры на доске.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class MovePieceListener implements IGameListner {
	/**
	 * Выбранная для перемещения фигура.
	 */
	private Piece selectedPiece;
	
	/**
	 * Клетка на которой стоит выбранная для перемещений фигура.
	 */
	private Square selectedSquare;
	
	/**
	 * Сохраненный курсов. После перемещения фигуры как курсора,
	 * этот курсор будет восстановлен.
	 */
	private Cursor savedCursor;

	/**
	 * Доска на которой происходят изменения.
	 */
	private final Board board;
	
	/**
	 * Панель на которой рисуется доска.
	 */
	private final GameBoard boardPanel;

	/**
	 * Создать слушателя мыши для панели доски на которой перемещяются фигуры.
	 *  
	 * @param boardPanel
	 */
	public MovePieceListener(GameBoard boardPanel) {
		this.board = boardPanel.board;
		this.boardPanel = boardPanel;
	}
	
	@Override
	public void mouseDown(Square mouseSquare, int button) {
		if (mouseSquare.isEmpty())
			return;
		
		PieceColor moveColor = board.getMoveColor();

		// Выберем для перемещения фигуру нужного цвета. 
		selectedPiece = mouseSquare.getPiece();
		if (selectedPiece.getColor() != moveColor)
			return;
		
		// На время перемещения фигуры мышкой 
		// снимем ее с доски.
		selectedSquare = mouseSquare;
		selectedSquare.removePiece();
		
		// Сохраним курсор для его восстановления
		// после перемещения фигуры мышкой.
		savedCursor = boardPanel.getCursor();
		
		// Зададим изображение курсора такое как избражение у фигуры.
		boardPanel.pieceToCursor(selectedPiece);
	    
		// Перерисуем изображение доски с временно снятой фигурой.
		board.setBoardChanged();
		boardPanel.redraw();
	}
	
	@Override
	public void mouseUp(Square mouseSquare, int button) {
		if (selectedSquare == null)
			return;
		
		// Возвращаем фигуру на начальную клетку.
		// Теперь знаем куда эта фигура пойдет.
		// Все изменения на доске, связанные с этим ходом,
		// будут сделаны классом реализующим интерфейс Move.
		selectedSquare.setPiece(selectedPiece);
		
		if (selectedPiece.isCorrectMove(mouseSquare)) {
			// Ход на заданную клетку правильный.
			// Создадим экземпляр хода и выполним его.
			Move move = selectedPiece.makeMove(selectedSquare, mouseSquare);
			try {
				move.doMove();
			} catch (GameOver e) {
				// Сохраним экземпляр кода и истории партии.
				board.history.addMove(move);
				board.history.setResult(e.result);

				selectedPiece = null;
				selectedSquare = null;
				
				// Восстановим курсор (с изображением стрелки).
				boardPanel.setCursor(savedCursor);

				// Пусть слушатели изменений на доске 
				// нарисуют новое состояние доски.
				board.setBoardChanged();
				boardPanel.redraw();
			}
			
			// Сохраним экземпляр кода и истории партии.
			board.history.addMove(move);
			
			// TODO Реализовать запрос фигуры для превращения пешки.

			// Теперь ходить должен противник. 
			board.changeMoveColor();
		}

		selectedPiece = null;
		selectedSquare = null;
		
		// Восстановим курсор (с изображением стрелки).
		boardPanel.setCursor(savedCursor);

		// Пусть слушатели изменений на доске 
		// нарисуют новое состояние доски.
		board.setBoardChanged();
		boardPanel.redraw();
	}
}
