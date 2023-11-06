package chess.players;

import game.core.Move;

public class Founder extends ChessPlayer {
    @Override
    public String getName() {
        return "Винтик";
    }

    @Override
    public String getAuthorName() {
        return "Лынов Александр";
    }

    @Override
    protected int getWeight(Move m) {
        return 0;
    }
}
