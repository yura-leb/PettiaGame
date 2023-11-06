package breakthrough.players;

import game.core.Move;

/**
 * TODO Король Д.С. - реализовать алгоритм
 * <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%B5%D1%80%D0%BC%D0%B5%D1%81"></a>Гермес</a> -
 * бог торговли и счастливого случая, хитрости и воровства, юношества и красноречия.
 * Покровитель глашатаев, послов, пастухов, путников.
 */
public class Hermes extends BreakThroughPlayer {

    @Override
    public String getName() {
        return "Гермес";
    }

    @Override
    public String getAuthorName() {
        return "Король Д.С.";
    }

    @Override
    protected int getWeight(Move m) {
        return 0;
    }
}
