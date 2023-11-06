package reversi;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.PieceColor;
import game.core.Square;
import game.players.IPlayer;
import game.players.Vinni;
import reversi.pieces.Hole;
import reversi.pieces.Stone;
import reversi.players.Ia;
import reversi.players.Owl;
import reversi.players.Rabbit;
import reversi.players.Tiger;

/**
 * Игра
 * <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Reversi extends Game {
    private static final IPieceProvider pieceProvider = (square, color) -> new Stone(square, color);

    static {
        Game.addPlayer(Reversi.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(Reversi.class, new Vinni(pieceProvider));
        Game.addPlayer(Reversi.class, new Ia(pieceProvider));
        Game.addPlayer(Reversi.class, new Owl(pieceProvider));
        Game.addPlayer(Reversi.class, new Tiger(pieceProvider));
        Game.addPlayer(Reversi.class, new Rabbit(pieceProvider));

    }

    /**
     * Вернуть инициализированную доску для игры в реверси.
     *
     * @param nHoles - количество случайно расположенных отверстий.
     */
    public Reversi() {
        initBoardDefault();
        int nHoles = 0;
        
        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider));

        if (nHoles != 0) {
            int randomV = (int) (8 * Math.random());
            int randomH = (int) (8 * Math.random());

            Square randomSquare = board.getSquare(randomV, randomH);
            new Hole(randomSquare, PieceColor.BLACK);
        }
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(8, 8);

        new Stone(board.getSquare(3, 3), PieceColor.BLACK);
        new Stone(board.getSquare(4, 4), PieceColor.BLACK);

        new Stone(board.getSquare(3, 4), PieceColor.WHITE);
        new Stone(board.getSquare(4, 3), PieceColor.WHITE);
    }
}
