package game.ui;

import game.core.History;
import game.core.Move;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Панель для изображения последовательности ходов в партии.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 *
 */
@SuppressWarnings("deprecation")
public class HistoryPanel extends Canvas 
	implements Observer
{
	private static final Font font = new Font(Display.getCurrent(), "mono", 10,
			SWT.NORMAL);

	private static final Color WHITE = new Color(null, 255, 255, 255);
	private static final Color BLACK = new Color(null,   0,   0,   0);

	final History history;

	/**
	 * Создать панель для изображения последовательности ходов в партии.
	 * 
	 * @param parent
	 *            родительский управляющий элемент.
	 * @param history
	 *            история партии
	 */
	public HistoryPanel(Composite parent, History history) {
		super(parent, SWT.BORDER);

		this.history = history;
		
		history.getBoard().addObserver(this);

		setBackground(WHITE);

		GridLayout layout = new GridLayout(3, false);
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		
		GridData dataNum  = new GridData(SWT.RIGHT, SWT.BOTTOM, false, false);
		GridData dataMove = new GridData(SWT.RIGHT, SWT.BOTTOM, false, false);

		Label cell;
		cell = new Label(this, SWT.BORDER);
		cell.setFont(font);
		cell.setText(" N  ");
		cell.setLayoutData(dataNum);

		cell = new Label(this, SWT.BORDER);
		cell.setFont(font);
		cell.setText("Белые ");
		cell.setForeground(BLACK);
		cell.setBackground(WHITE);
		cell.setLayoutData(dataMove);

		cell = new Label(this, SWT.BORDER);
		cell.setFont(font);
		cell.setText("Черные ");
		cell.setForeground(WHITE);
		cell.setBackground(BLACK);
		cell.setLayoutData(dataMove);

		for (int k = 1; k <= 28; k++) {

			String blank = (k < 10 ? "  " : " ");
			cell = new Label(this, SWT.BORDER);
			cell.setFont(font);
			cell.setText(blank + k + "  ");
			cell.setLayoutData(dataNum);
			cell.setForeground(BLACK);

			cell = new Label(this, SWT.BORDER);
			cell.setFont(font);
			cell.setText(" ");
			cell.setLayoutData(dataMove);

			cell = new Label(this, SWT.BORDER);
			cell.setFont(font);
			cell.setText(" ");
			cell.setLayoutData(dataMove);
		}

		init();
	}

	/**
	 * 
	 */
	void init() {
		clear();
		
		Control[] ch = getChildren();
		
		int kLabel = 4, kMove = 0;
		for (Move m : history.getMoves()) {
			Label label = (Label) ch[kLabel];
			label.setText(m.toString());
			kLabel += (kMove % 2 == 0 ? 1 : 2);
			kMove++;
		}
	}

	void clear() {
		Control[] ch = this.getChildren();

		int kLabel = 4, kMove = 0;
		while (kLabel < ch.length) {
			Label label = (Label) ch[kLabel];
			label.setText(" ");
			kLabel += (kMove % 2 == 0 ? 1 : 2);
			kMove++;
		}
	}


	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		init();
	}
}
