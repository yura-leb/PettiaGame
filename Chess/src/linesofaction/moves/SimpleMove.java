package linesofaction.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;
import linesofaction.pieces.Stone;

public class SimpleMove implements ITransferMove {
    private final Piece piece;

    /**
     * Откуда пошла фигура.
     */
    protected Square source;

    /**
     * Куда пошла фигура.
     */
    protected Square target;

    public SimpleMove(Stone stone, Square... squares) {
        source = squares[0];
        target = squares[1];

        piece = source.getPiece();
    }

    @Override
    public void doMove() throws GameOver {
      // TODO
    }

    @Override
    public void undoMove() {
      // TODO
    }

    @Override
    public Piece getPiece() { return piece; }

    @Override
    public Square getSource() {
        return source;
    }

    @Override
    public Square getTarget() {
        return target;
    }
}
