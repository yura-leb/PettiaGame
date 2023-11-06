package rabbit.players;

import game.core.Move;

public class RabbitPlayer extends WolfRabbitPlayer {
    @Override
    public String getName() {
        return "Заяц";
    }

    @Override
    public String getAuthorName() {
        return "Зяблицева";
    }

    @Override
    int getWeight(Move m) {
        return 0;
    }
}
