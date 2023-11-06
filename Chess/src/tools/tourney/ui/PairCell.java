package tools.tourney.ui;

import game.players.IPlayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Ячейка показывает пару игроков в очередном туре и результат их игры.
 */
class PairCell extends Composite {
    private static final Color COLOR_BLACK = new Color(null, 0, 0, 0);
    private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);

    private static final int CELL_SIZE = 40;

    /**
     * Текст представляющий пару игроков.
     */
    public final Label pair;

    /**
     * Текст представляющий резултат игры пары игроков.
     */
    public final Label result;

    PairCell(Composite parent, int k, IPlayer player1, IPlayer player2) {
        super(parent, SWT.RIGHT);

        GridLayout layout = new GridLayout(2, true);
        setLayout(layout);

        String players = String.format("%2d. %s - %s", k, player1.getName(), player2.getName());
        String authors = String.format("Авторы: %s - %s", player1.getAuthorName(), player2.getAuthorName());

        pair = new Label(this, SWT.LEFT);
        pair.setForeground(COLOR_WHITE);
        pair.setText(players);
        pair.setToolTipText(authors);

        GridData gridData = new GridData();
        gridData.widthHint = CELL_SIZE;

        result = new Label(this, SWT.BORDER | SWT.CENTER);
        result.setLayoutData(gridData);
        result.setBackground(COLOR_BLACK);
        result.setForeground(COLOR_WHITE);
        result.setText(" * ");
    }
}


