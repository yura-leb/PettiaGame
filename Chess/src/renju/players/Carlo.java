package renju.players;

import game.core.IPieceProvider;
import game.core.Move;

public class Carlo extends RenjuPlayer {
	@Override
	public String getName() {
		return "Папа Карло";
	}

	@Override
	public String getAuthorName() {
		return "Королев Борис";
	}

	public Carlo(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	public int getMoveWeight(Move m) {
		// TODO Auto-generated method stub
		return 0;
	}
}
