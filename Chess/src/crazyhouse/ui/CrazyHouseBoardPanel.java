package crazyhouse.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import chess.ui.images.ChessImages;
import crazyhouse.pieces.Bishop;
import crazyhouse.pieces.King;
import crazyhouse.pieces.Knight;
import crazyhouse.pieces.Pawn;
import crazyhouse.pieces.Queen;
import crazyhouse.pieces.Rook;
import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;
import crazyhouse.ui.listeners.MovePieceListener;

public class CrazyHouseBoardPanel extends EuropeBoard {
	   private static final Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

		static {
	        Map<Class<? extends Piece>, Image> whites = new HashMap<>();
	        Map<Class<? extends Piece>, Image> blacks = new HashMap<>();

			pieceImages = new HashMap<>();
			pieceImages.put(PieceColor.WHITE, whites);
			pieceImages.put(PieceColor.BLACK, blacks);

			// Инициализируем карту изображений белых фигур.
			//
			whites.put(Pawn.class,   ChessImages.imagePawnWhite);
			whites.put(Rook.class,   ChessImages.imageRookWhite);
			whites.put(Knight.class, ChessImages.imageKnightWhite);
			whites.put(Bishop.class, ChessImages.imageBishopWhite);
			whites.put(Queen.class,  ChessImages.imageQueenWhite);
			whites.put(King.class,   ChessImages.imageKingWhite);
			
			// Инициализируем карту изображений черных фигур.
			//
			blacks.put(Pawn.class,   ChessImages.imagePawnBlack);
			blacks.put(Rook.class,   ChessImages.imageRookBlack);
			blacks.put(Knight.class, ChessImages.imageKnightBlack);
			blacks.put(Bishop.class, ChessImages.imageBishopBlack);
			blacks.put(Queen.class,  ChessImages.imageQueenBlack);
			blacks.put(King.class,   ChessImages.imageKingBlack);
		}

		public CrazyHouseBoardPanel(Composite composite, Game game) {
			super(composite, game.board);
			
			listener = new MovePieceListener(this);
		}


		public Image getPieceImage(Piece piece, PieceColor color) {
			return pieceImages
					.get(color)
					.get( piece.getClass() );
		}

}
