package tools.tourney;

import game.players.IPlayer;

import java.util.List;

/**
 * Матч между двумя игроками
 */
public class Match extends Competition {
    public final int nGames;

    public Match(List<IPlayer> players, int nGames) {
        super(players);
        this.nGames = nGames;
    }

    @Override
    void run() {
    }
}
