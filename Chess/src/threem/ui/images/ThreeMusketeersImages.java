package threem.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ThreeMusketeersImages {
	public static Image icoThree;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		icoThree  = new Image(display, ThreeMusketeersImages.class.getResourceAsStream("mech.png"));
	}
}
