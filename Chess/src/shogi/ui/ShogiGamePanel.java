package shogi.ui;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.GamePanel;
import game.ui.WoodBoard;
import game.ui.images.GameImages;
import game.ui.listeners.MovePieceListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import shogi.Shogi;
import shogi.pieces.*;
import shogi.ui.images.ShogiImages;

import java.util.HashMap;
import java.util.Map;

/**
 * Панель для игры в японские шахматы.
 */
public class ShogiGamePanel extends GamePanel {
    public ShogiGamePanel(Composite parent) {
        super(parent, new Shogi());

        insertSquares(new ShogiBoardPanel(this, game), true);

        adorned.updatePieceBoxes();
    }
}

/**
 * Панель для доски японские шахмат.
 */
class ShogiBoardPanel extends WoodBoard {

    private static final Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;
    private static final Map<PieceColor, Map<Class<? extends Piece>, Image>> transformedPieceImages;

    static {
        Map<Class<? extends Piece>, Image> whites = new HashMap<>();
        Map<Class<? extends Piece>, Image> blacks = new HashMap<>();
        Map<Class<? extends Piece>, Image> transformedWhites = new HashMap<>();
        Map<Class<? extends Piece>, Image> transformedBlacks = new HashMap<>();

        pieceImages = new HashMap<>();
        transformedPieceImages = new HashMap<>();
        pieceImages.put(PieceColor.WHITE, whites);
        pieceImages.put(PieceColor.BLACK, blacks);
        transformedPieceImages.put(PieceColor.WHITE, transformedWhites);
        transformedPieceImages.put(PieceColor.BLACK, transformedBlacks);

        // Инициализируем карту изображений белых фигур.
        //
        whites.put(King.class, ShogiImages.wKing);
        whites.put(GeneralGold.class, ShogiImages.wGeneralGold);
        whites.put(GeneralSilver.class, ShogiImages.wGeneralSilver);
        whites.put(Knight.class, ShogiImages.wKnight);
        whites.put(Lance.class, ShogiImages.wLance);
        whites.put(Rook.class, ShogiImages.wRook);
        whites.put(Bishop.class, ShogiImages.wBishop);
        whites.put(Pawn.class, ShogiImages.wPawn);

        // Инициализируем карту изображений черных фигур.
        //
        blacks.put(King.class, ShogiImages.bKing);
        blacks.put(GeneralGold.class, ShogiImages.bGeneralGold);
        blacks.put(GeneralSilver.class, ShogiImages.bGeneralSilver);
        blacks.put(Knight.class, ShogiImages.bKnight);
        blacks.put(Lance.class, ShogiImages.bLance);
        blacks.put(Rook.class, ShogiImages.bRook);
        blacks.put(Bishop.class, ShogiImages.bBishop);
        blacks.put(Pawn.class, ShogiImages.bPawn);

        // Инициализируем карту изображений трансформированных белых фигур.
        //
        transformedWhites.put(Lance.class, ShogiImages.wTLance);
        transformedWhites.put(Pawn.class, ShogiImages.wTPawn);

        // Инициализируем карту изображений трансформированных черных фигур.
        //
        transformedBlacks.put(Lance.class, ShogiImages.bTLance);
        transformedBlacks.put(Pawn.class, ShogiImages.bTPawn);

    }

    public ShogiBoardPanel(Composite composite, Game game) {
        super(composite, game.board, GameImages.woodDark);

        listener = new MovePieceListener(this);

        setPromptColor(new Color(null, 0, 100, 0));
    }

    protected void drawPiece(GC gc, int v, int h, int squareWidth, int squareHeight) {
        ShogiPiece piece = (ShogiPiece) board.getSquare(v, h).getPiece();
        if (piece == null) return;


        int x = v * squareWidth;
        int y = h * squareHeight;

        Image image = getPieceImage(piece, piece.getColor());
        Rectangle bounds = image.getBounds();
        gc.drawImage(image,
                0, 0, bounds.width, bounds.height,
                x, y, squareWidth, squareHeight);
    }

    @Override
    public Image getPieceImage(Piece piece, PieceColor color) {
        ShogiPiece shogiPiece = (ShogiPiece) piece;

        if (!shogiPiece.isTransformed) {
            return pieceImages
                    .get(color)
                    .get(piece.getClass());
        } else {
            return transformedPieceImages
                    .get(color)
                    .get(piece.getClass());
        }

    }


    @Override
    public void mouseUp(MouseEvent e) {
        if (this.getCursor() != null) {
            this.getCursor().dispose();
            this.setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_ARROW));
        }

        super.mouseUp(e);
    }
}
