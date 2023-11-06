package game.core;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Group;

import game.ui.CubePanel;
import game.ui.GameControlPanel;

/**
 * Панель для отображения двух кубиков используемых 
 * в игре для получения случайных чисел.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
@SuppressWarnings("deprecation")
public class CubesPanel extends Canvas implements Observer {
	private static final Color COLOR_FRAME = new Color(null, 0, 0, 255);

	private CubePanel cube1Panel;
	private CubePanel cube2Panel;
	private CubePanel cube3Panel;
	private CubePanel cube4Panel;
	
	private Game game;

	public CubesPanel(GameControlPanel parent, Game game) {
		super(parent, SWT.NONE);
		
		this.game = game;
		
		FillLayout layout = new FillLayout(SWT.HORIZONTAL);
		layout.spacing = 5;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		setLayout(layout);
		
		Group group = new Group(this, SWT.SHADOW_IN);
		group.setForeground(COLOR_FRAME);
		group.setLayout( new GridLayout(2, false) );
		group.setText("Кубики для хода:");

		BoardWithCubes board = (BoardWithCubes) game.board;
		cube1Panel = new CubePanel(group, board.cube1);
		cube2Panel = new CubePanel(group, board.cube2);
		cube3Panel = new CubePanel(group, board.cube3);
		cube4Panel = new CubePanel(group, board.cube4);

		game.board.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		BoardWithCubes board = (BoardWithCubes) game.board;
		
		cube1Panel.cube = board.cube1;
		cube1Panel.update();
		
		cube2Panel.cube = board.cube2;
		cube2Panel.update();
		
		cube3Panel.cube = board.cube3;
		cube3Panel.update();
		
		cube4Panel.cube = board.cube4;
		cube4Panel.update();
	}
}