package tools.tourney.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import game.core.Game;
import game.players.IPlayer;
import tools.tourney.Match;
import tools.tourney.SwissTourney;

/**
 * Панель для проведения соревнований.
 */
public class CompetitionPanel extends Composite {
    private final RoundTourneyPanel roundPanel;
    private final SwissTourneyPanel swissPanel;
    private final MatchPanel matchPanel;

    public CompetitionPanel(Composite parent, List<IPlayer> players) {
        super(parent, SWT.NONE);
        setLayout( new FillLayout(SWT.VERTICAL) );

        TabFolder folder = new TabFolder(this, SWT.TOP);

        roundPanel = new RoundTourneyPanel(folder);
        swissPanel = new SwissTourneyPanel(folder, new SwissTourney(players));
        matchPanel = new MatchPanel(folder, new Match(players, 10));

        TabItem roundTab = new TabItem(folder, SWT.NONE);
        roundTab.setText("Круговая система");
        roundTab.setControl(roundPanel);

        TabItem swissTab = new TabItem(folder, SWT.NONE);
        swissTab.setText("Швейцарская система");
        swissTab.setControl(swissPanel);

        TabItem matchTab = new TabItem(folder, SWT.NONE);
        matchTab.setText("Матч");
        matchTab.setControl(matchPanel);
    }

    public CompetitionPanel(Composite gamesFolder, Class<? extends  Game> gameClass) {
        this(gamesFolder, withoutMan(gameClass));
    }

    /**
     * Удалим из списка игроков (алгоритмов) этой игры человека
     * чтобы можно было тесторовать алгоримы без вмешательства человека.
     * @param gameClass - класс игры
     * @return сописок игроков без человека.
     */
    private static List<IPlayer> withoutMan(Class<? extends  Game>  gameClass) {
        List<IPlayer> iPlayers = Game.allPlayers.get(gameClass);

        List<IPlayer> algorithmsOnly = iPlayers.stream()
                .filter(p -> p.getClass() != IPlayer.HOMO_SAPIENCE.getClass())
                .collect(Collectors.toList());

        return algorithmsOnly;
    }
}