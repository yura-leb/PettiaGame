package renju.players;

import game.core.IPieceProvider;
import game.core.Move;

public class Malvina extends RenjuPlayer {
	@Override
	public String getName() {
		return "Мальвина";
	}

	@Override
	public String getAuthorName() {
		return "Гуртова Кристина";
	}

	public Malvina(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	public int getMoveWeight(Move m) {
		// TODO Auto-generated method stub
		return 0;
	}
}
