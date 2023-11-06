package viruswars;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Vinni;
import viruswars.pieces.Virus;

public class Viruswars extends Game {
    private static final IPieceProvider pieceProvider = (square, color) -> new Virus(square, color);

    static {
        Game.addPlayer(Viruswars.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(Viruswars.class, new Vinni(pieceProvider));
    }
    
    public Viruswars() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider));
    }
    
	@Override
	public void initBoardDefault() {
        super.initBoard(8, 8);

        new Virus(board.getSquare(3, 3), PieceColor.BLACK);
        new Virus(board.getSquare(4, 4), PieceColor.BLACK);

        new Virus(board.getSquare(3, 4), PieceColor.WHITE);
        new Virus(board.getSquare(4, 3), PieceColor.WHITE);

	}
}

