package linesofaction.ui;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;
import game.ui.images.GameImages;
import game.ui.listeners.MovePieceListener;
import linesofaction.pieces.Stone;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import java.util.HashMap;
import java.util.Map;

public class LinesOfActionBoardPanel extends EuropeBoard {
    private static final Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

    static {
        Map<Class<? extends Piece>, Image> whites = new HashMap<>();
        Map<Class<? extends Piece>, Image> blacks = new HashMap<>();

        pieceImages = new HashMap<>();
        pieceImages.put(PieceColor.WHITE, whites);
        pieceImages.put(PieceColor.BLACK, blacks);

        // Инициализируем карту изображений белых фигур.
        whites.put(Stone.class, GameImages.stoneWhite);

        // Инициализируем карту изображений черных фигур.
        blacks.put(Stone.class, GameImages.stoneBlack);
    }

    public LinesOfActionBoardPanel(Composite composite, Game game) {
        super(composite, game.board);

        listener = new MovePieceListener(this);
    }

    @Override
    public Image getPieceImage(Piece piece, PieceColor color) {
        return pieceImages
                .get(color)
                .get(piece.getClass());
    }
}
