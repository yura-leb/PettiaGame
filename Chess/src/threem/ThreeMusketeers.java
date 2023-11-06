package threem;

import static game.core.PieceColor.BLACK;
import static game.core.PieceColor.WHITE;

import game.core.Game;
import game.core.Square;
import game.players.IPlayer;
import game.players.Neznaika;
import threem.pieces.Enemy;
import threem.pieces.Musketeer;

/**
 *
 * TODO Petrova реализовать правиля игры
 * <a href="https://wiki5.ru/wiki/Three_Musketeers_(game)">Три Мушкетера</a>
 */
public class ThreeMusketeers extends Game {
	static {
		addPlayer(ThreeMusketeers.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(ThreeMusketeers.class, new Neznaika());
	}
	
    public ThreeMusketeers() {
        initBoardDefault();
        
        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());

        board.changeMoveColor();
    }
    
    @Override
    public void initBoardDefault() {
        super.initBoard(5, 5);

		board.getSquares().forEach(s -> {  
			if (isForBlack(s)) new Musketeer(s, BLACK); else new Enemy(s, WHITE);
		});
    }
    
    boolean isForBlack(Square s) {
    	if (s.h == 2 && s.v == 2) return true;
    	if (s.h == 4 && s.v == 0) return true;
    	if (s.h == 0 && s.v == 4) return true;

    	return false;
    }
}
