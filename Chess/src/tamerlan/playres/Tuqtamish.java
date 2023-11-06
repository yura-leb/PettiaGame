package tamerlan.playres;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;
import tamerlan.pieces.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Тохтамыш - хан Золотой Орды.
 */
public class Tuqtamish extends TamerlanChessPlayer {
    private final Comparator<? super Move> brain
            = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

    @Override
    public String getName() {
        return "Тохтамыш (З.Орда)";
    }

    @Override
    public String getAuthorName() {
        return "???";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    Comparator<? super Move> getComparator() {
        return brain;
    }

    /**
     * mean pieces weights
     * 'pawn': 1,
     * 'knight': 3.5,
     * 'bishop': 3,
     * 'rook': 5,
     * 'queen': 9,
     * 'giraf': 12.5,
     * 'visir': 6.5,
     * 'warmachine': 8.5
     *
     * @param piece фигура в шахматах Тамерлана.
     * @return Средний вес фигуры
     */
    private int getCusPieWeight(Piece piece, boolean unShift) {
        // У захвата короля врага наивысший приоритет.
        if (piece instanceof King)
            return 1100;
        double[] values = {12.5, 9, 8.5, 6.5, 5, 3.5, 3, 1};
        int[] values_correct = new int[8];
        for (int i = 0; i < values.length; i++) {
            if (unShift) values_correct[i] = (int) values[i] * 5;
            else values_correct[i] = (int) (870 + values[i] * 5);
        }
        if (piece instanceof Giraffe) return values_correct[0];
        if (piece instanceof Queen) return values_correct[1];
        if (piece instanceof WarMachine) return values_correct[2];
        if (piece instanceof Vizir) return values_correct[3];
        if (piece instanceof Rook) return values_correct[4];
        if (piece instanceof Knight) return values_correct[5];
        if (piece instanceof Bishop) return values_correct[6];
        return values_correct[7];
    }

    private King getFriendKing(Piece piece) {
        for (Piece p :
                piece.getFriends()) {
            if (p instanceof King) {
                return (King) p;
            }
        }
        return null;
    }

    private ArrayList<Square> isEnemyCanEatKing(Piece piece) {
        PieceColor color;
        
        if (piece.isWhite()) color = PieceColor.BLACK;
        else color = PieceColor.WHITE;
        
        List<Move> correct_Moves = getCorrectMoves(piece.square.getBoard(), color);
        ArrayList<Square> dangerous_Moves = new ArrayList<>();
        
        for (Move m : correct_Moves) {
            if (m instanceof ICaptureMove) {
                ICaptureMove capture = (ICaptureMove) m;
                if (capture.getCaptured().get(0).getPiece() instanceof King)
                    dangerous_Moves.add(((ICaptureMove) m).getCaptured().get(0));
            }
        }

        return dangerous_Moves;
    }

    /**
     * Задать вес для хода.
     *
     * @param move - ход
     * @return оценка хода.
     */
    private int getMoveWeight(Move move) {
        ITransferMove transfer = (ITransferMove) move;

        Square source = transfer.getSource();
        Square target = transfer.getTarget();
        Piece thePiece = source.getPiece();

        if (move instanceof ICaptureMove) {
            // Ход - взятие фигуры врага.
            ICaptureMove capture = (ICaptureMove) move;

            // captured
            Square capturedSquare = capture.getCaptured().get(0);
            Piece capturedPiece = capturedSquare.getPiece();
            // source
            Piece sourcePiece = source.getPiece();


            int base = getCusPieWeight(capturedPiece, false);

            // bigger piece eat small piece
            if (getCusPieWeight(capturedPiece, false) > getCusPieWeight(sourcePiece, false)) {
                base += 7 * (getCusPieWeight(capturedPiece, false) - getCusPieWeight(sourcePiece, false));
            }

            ArrayList<Square> dangerousMoves = isEnemyCanEatKing(thePiece);
            if (dangerousMoves.contains(capturedSquare)) base += 1000;

            King my_king = getFriendKing(thePiece);
            assert my_king != null;
            base += 7 * (MAX_DISTANCE - distance(target, my_king.square));

            return base;
        }

        // Из всех ходов без взятия фигуры врага лучший ход
        // который максимально приближает к королю врага.
        King enemyKing = getEnemyKing(thePiece);
        int stepWeight = MAX_DISTANCE - distance(target, enemyKing.square);

        return stepWeight;
    }
}