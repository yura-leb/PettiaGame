package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import game.core.Board;

/**
 * Панель выбора размеров доски для игры.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class BoardSizePanel extends Composite {
	private static final Color TITLE_COLOR = new Color(null, 255, 255, 0);

	/**
	 * Создать панель для выбора размеров доски.
	 * 
	 * @param parent - куда вставляется панель.
	 * @param gamePanel - панель игры.
	 * @param sizes - допустимые размеры доски.
	 */
	public BoardSizePanel(Composite parent, GamePanel gamePanel, int[] ... sizes) {
		super(parent, SWT.NONE);
		setLayout( new GridLayout(1, true) );

		Board board = gamePanel.game.board;
		
		GridData groupData = new GridData(SWT.FILL, SWT.FILL, true, true);
		
		Group group = new Group(this, SWT.SHADOW_IN);
		group.setText("Доска");
		group.setForeground(TITLE_COLOR);
		group.setLayout( new GridLayout(1, false) );
		group.setLayoutData(groupData);
		
		for (int[] size : sizes) {
			int nV = size[0];
			int nH = size[1];

			Button b = new Button(group, SWT.RADIO);
			b.setText(String.format("%d x %d", nV, nH));
			
			if ((nV == board.nV) && (nH == board.nH))
				b.setSelection(true);
			
			b.addListener(SWT.Selection, event -> {
				if (b.getSelection())  
					gamePanel.resizeBoard(nV, nH);
			});
		}
	}
}
