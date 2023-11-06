package coffee.ui;

import coffee.pieces.Bean;
import coffee.ui.images.CoffeeImages;
import game.core.*;
import game.ui.GreenBoard;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class CoffeeBoardPanel extends GreenBoard implements IPieceProvider {
    public CoffeeBoardPanel(Composite composite, Game game) {
        super(composite, game.board);

        // Слушатель мыши для постановки новой фигуры на доску.
        listener = new PutPieceListener(this, game);

        // Слушатель мыши для отрисовки подсказки на доске -
        // можно ли ставить фигуру на клетку на доски.
        mouseMoveListener = PutPiecePromptListener.EMPTY;
    }

    @Override
    public Piece getPiece(Square square, PieceColor color) {
        return new Bean(square, color, Dirs.DOWN);
    }

    @Override
    public Image getPieceImage(Piece piece, PieceColor color) {
        Bean bean = (Bean) piece;

        switch (bean.dir) {
            case DOWN:
            case UP:
                return color == PieceColor.WHITE ? CoffeeImages.imageBeanWhiteVertical
                                                 : CoffeeImages.imageBeanBlackVertical;
            case LEFT:
            case RIGHT:
                return color == PieceColor.WHITE ? CoffeeImages.imageBeanWhiteHorizontal
                                                 : CoffeeImages.imageBeanBlackHorizontal;
            default:
                return null;
        }
    }
}
