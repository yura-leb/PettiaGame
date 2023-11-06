package camelot.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import camelot.pieces.Knight;
import camelot.pieces.Pawn;
import chess.ui.images.ChessImages;
import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.WoodBoard;
import game.ui.images.GameImages;

public class CamelotBoardPanel extends WoodBoard {
	private static final Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

	static {
		Map<Class<? extends Piece>, Image> whites = new HashMap<>();
		Map<Class<? extends Piece>, Image> blacks = new HashMap<>();

		pieceImages = new HashMap<>();
		pieceImages.put(PieceColor.WHITE, whites);
		pieceImages.put(PieceColor.BLACK, blacks);

		// Инициализируем карту изображений белых фигур.
		//
		whites.put(Pawn.class, ChessImages.imagePawnWhite);
		whites.put(Knight.class, ChessImages.imageKnightWhite);

		// Инициализируем карту изображений черных фигур.
		//
		blacks.put(Pawn.class, ChessImages.imagePawnBlack);
		blacks.put(Knight.class, ChessImages.imageKnightBlack);
	}

	public CamelotBoardPanel(Composite composite, Game game) {
		super(composite, game.board, GameImages.woodLight);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return pieceImages.get(color).get(piece.getClass());
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int sw, int sh) {
		Square theSquare = board.getSquare(v, h);

		if (isCorner(theSquare))
			return;
		if (nearCastle(theSquare))
			return;

		gc.setForeground(LINE_COLOR);
		gc.drawRectangle(v * sw, h * sh, sw, sh);

		gc.setBackground(LINE_COLOR);
		if (isCastle(theSquare))
			gc.fillRectangle(v * sw + sw / 4, h * sh + sh / 4, sw / 2, sh / 2);
	}

	private boolean isCorner(Square theSquare) {
		List<Square> corners = board.getCorners();

		return corners.stream().anyMatch(s -> s.distance(theSquare) < 3);
	}

	private boolean nearCastle(Square s) {
		if (s.h == 0 && (s.v == 3 || s.v == 4 || s.v == 7 || s.v == 8))
			return true;
		if (s.h == board.nH - 1 && (s.v == 3 || s.v == 4 || s.v == 7 || s.v == 8))
			return true;

		return false;
	}

	private boolean isCastle(Square s) {
		if (s.h == 0 && (s.v == 5 || s.v == 6))
			return true;
		if (s.h == board.nH - 1 && (s.v == 5 || s.v == 6))
			return true;

		return false;
	}
}
