package viruswars.ui.images;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ViruswarsImages {
	public static Image icoViruswars;

	public static Image imageVirusRed;
	public static Image imageVirusBlue;
	public static Image imageZombiRed;
	public static Image imageZombiBlue;
	
	static {
		Device display = Display.getCurrent();

		icoViruswars = new Image(display, ViruswarsImages.class.getResourceAsStream("VirusRed.png"));

		imageVirusRed  = new Image(display, ViruswarsImages.class.getResourceAsStream("VirusRed.png"));
		imageVirusBlue = new Image(display, ViruswarsImages.class.getResourceAsStream("VirusBlue.png"));

		imageZombiRed  = new Image(display, ViruswarsImages.class.getResourceAsStream("ZombiRed.png"));
		imageZombiBlue = new Image(display, ViruswarsImages.class.getResourceAsStream("ZombiBlue.png"));
	}
}

