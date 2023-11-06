package go.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Изображения для игры
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GoImages {
	public static Image icoGo;

	public static Image imageStoneWhite;
	public static Image imageStoneBlack;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		icoGo  = new Image(display, GoImages.class.getResourceAsStream("icoGo.png"));

		imageStoneBlack  = new Image(display, GoImages.class.getResourceAsStream("bStone.png"));
		imageStoneWhite  = new Image(display, GoImages.class.getResourceAsStream("wStone.png"));
	}
}