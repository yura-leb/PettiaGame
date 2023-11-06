package chess.moves;

import chess.pieces.King;
import chess.pieces.Queen;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;

import java.util.Collections;
import java.util.List;

/**
 * Ход европейских шахмат - преврашение пешки на последней горизонтали
 * в новую фигуру с возможным взятием фигуры противника.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Promotion extends SimpleMove
        implements ICaptureMove {
    private final Piece pawn;
    private Piece capturedPiece;
    private Queen promotedPiece;

    public Promotion(Square[] squares) {
        super(squares);

        pawn = source.getPiece();

        if (source.v != target.v)
            // Ход по диагонали со взятием фигуры.
            capturedPiece = target.getPiece();
    }

    @Override
    public List<Square> getCaptured() {
        return capturedPiece == null
                ? Collections.emptyList() : Collections.singletonList(target);
    }

    /*
     * Удалить пешку, поставить фигуру.
     */
    @Override
    public void doMove() throws GameOver {
        if (capturedPiece != null)
            target.removePiece();

        source.removePiece();
        promotedPiece = new Queen(target, pawn.getColor());

        target.setPiece(promotedPiece);

        if (capturedPiece != null)
            if (capturedPiece instanceof King)
                throw new GameOver(GameResult.lost(capturedPiece));
    }

    /*
     * Удалить фигуру, поставить пешку.
     */
    @Override
    public void undoMove() {
        target.removePiece();
        source.setPiece(piece);

        if (capturedPiece != null)
            target.setPiece(capturedPiece);
    }

    @Override
    public String toString() {
        String movekind = (capturedPiece == null) ? "-" : "x";
        String pieceKind = promotedPiece.toString();

        return "" + piece + source + movekind + target + pieceKind;
    }
}
