package tools.tourney.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class TourneyImages {
	public static Image icoCompetition;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		icoCompetition  = new Image(display, TourneyImages.class.getResourceAsStream("icoGameDB.png"));
	}
}