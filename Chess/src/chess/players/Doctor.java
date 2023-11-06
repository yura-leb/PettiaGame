package chess.players;

import game.core.Move;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;
import tamerlan.pieces.King;

public class Doctor extends ChessPlayer {
    @Override
    public String getName() {
        return "Пилюлькин";
    }

    @Override
    public String getAuthorName() {
        return "???";
    }

    @Override
    protected int getWeight(Move m) {
        ITransferMove move = (ITransferMove) m;

        Square target = move.getTarget();

        Piece enemy = target.getPiece();
        if (enemy != null)
            if (enemy instanceof King)
                return 1000;

        return 0;
    }
}

