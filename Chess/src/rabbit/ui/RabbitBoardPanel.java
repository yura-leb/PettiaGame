package rabbit.ui;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import rabbit.ui.images.RabbitImages;

public class RabbitBoardPanel extends EuropeBoard {
    public RabbitBoardPanel(Composite composite, Game game) {
        super(composite, game.board);
    }

    @Override
    public Image getPieceImage(Piece piece, PieceColor color) {
        return color == PieceColor.WHITE ? RabbitImages.rabbitImage : RabbitImages.wolfImage;
    }
}
