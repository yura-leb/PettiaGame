package kalah.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class KalahImages {
	public static Image icoKalah = new Image(Display.getCurrent(), KalahImages.class.getResourceAsStream("icoKalah.png"));

	public static Image ball = new Image(Display.getCurrent(), KalahImages.class.getResourceAsStream("wStone.png"));
}
