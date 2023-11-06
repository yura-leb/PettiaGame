package vikings.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import game.ui.BoardSizePanel;
import game.ui.GamePanel;
import vikings.Vikings;

/**
 * Панель для игры в Викинги.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class VikingsGamePanel extends GamePanel {
	/**
	 * Создание панели для игры в Викинги.
	 * 
	 * @param parent
	 *            - куда вставляется панель.
	 * @param boardSize
	 *            - начальный размер доски.
	 */
	public VikingsGamePanel(Composite parent, int boardSize) {
		super(parent, new Vikings(boardSize));
		
		final VikingsBoardPanel gameBoard = new VikingsBoardPanel(this, game);
		insertSquares(gameBoard);
		
		GridData data = new GridData(SWT.FILL, SWT.TOP, false, true);
		data.widthHint = 120;
		
		int[][] sizes = { {9,9}, {11,11} };
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
}
