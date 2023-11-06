package vikings.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Изображения для игры Викинги.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class VikingImages {
	public static Image icoVikings9;
	public static Image icoVikings11;

	public static Image imageCyningBlack;
	public static Image imageVikingBlack;
	
	public static Image imageCyningWhite;
	public static Image imageVikingWhite;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		icoVikings9  = new Image(display, VikingImages.class.getResourceAsStream("icoVikings9.png"));
		icoVikings11 = new Image(display, VikingImages.class.getResourceAsStream("icoVikings11.png"));

		imageCyningBlack = new Image(display, VikingImages.class.getResourceAsStream("bСyning.png"));
		imageVikingBlack  = new Image(display, VikingImages.class.getResourceAsStream("bViking.png"));
		
		imageCyningWhite = new Image(display, VikingImages.class.getResourceAsStream("wСyning.png"));
		imageVikingWhite  = new Image(display, VikingImages.class.getResourceAsStream("wViking.png"));
	}
}
