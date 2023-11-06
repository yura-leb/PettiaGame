package halma.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Изображения игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a> 
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class HalmaImages {
	public static Image icoHalma;

	public static Image imageStoneBlack;
	public static Image imageStoneWhite;
	
	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		icoHalma  = new Image(display, HalmaImages.class.getResourceAsStream("icoHalma.png"));

		imageStoneBlack  = new Image(display, HalmaImages.class.getResourceAsStream("bStone.png"));
		imageStoneWhite  = new Image(display, HalmaImages.class.getResourceAsStream("wStone.png"));
	}
}
