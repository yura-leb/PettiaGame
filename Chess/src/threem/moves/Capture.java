package threem.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.Board;
import game.core.PieceColor;
import game.core.GameResult;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Capture extends SimpleMove {
    private Piece captured;

    public Capture(Square[] squares) {
        super(squares);

        captured = target.getPiece();
    }

    private void checkGameFinished(Board board) throws GameOver {
        List<Piece> musketeers = board.getPieces(PieceColor.BLACK);
        List<Integer> enemiesNearMusketeer = new LinkedList<>();

        for (Piece musketeer : musketeers) {
            List<Piece> enemies = board.getPieces(PieceColor.WHITE);
            enemies = enemies.stream()
                    .filter(it -> musketeer.square.isNear(it.square))
                    .filter(it -> musketeer.square.isVertical(it.square) || musketeer.square.isHorizontal(it.square))
                    .collect(Collectors.toList());
            enemiesNearMusketeer.add(enemies.size());
        }

        int nonZeroElements = 0;
        for (Integer count : enemiesNearMusketeer) {
            if (count != 0) {
                nonZeroElements++;
            }
        }
        if (nonZeroElements == 0) {
            throw new GameOver(GameResult.BLACK_WIN);
        }
    }

    @Override
    public void doMove() throws GameOver {
        captured.remove();
        super.doMove();
        checkGameFinished(target.getBoard());
    }

    @Override
    public void undoMove() {
        super.undoMove();
        target.setPiece(captured);
    }

    @Override
    public String toString() {
        return "" + piece + source + "x" + target;
    }
}
