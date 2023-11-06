package coffee;

import coffee.pieces.Bean;
import game.core.Dirs;
import game.core.Game;
import game.core.IPieceProvider;
import game.players.IPlayer;
import game.players.Vinni;

public class Coffee extends Game {
    private static final IPieceProvider pieceProvider = (square, color) -> new Bean(square, color, Dirs.DOWN);

    static {
        Game.addPlayer(Coffee.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(Coffee.class, new Vinni(pieceProvider));
    }

    @Override
    public void initBoardDefault() {

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider));
    }
}
