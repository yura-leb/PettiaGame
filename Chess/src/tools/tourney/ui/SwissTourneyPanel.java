package tools.tourney.ui;

import game.players.IPlayer;
import tools.tourney.SwissTourney;

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
 * Панель турнира для игры по швейцарской системе.
 */
class SwissTourneyPanel extends Composite {
    private static final Color COLOR_GREEN = new Color(null, 0, 100, 0);
    private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);

     SwissTourneyPanel(Composite parent, SwissTourney swissTourney) {
        super(parent, SWT.NONE);

        setBackground(COLOR_GREEN);
        setLayout(new RowLayout(SWT.VERTICAL));

        int nTour = 1;
        List<IPlayer> players = swissTourney.players;

        new TourTable(this, nTour, players);

        // TODO Барзыгин реализовать визуализацию 2-го и последующих туров турнира.

        Button start = new Button(this, SWT.PUSH | SWT.CENTER);
        start.setText("Старт");
        start.addSelectionListener(widgetSelectedAdapter(e ->
                System.out.println("Старт")
        ));

        pack();
    }

    /**
     * Таблица показывает игры очередного тура соревнования проводимого по швейцарской системы.
     */
    static class TourTable extends Composite {
        TourTable(Composite parent, int nTour, List<IPlayer> players) {
            super(parent, SWT.NONE);
            GridLayout layout = new GridLayout(1, true);
            setLayout(layout);

            Group group = new Group(this, SWT.NONE);
            group.setLayout(new GridLayout(1, true));
            group.setForeground(COLOR_WHITE);

            group.setText("Тур " + nTour);

            int nPlayers = players.size();
            for (int k = 0; k < nPlayers - 1; k += 2)
                new PairCell(group, 1 + (k+1)/2, players.get(k), players.get(k + 1));
        }
    }
}

