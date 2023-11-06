package game.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Класс для доступа к изображениям общим для всех игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameImages {
	public static Image woodDark;
	public static Image woodLight;
	public static Image woodMedium;
	public static Image papiro;
	public static Image marbl;
	
	public static Image stoneBlack;
	public static Image stoneBlue;
	public static Image stoneGreen;
	public static Image stoneMagenta;
	public static Image stoneRed;
	public static Image stoneWhite;
	public static Image stoneYellow;
	
	public static Image wStone;
	public static Image bStone;
	
	
	/**
	 * Изображения на гранях кубика.
	 */
	private static Image[] cubes;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		woodDark   = new Image(display, GameImages.class.getResourceAsStream("wood_dark.png"));
		woodLight  = new Image(display, GameImages.class.getResourceAsStream("wood_light.png"));
		woodMedium = new Image(display, GameImages.class.getResourceAsStream("wood_medium.png"));
		
		papiro     = new Image(display, GameImages.class.getResourceAsStream("papiro.png"));

		marbl     = new Image(display, GameImages.class.getResourceAsStream("marbl.png"));

		stoneBlack   = new Image(display, GameImages.class.getResourceAsStream("stoneBlack.png"));
		stoneBlue    = new Image(display, GameImages.class.getResourceAsStream("stoneBlue.png"));
		stoneGreen   = new Image(display, GameImages.class.getResourceAsStream("stoneGreen.png"));
		stoneMagenta = new Image(display, GameImages.class.getResourceAsStream("stoneMagenta.png"));
		stoneRed     = new Image(display, GameImages.class.getResourceAsStream("stoneRed.png"));
		stoneYellow  = new Image(display, GameImages.class.getResourceAsStream("stoneYellow.png"));
		stoneWhite   = new Image(display, GameImages.class.getResourceAsStream("stoneWhite.png"));

		wStone  = new Image(display, GameImages.class.getResourceAsStream("wStone.png"));
		bStone   = new Image(display, GameImages.class.getResourceAsStream("bStone.png"));

		cubes = new Image[7];
		cubes[0]   = new Image(display, GameImages.class.getResourceAsStream("cube0.png"));
		cubes[1]   = new Image(display, GameImages.class.getResourceAsStream("cube1.png"));
		cubes[2]   = new Image(display, GameImages.class.getResourceAsStream("cube2.png"));
		cubes[3]   = new Image(display, GameImages.class.getResourceAsStream("cube3.png"));
		cubes[4]   = new Image(display, GameImages.class.getResourceAsStream("cube4.png"));
		cubes[5]   = new Image(display, GameImages.class.getResourceAsStream("cube5.png"));
		cubes[6]   = new Image(display, GameImages.class.getResourceAsStream("cube6.png"));
	}
	
	/**
	 * Для заданного значения выдать изображение этого значения на кубике.
	 * 
	 * @param n - значение
	 * @return изображение значения.
	 */
	static
	public Image getCubeImage(int n) {
		return cubes[n];
	}
} 
