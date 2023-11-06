package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import game.core.Cube;
import game.ui.images.GameImages;

/**
 * Класс для отображения текущего значения 
 * у кубика "генерирующего" случайные числа.
 */
public class CubePanel extends Canvas {
	public Cube cube;
	private Label cubeView;

	public CubePanel(Composite parent, Cube cube) {
		super(parent, SWT.NONE);
		this.cube = cube;
		
		FillLayout layout = new FillLayout(SWT.HORIZONTAL);
		setLayout(layout);

		cubeView = new Label(this, SWT.NONE);
		update();
	}
	
	public void update() {
		Image cubeImage = GameImages.getCubeImage(cube.getValue());
		cubeView.setImage(cubeImage);
	}
}