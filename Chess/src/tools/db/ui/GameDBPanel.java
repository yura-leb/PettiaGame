package tools.db.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import chess.Chess;
import chess.ui.ChessBoardPanel;
import game.core.Game;
import game.ui.AdornedBoard;
import game.ui.MovesJornal;

/**
 * Панель для поиска партий в базе данных.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameDBPanel extends Composite {
    private Game game;

    private AdornedBoard board;
    private MovesJornal jornal;
    private GamesListPanel gamesListPanel;
    private GamesSearchPanel gamesSearchPanel;

    public GameDBPanel(Composite parent) {
        super(parent, SWT.TRANSPARENT);
        game = new Chess();

        FillLayout layout = new FillLayout(SWT.VERTICAL);
        layout.spacing = 5;
        setLayout(layout);

        addGamesSearchPanel(this);
        addBoardAndHistory(this, game);
    }

    private void addGamesSearchPanel(GameDBPanel parent) {
        TabFolder folder = new TabFolder(parent, SWT.TOP);

        gamesListPanel = new GamesListPanel(folder);
        gamesSearchPanel = new GamesSearchPanel(folder);

        TabItem searchTab = new TabItem(folder, SWT.NONE);
        searchTab.setText("Поиск");
        searchTab.setControl(gamesSearchPanel);

        TabItem resultTab = new TabItem(folder, SWT.NONE);
        resultTab.setText("Результат");
        resultTab.setControl(gamesListPanel);

    }

    private Composite addBoardAndHistory(Composite parent, Game game) {
        Composite container = new Composite(this, SWT.NONE);
        container.setLayout(new GridLayout(2, false));

        ChessBoardPanel gameBoard = new ChessBoardPanel(container, game);

        board = new AdornedBoard(container);
        board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        board.insertSquares(gameBoard);

        jornal = new MovesJornal(container, gameBoard.board.history);
        jornal.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));

        return container;
    }


    /**
     * Изменить размеры доски.
     *
     * @param nV - количество вертикалей.
     * @param nH - количество горизонталей.
     */
    public void resizeBoard(int nV, int nH) {
        // Новые размеры доски и расстановка фигур.
        game.initBoard(nV, nH);

        board.resize(nV, nH);
    }
}