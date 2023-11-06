package tshupu;

import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import tshupu.pieces.TshuPuPiece;

public class TshuPu extends Game {
    private static final int BOARD_SIZE = 9;


    static {
        Game.addPlayer(TshuPu.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(TshuPu.class, new Neznaika());

    }

    public TshuPu() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setWhitePlayer(new Neznaika());
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(BOARD_SIZE, BOARD_SIZE);
        
        new TshuPuPiece(board.getSquare(4, 0), PieceColor.WHITE);
        new TshuPuPiece(board.getSquare(4, 8), PieceColor.BLACK);
        new TshuPuPiece(board.getSquare(8, 4), PieceColor.GREEN);
        new TshuPuPiece(board.getSquare(0, 4), PieceColor.BLUE);
    }
}
