package backgammon.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class BackgammonImages {
	public static Image iconBackgammon;
	public static Image imageStoneWhite;
	public static Image imageStoneBlack;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		iconBackgammon  = new Image(display, BackgammonImages.class.getResourceAsStream("icoBackgammon.png"));

		imageStoneBlack  = new Image(display, BackgammonImages.class.getResourceAsStream("bStone.png"));
		imageStoneWhite  = new Image(display, BackgammonImages.class.getResourceAsStream("wStone.png"));
	}
}
