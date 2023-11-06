package chess.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Класс для доступа к уникальным изображениям шахматных фигур.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChessImages {
	public static Image icoChess;
	
	public static Image imageKingWhite;
	public static Image imageQueenWhite;
	public static Image imageBishopWhite;
	public static Image imageKnightWhite;
	public static Image imageRookWhite;
	public static Image imagePawnWhite;

	public static Image imageKingBlack;
	public static Image imageQueenBlack;
	public static Image imageBishopBlack;
	public static Image imageKnightBlack;
	public static Image imageRookBlack;
	public static Image imagePawnBlack;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		imageKingBlack   = new Image(display, ChessImages.class.getResourceAsStream("bKingZurich.gif"));
		imageQueenBlack  = new Image(display, ChessImages.class.getResourceAsStream("bQueenZurich.gif"));
		imageBishopBlack = new Image(display, ChessImages.class.getResourceAsStream("bBishopZurich.gif"));
		imageKnightBlack = new Image(display, ChessImages.class.getResourceAsStream("bKnightZurich.gif"));
		imageRookBlack   = new Image(display, ChessImages.class.getResourceAsStream("bRookZurich.gif"));
		imagePawnBlack   = new Image(display, ChessImages.class.getResourceAsStream("bPawnZurich.gif"));
		
		imageKingWhite   = new Image(display, ChessImages.class.getResourceAsStream("wKingZurich.gif"));
		imageQueenWhite  = new Image(display, ChessImages.class.getResourceAsStream("wQueenZurich.gif"));
		imageBishopWhite = new Image(display, ChessImages.class.getResourceAsStream("wBishopZurich.gif"));
		imageKnightWhite = new Image(display, ChessImages.class.getResourceAsStream("wKnightZurich.gif"));
		imageRookWhite   = new Image(display, ChessImages.class.getResourceAsStream("wRookZurich.gif"));
		imagePawnWhite   = new Image(display, ChessImages.class.getResourceAsStream("wPawnZurich.gif"));

		icoChess = imageKnightBlack;
	}  
} 
