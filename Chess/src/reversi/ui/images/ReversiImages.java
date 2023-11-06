package reversi.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Изображения для игры в 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiImages {
	public static Image icoReversi;
	public static Image icoReversiX;

	public static Image imageStoneBlack;
	public static Image imageStoneWhite;
	
	public static Image imageHoleBlack;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		icoReversi  = new Image(display, ReversiImages.class.getResourceAsStream("icoReversi.png"));
		icoReversiX = new Image(display, ReversiImages.class.getResourceAsStream("icoReversiX.png"));

		imageHoleBlack  = new Image(display, ReversiImages.class.getResourceAsStream("bHole.png"));

		imageStoneBlack  = new Image(display, ReversiImages.class.getResourceAsStream("bStone.png"));
		imageStoneWhite  = new Image(display, ReversiImages.class.getResourceAsStream("wStone.png"));
	}
}
