package threem.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import threem.moves.Capture;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Musketeer extends Piece {
    public Musketeer(Square s, PieceColor color) {
        super(s, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square mySquare = this.square;
        List<Square> moves = Arrays.stream(squares)
                .filter(square -> !square.isEmpty())
                .filter(square -> square.getPiece().isEnemy(mySquare.getPiece()))
                .filter(square -> square.isHorizontal(mySquare) || square.isVertical(mySquare))
                .filter(mySquare::isNear)
                .collect(Collectors.toList());
        return !moves.isEmpty();
    }

    @Override
    public Move makeMove(Square... squares) {
        return new Capture(squares);
    }

    @Override
    public String toString() {
        return "";
    }
}
