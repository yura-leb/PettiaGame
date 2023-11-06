package tools.tourney.ui;

import game.players.IPlayer;
import tools.tourney.Match;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import java.util.List;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

/**
 * Панель для игр матча.
 */
class MatchPanel extends Composite {
    private static final Color COLOR_GREEN = new Color(null, 0, 100, 0);
    private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);

    MatchPanel(Composite parent, Match match) {
        super(parent, SWT.NONE);
        setBackground(COLOR_GREEN);

        setLayout(new RowLayout(SWT.VERTICAL));

        new MatchTable(this, match);

        Button start = new Button(this, SWT.PUSH | SWT.CENTER);
        start.setText("Старт");
        start.addSelectionListener(widgetSelectedAdapter(e ->
                System.out.println("Старт")
        ));

        pack();
    }

    static class MatchTable extends Composite {
        MatchTable(Composite parent, Match match) {
            super(parent, SWT.NONE);
            GridLayout layout = new GridLayout(1, true);
            setLayout(layout);

            Group group = new Group(this, SWT.NONE);
            group.setLayout(new GridLayout(1, true));
            group.setForeground(COLOR_WHITE);

            group.setText("Матч");

            int nGames = match.nGames;

            List<IPlayer> players = match.players;

            for (int k = 0; k < nGames - 1; k++) {
                boolean isOdd = (k % 2 == 0);
                IPlayer player1 = players.get(isOdd ? 0 : 1);
                IPlayer player2 = players.get(isOdd ? 1 : 0);

                new PairCell(group, 1 + k, player1, player2);
            }
        }
    }
}