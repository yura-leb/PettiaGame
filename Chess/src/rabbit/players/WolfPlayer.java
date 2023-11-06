package rabbit.players;

import game.core.Move;

public class WolfPlayer extends WolfRabbitPlayer {
    @Override
    public String getName() {
        return "Волк";
    }

    @Override
    public String getAuthorName() {
        return "Зяблицева";
    }

    @Override
    int getWeight(Move m2) {
        return 0;
    }
}
