package gomoku.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class GomokuImages {
	public static Image icoGomoku;

	public static Image imageStoneBlack;
	public static Image imageStoneWhite;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		icoGomoku = new Image(display, GomokuImages.class.getResourceAsStream("icoGomoku.png"));

		imageStoneBlack  = new Image(display, GomokuImages.class.getResourceAsStream("bStone.png"));
		imageStoneWhite  = new Image(display, GomokuImages.class.getResourceAsStream("wStone.png"));
	}
}
