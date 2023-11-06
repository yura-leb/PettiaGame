package checkers.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Изображения для игры в шашки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CheckersImages {
	public static Image iconCheckers;
	
	public static Image imageManWhite;
	public static Image imageKingWhite;

	public static Image imageManBlack;
	public static Image imageKingBlack;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		iconCheckers    = new Image(display, CheckersImages.class.getResourceAsStream("iconCheсkers.png"));

		imageKingBlack  = new Image(display, CheckersImages.class.getResourceAsStream("bKing.png"));
		imageManBlack   = new Image(display, CheckersImages.class.getResourceAsStream("bMan.png"));
		
		imageKingWhite  = new Image(display, CheckersImages.class.getResourceAsStream("wKing.png"));
		imageManWhite   = new Image(display, CheckersImages.class.getResourceAsStream("wMan.png"));
	}
}
