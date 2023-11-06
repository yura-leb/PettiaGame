package shogi.ui.images;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

/**
 * Изображения для игры в <a href="http://shogi.by/shogi/1/#zones">Cёги</a>
 */
public class ShogiImages {
	public static Image icoShogi;

	public static Image imageWoodUp;
	public static Image imageWoodDown;

	public static Image wKing;
	public static Image wGeneralGold;
	public static Image wGeneralSilver;
	public static Image wKnight;
	public static Image wLance;
	public static Image wBishop;
	public static Image wRook;
	public static Image wPawn;
	
	public static Image wTPawn;
	public static Image wTLance;

	public static Image bKing;
	public static Image bGeneralSilver;
	public static Image bGeneralGold;
	public static Image bKnight;
	public static Image bLance;
	public static Image bBishop;
	public static Image bRook;
	public static Image bPawn;
	public static Image bTPawn;
	public static Image bTLance;

	private static Image imageKing;
	private static Image imageGeneralGold;
	private static Image imageGeneralSilver;
	private static Image imageKnight;
	private static Image imageLance;
	private static Image imageBishop;
	private static Image imageRook;
	private static Image imagePawn;
	private static Image imageTPawn;
	private static Image imageTLance;

	static {
		Device display = Display.getCurrent();

		icoShogi = new Image(display, ShogiImages.class.getResourceAsStream("icoShogi.png"));

		imageWoodUp   = new Image(display, ShogiImages.class.getResourceAsStream("wood_up_m.png"));
		imageWoodDown = new Image(display, ShogiImages.class.getResourceAsStream("wood_down_m.png"));

		imageKing          = new Image(display, ShogiImages.class.getResourceAsStream("ShogiKing.jpg"));
		imageGeneralGold   = new Image(display, ShogiImages.class.getResourceAsStream("ShogiGeneralGold.png"));
		imageGeneralSilver = new Image(display, ShogiImages.class.getResourceAsStream("ShogiGeneralSilver.png"));
		imageKnight        = new Image(display, ShogiImages.class.getResourceAsStream("ShogiKnight.png"));
		imageLance         = new Image(display, ShogiImages.class.getResourceAsStream("ShogiLance.png"));
		imageBishop        = new Image(display, ShogiImages.class.getResourceAsStream("ShogiBishop.jpg"));
		imageRook          = new Image(display, ShogiImages.class.getResourceAsStream("ShogiRook.png"));
		imagePawn          = new Image(display, ShogiImages.class.getResourceAsStream("ShogiPawn.jpg"));
		
		//Трансформированные
		
		imageTPawn = new Image(display, ShogiImages.class.getResourceAsStream("ShogiTransformedPawn.jpg"));
		imageTLance         = new Image(display, ShogiImages.class.getResourceAsStream("ShogiTransformedLance.jpg"));

		wKing          = merge(imageWoodUp, imageKing);
		wGeneralGold   = merge(imageWoodUp, imageGeneralGold);
		wGeneralSilver = merge(imageWoodUp, imageGeneralSilver);
		wKnight        = merge(imageWoodUp, imageKnight);
		wLance         = merge(imageWoodUp, imageLance);
		wBishop        = merge(imageWoodUp, imageBishop);
		wRook          = merge(imageWoodUp, imageRook);
		wPawn          = merge(imageWoodUp, imagePawn);
		wTPawn          = merge(imageWoodUp, imageTPawn);
		wTLance         = merge(imageWoodUp, imageTLance);

		bKing          = merge(imageWoodDown, imageKing);
		bGeneralGold   = merge(imageWoodDown, imageGeneralGold);
		bGeneralSilver = merge(imageWoodDown, imageGeneralSilver);
		bKnight        = merge(imageWoodDown, imageKnight);
		bLance         = merge(imageWoodDown, imageLance);
		bBishop        = merge(imageWoodDown, imageBishop);
		bRook          = merge(imageWoodDown, imageRook);
		bPawn          = merge(imageWoodDown, imagePawn);
		bTPawn          = merge(imageWoodDown, imageTPawn);
		bTLance         = merge(imageWoodDown, imageLance);
		
	}	

	private static Image merge(Image image1, Image image2) {
		Rectangle bounds1 = image1.getBounds();
		Rectangle bounds2 = image2.getBounds();

		int width = Math.max(bounds1.width, bounds2.width);
		int height = Math.max(bounds1.height, bounds2.height);

		Image paper = new Image(Display.getCurrent(), width, height);
	    Image glass = makeTransparent(paper);
	    
	    Image transpatent1 = makeTransparent(image1);
	    Image transpatent2 = makeTransparent(image2);
	    
	    int d = width/8;
		GC gc = new GC(glass);
		
		try {
			gc.setAntialias(SWT.ON);

			gc.drawImage(transpatent1, 
					0, 0, bounds1.width, bounds1.height, 
					0, 0, width, height);
			
			gc.drawImage(transpatent2, 
					0, 0, bounds2.width, bounds2.height, 
					d, d, width-2*d, height-2*d);
		} finally {
			gc.dispose();
		}

		return glass;
	}

	private static Image makeTransparent(Image image) {
		ImageData imageData = image.getImageData();
	    imageData.transparentPixel = imageData.getPixel(0, 0);
	    
	    return new Image(Display.getCurrent(), imageData);
	}
}
