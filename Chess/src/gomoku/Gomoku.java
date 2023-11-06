package gomoku;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Vinni;
import gomoku.pieces.Stone;


public class Gomoku extends Game {
    private static final IPieceProvider pieceProvider = (square, color) -> new Stone(square, color);

    static {
        Game.addPlayer(Gomoku.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(Gomoku.class, new Vinni(pieceProvider, 19*19));

    }

    public Gomoku(int boardSize) {
        initBoardDefault();
        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider, boardSize * boardSize));
    }

    @Override
    public void initBoard(int nV, int nH) {
        super.initBoard(nV, nH);
        new gomoku.pieces.Stone(board.getSquare(nV/2, nH/2), PieceColor.BLACK);
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(15, 15);
        new gomoku.pieces.Stone(board.getSquare(7, 7), PieceColor.BLACK);
    }
}
