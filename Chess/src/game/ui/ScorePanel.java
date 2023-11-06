package game.ui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import game.core.Game;
import game.core.PieceColor;

/**
 * Панель для демонстрации количества счета в игре.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
@SuppressWarnings("deprecation")
public class ScorePanel extends Canvas implements Observer {
	private static final int MIN_W = 300;
	
	private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);
	private static final Color COLOR_GRAY  = new Color(null, 192, 192, 192);
	private static final Color COLOR_TEXT  = new Color(null,   0,   0,   0);
	private static final Color COLOR_FRAME = new Color(null,   0,   0, 255);

	private final Label whiteScore;
	private final Label blackScore;

	private int wScore;
	private int bScore;
	
	private final Game game;

	public ScorePanel(Composite parent, Game game) {
		super(parent, SWT.NONE);
		
		this.game = game;

		FillLayout layout = new FillLayout(SWT.VERTICAL);
		layout.spacing = 10;
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		setLayout(layout);
		
		Group group = new Group(this, SWT.SHADOW_IN);
		group.setForeground(COLOR_FRAME);
		group.setLayout( new GridLayout(1, false) );
		group.setText("Счет");
		
		GridData data;
		
		data = new GridData(SWT.FILL, SWT.BOTTOM, true, true);
		data.widthHint = 100;
		
		whiteScore = new Label(group, SWT.BORDER_SOLID);
		whiteScore.setBackground(COLOR_WHITE);
		whiteScore.setForeground(COLOR_TEXT);
		whiteScore.computeSize(MIN_W, SWT.DEFAULT);
		whiteScore.setLayoutData(data);

		data = new GridData(SWT.FILL, SWT.BOTTOM, true, true);
		data.widthHint = 100;
		
		blackScore = new Label(group, SWT.BORDER_SOLID);
		blackScore.setBackground(COLOR_GRAY);
		blackScore.setForeground(COLOR_TEXT);
		blackScore.computeSize(MIN_W, SWT.DEFAULT);
		blackScore.setLayoutData(data);
		
		wScore = game.getScore(PieceColor.WHITE);
		bScore = game.getScore(PieceColor.BLACK);
		
		whiteScore.setText("Белые:\t"  + wScore);
		blackScore.setText("Черные:\t" + bScore);

		game.board.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		wScore = game.getScore(PieceColor.WHITE);
		bScore = game.getScore(PieceColor.BLACK);
		
		whiteScore.setText("Белые:\t"  + wScore);
		blackScore.setText("Черные:\t" + bScore);
	}
}
