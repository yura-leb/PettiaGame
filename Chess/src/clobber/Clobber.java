package clobber;

import clobber.pieces.Stone;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
/**
 * TODO Романовская Юлия
 * Правила игры:
 * http://www.iggamecenter.com/info/ru/clobber.html
 */
public class Clobber extends Game {
    static {
        addPlayer(Clobber.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(Clobber.class, new Neznaika());
    }

    public Clobber() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(5, 6);
	    int n = 5;
	    int begin = 0;
	    for (int j = 5; j >= 0; j--) {
	    	for (int i = begin; i < 5; i += 2) { 
	    		new Stone(board.getSquare(i, j), PieceColor.BLACK);
	    		new Stone(board.getSquare(i, n - j), PieceColor.WHITE);
	    	}
	    	if (begin > 0) {
	    		begin = 0;
	    	} else begin = 1;
	    		
	    	
	    }
   
		
	}
}
