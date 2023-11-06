package crazyhouse.ui.listeners;

import org.eclipse.swt.graphics.Cursor;

import game.core.Board;
import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GameBoard;
import game.ui.listeners.IGameListner;

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
     * Фигура из хранилища
     */
	private Piece boxSelectedPiece;

	/**
	 * Создать слушателя мыши для панели доски на которой перемещяются фигуры.
	 *  
	 * @param boardPanel
	 */
	public MovePieceListener(GameBoard boardPanel) {
		this.board = boardPanel.board;
		this.boardPanel = boardPanel;
	}
	
    /**
     * Задать фигуру для слушателя.
     * @param p - фигура.
     */
    @Override
    public void setPiece(Piece p) {
		// определяем цвет игрока, который должен сейчас ходить
		PieceColor moveColor = board.getMoveColor();
		
		// если пытаемся взять фигуру из чужого хранилища
		if (p.getColor() != moveColor)	
			return; // => ничего не делаем: выходим из функции
		
		// иначе: если пытаемся взять/положить фигуру из/в своего/своё хранилища
		
		// если на предыдущем шаге была взята фигура из банка
		if (boxSelectedPiece != null) {
			// => пытаемся вернуть её на место
			
			// если пытаемся поставить фигуру на чужое место в хранилище
			if (p != boxSelectedPiece)
				return;
			
			// иначе: ставим фигуру обратно на её место
			
    		// восстановим курсор (с изображением стрелки).
    		boardPanel.setCursor(savedCursor);
    		
    		// установим значений взятой фигуры с box-панели в значение null
    		boxSelectedPiece = null;
    		
    		return;
		}
		
		// иначе: если мы пытаемся взять фигуру из нашего хранилища
		
		// запоминаем фигуру
		boxSelectedPiece = p;
		
		// Сохраним курсор для его восстановления
		// после перемещения фигуры мышкой.
		savedCursor = boardPanel.getCursor();
		
		// Зададим изображение курсора такое как избражение у фигуры.
		boardPanel.pieceToCursor(boxSelectedPiece);
	    
		// Перерисуем изображение доски
		board.setBoardChanged();
		boardPanel.redraw();
    }
	
	// начинаем тянуть фигурку
	@Override
	public void mouseDown(Square mouseSquare, int button) {
		// если раннее игрок взял фигуру из хранилища (и держит её на курсоре)
		if (boxSelectedPiece != null) {
			// если ход является корректным c точки зрения правил игры CrazyHouse
			if (boxSelectedPiece.isCorrectMove(mouseSquare)) {
				// Создадим экземпляр хода и выполним его.
				Move move = boxSelectedPiece.makeMove(mouseSquare);
				
				try {
					move.doMove(); // выполяем ход фигурой на заданную клетку
				} catch (GameOver e) {
					// Сохраним экземпляр хода и истории партии.
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
				
				// Сохраним экземпляр хода и истории партии.
				board.history.addMove(move);
				
				// Теперь ходить должен противник. 
				board.changeMoveColor();
				
				selectedPiece = null;
				selectedSquare = null;
				
				// восстановим курсор (с изображением стрелки).
				boardPanel.setCursor(savedCursor);
				
				// установим значений взятой фигуры с box-панели в значение null
				boxSelectedPiece = null;
				
				// Пусть слушатели изменений на доске 
				// нарисуют новое состояние доски.
				board.setBoardChanged();
				boardPanel.redraw();
			}
			
			return;
		}
		
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
	
	
	// отпускаем фигурку
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