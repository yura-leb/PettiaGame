package linesofaction;

import game.core.Game;
import game.players.IPlayer;
import game.players.Neznaika;
import linesofaction.pieces.Stone;

import static game.core.PieceColor.BLACK;
import static game.core.PieceColor.WHITE;

/**
 * <a href="https://www.iggamecenter.com/ru/rules/loa">Линии действия</a>
 */
public class LinesOfAction extends Game {
    static {
        addPlayer(LinesOfAction.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(LinesOfAction.class, new Neznaika());
    }

    public LinesOfAction() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(8, 8);

        for (int v = 1; v < 7; v++) {
            new Stone(board.getSquare(v, 0), BLACK);
            new Stone(board.getSquare(v, 7), BLACK);
        }
        for (int h = 1; h < 7; h++) {
            new Stone(board.getSquare(0, h), WHITE);
            new Stone(board.getSquare(7, h), WHITE);
        }
    }
}
