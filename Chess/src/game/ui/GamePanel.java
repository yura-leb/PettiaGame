package game.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;

/**
 * Составная панель для настольной игры:
 * 	<ul><li>
 * 		доска с клетками
 * 	 <li></li>
 * 		панель истории партии (список ходов)
 *	 <li></li>
 *		управляющая панель (выбор игроков, размера доски, счет, ...)
 * </li></ul>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GamePanel extends Composite {
	/**
	 * Игра которая отображается на доске.
	 */
	public Game game;

	/**
	 * Панель для управления игрой:
	 * выбор патрнеров, размера доски, показ текущего счета.
	 */
	public GameControlPanel control;

	/**
	 * Журнал с записанными ходами партии.
	 */
	public MovesJornal jornal;

	/**
	 * Доска без нумерации вертикалей и горизонталей.
	 */
	public GameBoard gameBoard;

	/**
	 * Доска с нумерацией вертикалей и горизонталей.
	 */
	public AdornedBoard adorned;

	public GamePanel(Composite parent, Game game) {
		super(parent, SWT.TRANSPARENT);
		this.game = game;
		
		setLayout( new GridLayout(3, false) );
		
		GridData data;

		data = new GridData(SWT.LEFT, SWT.FILL, false, true);
		data.widthHint = 150;

		control = new GameControlPanel(this, game);
		control.setLayoutData(data);
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		adorned = new AdornedBoard(this);
		adorned.setLayoutData(data);
	}

//	public GamePanel(Composite parent, Color color) {
//		super(parent, SWT.TRANSPARENT);
//	}

	public static Map<Class<? extends Game>, Class<? extends GameBoard>> gamePanelMap = new HashMap<>();

	/**
	 * Вставить в панель игры доску с клетками.
	 * 
	 * @param gameBoard вставляемая доска с клетками.
	 */
	public void insertSquares(GameBoard gameBoard, boolean hasBoxes) {
		this.gameBoard = gameBoard;
		adorned.insertSquares(gameBoard, hasBoxes);
		
		jornal = new MovesJornal(this, gameBoard.board.history);
		jornal.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));
		
		gamePanelMap.put(game.getClass(), gameBoard.getClass());
	}

	public void insertSquares(GameBoard gameBoard) {
		insertSquares(gameBoard, false);
	}

	/**
	 * Изменить размеры доски.
	 * 
	 * @param nV количество вертикалей.
	 * @param nH количество горизонталей.
	 */
	public void resizeBoard(int nV, int nH) {
		// Новые размеры доски и расстановка фигур.
		game.initBoard(nV, nH);
		
		adorned.resize(nV, nH);
	}
}