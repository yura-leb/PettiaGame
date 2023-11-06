package tools.db.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class GameDBImages {
	public static Image icoGameDB;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		icoGameDB  = new Image(display, GameDBImages.class.getResourceAsStream("icoGameDB.png"));
	}
}