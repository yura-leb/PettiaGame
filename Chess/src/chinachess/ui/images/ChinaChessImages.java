package chinachess.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Класс для доступа к уникальным изображениям шахматных фигур.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChinaChessImages {
	public static Image iconChinaChess;
	
	public static Image imageKingWhite;
	public static Image imageKingBlack;

	public static Image imageGuardWhite;
	public static Image imageGuardBlack;

	public static Image imageRookWhite;
	public static Image imageRookBlack;

	public static Image imagePawnWhite;
	public static Image imagePawnBlack;

	public static Image imageGunWhite;
	public static Image imageGunBlack;

	public static Image imageBishopWhite;
	public static Image imageBishopBlack;

	public static Image imageKnightWhite;
	public static Image imageKnightBlack;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		iconChinaChess   = new Image(display, ChinaChessImages.class.getResourceAsStream("ChinaChess.png"));

		imageKingWhite   = new Image(display, ChinaChessImages.class.getResourceAsStream("wKing.png"));
		imageKingBlack   = new Image(display, ChinaChessImages.class.getResourceAsStream("bKing.png"));

		imageGuardWhite  = new Image(display, ChinaChessImages.class.getResourceAsStream("wGuard.png"));
		imageGuardBlack  = new Image(display, ChinaChessImages.class.getResourceAsStream("bGuard.png"));

		imageRookWhite   = new Image(display, ChinaChessImages.class.getResourceAsStream("wRook.png"));
		imageRookBlack   = new Image(display, ChinaChessImages.class.getResourceAsStream("bRook.png"));

		imagePawnWhite   = new Image(display, ChinaChessImages.class.getResourceAsStream("wPawn.png"));
		imagePawnBlack   = new Image(display, ChinaChessImages.class.getResourceAsStream("bPawn.png"));

		imageGunWhite    = new Image(display, ChinaChessImages.class.getResourceAsStream("wGun.png"));
		imageGunBlack    = new Image(display, ChinaChessImages.class.getResourceAsStream("bGun.png"));

		imageBishopWhite = new Image(display, ChinaChessImages.class.getResourceAsStream("bBishop.png"));
		imageBishopBlack = new Image(display, ChinaChessImages.class.getResourceAsStream("wBishop.png"));

		imageKnightWhite = new Image(display, ChinaChessImages.class.getResourceAsStream("wKnight.png"));
		imageKnightBlack = new Image(display, ChinaChessImages.class.getResourceAsStream("bKnight.png"));
	}  
} 
