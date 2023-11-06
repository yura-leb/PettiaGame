package tools.tourney.ui;

import static java.awt.Label.CENTER;
import static java.lang.String.format;
import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;
import static tools.tourney.Competition.getResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

import breakthrough.BreakThrough;
import breakthrough.ui.BreakThroughBoardPanel;
import chess.Chess;
import chess.ui.ChessBoardPanel;
import chinachess.ChinaChess;
import chinachess.ui.ChinaChessBoardPanel;
import game.core.Game;
import game.core.GameResult;
import game.players.IPlayer;
import game.ui.AdornedBoard;
import game.ui.GameBoard;
import game.ui.MovesJornal;
import game.ui.images.GameImages;
import renju.Renju;
import renju.ui.RenjuBoardPanel;
import reversi.Reversi;
import reversi.ui.ReversiBoardPanel;
import tamerlan.TamerlanChess;
import tamerlan.ui.TamerlanChessBoardPanel;
import tools.tourney.RoundTourney;
import vikings.Vikings;
import vikings.ui.VikingsBoardPanel;

/**
 * Панель турнира для игры по круговой системе.
 */
class RoundTourneyPanel extends Composite {
    private static final Color COLOR_BLACK = new Color(null, 0, 0, 0);
    private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);
    private static final Color COLOR_GRAY = new Color(null, 100, 100, 100);
    private static final Color COLOR_RED = new Color(null, 255, 0, 0);
    private static final Color COLOR_BLUE = new Color(null, 0, 0, 255);
    private static final Color COLOR_GREEN = new Color(null, 0, 100, 0);

    private static final Color COLOR_SELECT = new Color(null, 255, 215, 0);

    private RoundTourney tourney;
    private static final int CELL_HEIGHT = 25;
    private static final int CELL_WIDTH = CELL_HEIGHT * 2;

    private GamesTable gamesTable;

    private final Composite westPanel;
    private final Composite centerPanel;
    private final Composite eastPanel;

    static private final Map<Class<? extends Game>, Class<? extends GameBoard>> map = new HashMap<>();

    static private Game currentGame;
    static private Label currentGameCell;

    private static Class<? extends Game> currentGameKind;
    private static Class<? extends GameBoard> currentGamePanelKind;
    private static final List<Class<? extends Game>> allGames;

    static {
        map.put(ChinaChess.class, ChinaChessBoardPanel.class);
        map.put(Reversi.class, ReversiBoardPanel.class);
        map.put(BreakThrough.class, BreakThroughBoardPanel.class);
        map.put(Renju.class, RenjuBoardPanel.class);
        map.put(Vikings.class, VikingsBoardPanel.class);
        map.put(Chess.class, ChessBoardPanel.class);
        map.put(TamerlanChess.class, TamerlanChessBoardPanel.class);

//		GamePanel.gamePanelMap.forEach((game, panel) -> {
//			String gameName = game.getSimpleName();
//			String panelName = panel.getSimpleName();
//			Set<String> players = Game.allPlayers.get(game)
//					.stream()
//					.map(IPlayer::getName)
//					.collect(toSet());
//			out.format("%s, %s = %s%n", gameName, panelName, players);
//		});
        allGames = map.keySet()
                .stream()
                .filter(gameKind -> Game.allPlayers.get(gameKind).size() >= 3)
                .collect(Collectors.toList());

        currentGameKind = map.keySet()
                .stream()
                .filter(gameKind -> Game.allPlayers.get(gameKind).size() >= 3)
                .filter(gameKind -> gameKind.getSimpleName().startsWith("China"))
                .findAny()
                .get();
//		currentGameKind = FisherChess.class; // ?
//		currentGameKind = Halma.class; // !

        currentGamePanelKind = map.get(currentGameKind);
    }

    RoundTourneyPanel(Composite parent) {
        super(parent, SWT.BORDER);

        setBackgroundImage(GameImages.woodDark);
        Layout layout = new GridLayout(3, false);
        setLayout(layout);

        westPanel = new Composite(this, SWT.BORDER);
        centerPanel = new Composite(this, SWT.BORDER);
        eastPanel = new Composite(this, SWT.BORDER);

        initPanel(currentGameKind);
    }

    private void initPanel(Class<? extends Game> gameKind) {
        currentGameKind = gameKind;
        currentGamePanelKind = map.get(gameKind);

        this.tourney = new RoundTourney(gameKind);
        this.tourney.run();
        currentGame = tourney.get(0, 1);

        //
        // Левая панель
        //
        GridData data = new GridData(SWT.FILL, SWT.TOP, false, true);

        RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
        rowLayout.center = true;
        westPanel.setLayout(rowLayout);
        westPanel.setLayoutData(data);
        westPanel.setBackground(COLOR_GREEN);

        Combo combo = new Combo(westPanel, SWT.READ_ONLY | SWT.CENTER);
        combo.setBackground(COLOR_WHITE);
        allGames.forEach(g -> combo.add(g.getSimpleName()));
        combo.addSelectionListener(widgetSelectedAdapter(e -> {
            int k = combo.getSelectionIndex();
            Class<? extends Game> gk = RoundTourneyPanel.allGames.get(k);
            selectGameKind(gk);
        }));
        combo.setText(combo.getItem(RoundTourneyPanel.allGames.indexOf(currentGameKind)));

        gamesTable = new GamesTable(westPanel, tourney);

        Button start = new Button(westPanel, SWT.PUSH | SWT.CENTER);
        start.setAlignment(SWT.CENTER);
        start.setText("Старт");
        start.addSelectionListener(widgetSelectedAdapter(e -> startTourney()));

        gameSelected(currentGame);
        westPanel.pack(true);
        layout();
    }

    private void startTourney() {
        this.tourney = new RoundTourney(currentGameKind);
        this.tourney.run();
        currentGame = tourney.get(0, 1);

        gamesTable.initPanel(tourney);
        gamesTable.pack(true);
        gamesTable.layout();

        gameSelected(currentGame);
        westPanel.pack(true);
        layout();
    }

    private void selectGameKind(Class<? extends Game> gameKind) {
        currentGameKind = gameKind;
        currentGamePanelKind = map.get(gameKind);

        tourney = new RoundTourney(gameKind);
        tourney.run();
        currentGame = tourney.get(0, 1);

        gamesTable.initPanel(tourney);

        gameSelected(currentGame);
    }

    public void clear(Composite c) {
        Control[] children = c.getChildren();
        for (int i = children.length - 1; i >= 0; i--)
            children[i].dispose();
    }

    private void gameSelected(Game game) {
        currentGame = game;

        clear(centerPanel);
        clear(eastPanel);
        game.board.deleteObservers();

        //
        // Центральная панель
        //
        GridData centerData = new GridData(SWT.FILL, SWT.FILL, true, true);
        centerData.widthHint = 580;
        centerPanel.setLayout(new FillLayout());
        centerPanel.setLayoutData(centerData);

        AdornedBoard adorned = new AdornedBoard(centerPanel);
        GameBoard boardPanel = getGamePanel(currentGameKind, game);
        adorned.insertSquares(boardPanel);

        //
        // Правая панель
        //
        GridData eastData = new GridData(SWT.LEFT, SWT.FILL, false, true);
        eastData.widthHint = 380;
        eastPanel.setLayout(new FillLayout());
        eastPanel.setLayoutData(eastData);
        new MovesJornal(eastPanel, currentGame.board.history);

        currentGame.board.setBoardChanged();

        centerPanel.pack(true);
        eastPanel.pack(true);
        layout();
    }

    private GameBoard getGamePanel(Class<? extends Game> gameKind, Game game) {
        GameBoard boardPanel = null;
        try {
            Class<? extends GameBoard> panelClass = map.get(gameKind);
            Constructor<?> cons = panelClass.getConstructor(Composite.class, Game.class);
            boardPanel = (GameBoard) cons.newInstance(centerPanel, game);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                 | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return boardPanel;
    }

    /**
     * Пустая ячейка на диагонали таблицы.
     */
    static class EmptyCell extends Composite {
        public EmptyCell(Composite parent) {
            super(parent, SWT.BORDER);

            setForeground(COLOR_BLACK);
            setBackground(COLOR_GRAY);

            GridLayout layout = new GridLayout(1, false);
            setLayout(layout);

            GridData gridData = new GridData();
            gridData.widthHint = CELL_WIDTH;
            gridData.heightHint = CELL_HEIGHT;
            setLayoutData(gridData);
        }
    }

    /**
     * Ячейка для отображения двух игр для каждого игрока: белыми и черными.
     */
    class GameCell extends Composite {
		private final Label whiteGameCell;
        private final Label blackGameCell;

        GameCell(Composite parent, RoundTourney tourney, int row, int col) {
            super(parent, SWT.BORDER);

			Game whiteGame = tourney.get(row, col);
			Game blackGame = tourney.get(col, row);

            setForeground(COLOR_BLACK);
            setBackground(COLOR_WHITE);

            GridLayout layout = new GridLayout(2, true);
            layout.verticalSpacing = 0;
            setLayout(layout);

            GridData gridData = new GridData();
            gridData.widthHint = CELL_WIDTH;
            gridData.heightHint = CELL_HEIGHT;
            setLayoutData(gridData);

            GameResult whiteResult = getResult(whiteGame);
            GameResult blackResult = getResult(blackGame);

            whiteGameCell = new Label(this, SWT.CENTER);
            whiteGameCell.setForeground(resultColor(whiteResult, true));
            whiteGameCell.setText(resultText(whiteResult, true));
            whiteGameCell.setToolTipText(tooltipText(whiteGame));
            whiteGameCell.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseDown(MouseEvent e) {
                    if (currentGameCell != null) {
                        currentGameCell.setBackground(COLOR_WHITE);
                        currentGameCell.update();
                        currentGameCell.redraw();
                    }
                    whiteGameCell.setBackground(COLOR_SELECT);
                    whiteGameCell.update();
                    whiteGameCell.redraw();

                    currentGameCell = whiteGameCell;
                    currentGame = tourney.get(row, col);
                    RoundTourneyPanel.this.gameSelected(currentGame);
                }
            });

            blackGameCell = new Label(this, SWT.CENTER);
            blackGameCell.setForeground(resultColor(blackResult, false));
            blackGameCell.setText(resultText(blackResult, false));
            blackGameCell.setToolTipText(tooltipText(blackGame));
            blackGameCell.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseDown(MouseEvent e) {
                    if (currentGameCell != null) {
                        currentGameCell.setBackground(COLOR_WHITE);
                        currentGameCell.update();
                        currentGameCell.redraw();
                    }
                    blackGameCell.setBackground(COLOR_SELECT);
                    blackGameCell.update();
                    blackGameCell.redraw();

                    currentGameCell = blackGameCell;
                    currentGame = tourney.get(col, row);
                    RoundTourneyPanel.this.gameSelected(currentGame);
                }
            });

            if (whiteGame == currentGame) {
                currentGameCell = whiteGameCell;
                whiteGameCell.setBackground(COLOR_SELECT);
            }
        }
    }

    /**
     * Панель для отображения количества очков набранных игроком.
     */
    static class ResultCell extends Composite {
        ResultCell(Composite parent, RoundTourney tourney, int row) {
            super(parent, SWT.BORDER);
            setForeground(COLOR_BLACK);
            setBackground(COLOR_WHITE);

            IPlayer player = tourney.get(row);

            GridLayout layout = new GridLayout(1, true);
            layout.verticalSpacing = 0;
            setLayout(layout);

            GridData gridData = new GridData();
            gridData.widthHint = CELL_WIDTH;
            gridData.heightHint = CELL_HEIGHT;
            setLayoutData(gridData);

            Label result = new Label(this, SWT.CENTER);
            result.setText("" + tourney.score(row));
            result.setToolTipText(player.getName());
        }
    }

    /**
     * Таблица для отображения соревнования проводимого по круговой системе.
     */
    public class GamesTable extends Composite {
        GamesTable(Composite parent, RoundTourney tourney) {
            super(parent, SWT.NONE);

            initPanel(tourney);
        }

        public void initPanel(RoundTourney tourney) {
            clear(this);
            int nPlayers = tourney.size();

            GridLayout layout = new GridLayout(1 + nPlayers + 1, false);
            layout.horizontalSpacing = 0;
            layout.verticalSpacing = 0;
            setLayout(layout);

            // Верхний левый угол таблицы.
            new Label(this, SWT.NONE);

            // Номера колонок - номера оппонентов игрока.
            for (int k = 0; k < nPlayers; k++) {
                IPlayer player = tourney.get(k);

                Label playerNumber = new Label(this, SWT.CENTER);
                playerNumber.setAlignment(CENTER);
                playerNumber.setForeground(COLOR_WHITE);
                playerNumber.setText("" + (1 + k));
                playerNumber.setToolTipText(player.getName());
            }

            // Колонка для отображения очков набранных игроком.
            Label playersScore = new Label(this, SWT.CENTER);
            playersScore.setForeground(COLOR_WHITE);
            playersScore.setText("Очки");

            // Строки таблицы с результатами игры
            // для игрока записанного в начале строки.
            for (int row = 0; row < nPlayers; row++) {
                IPlayer player = tourney.get(row);

                // Номер игрока в таблице и имя игрока.
                String txt = format("%2d. %s ", 1 + row, player.getName());

                Label name = new Label(this, SWT.LEFT);
                name.setForeground(COLOR_WHITE);
                name.setText(txt);
                name.setToolTipText("Автор алгоритма: " + player.getAuthorName());

                // Результаты игры этого игрока белыми и черными
                // с каждым из игроков в этой таблице.
                for (int col = 0; col < nPlayers; col++) {
                    IPlayer opponent = tourney.get(col);
                    boolean isDiagonal = (player == opponent);

                    if (isDiagonal)
                        new EmptyCell(this);
                    else
                        new GameCell(this, tourney, row, col);
                }

                // Количество очков набранных игроком.
                new ResultCell(this, tourney, row);
            }
        }

    }

    static private String resultText(GameResult gemRes, boolean whitePlay) {
        switch (gemRes) {
            case WHITE_WIN:
                return whitePlay ? " 1 " : " 0 ";
            case BLACK_WIN:
                return whitePlay ? " 0 " : " 1 ";
            case DRAWN:
                return " \u00BD ";
            case UNKNOWN:
                return " Х ";
        }
        return null;
    }

    static private Color resultColor(GameResult gemRes, boolean whitePlay) {
        switch (gemRes) {
            case WHITE_WIN:
                return whitePlay ? COLOR_RED : COLOR_BLUE;
            case BLACK_WIN:
                return whitePlay ? COLOR_BLUE : COLOR_RED;
            case DRAWN:
                return COLOR_RED;
            case UNKNOWN:
                return COLOR_GREEN;
        }
        return null;
    }

    static private String tooltipText(Game game) {
        IPlayer whitePlayer = game.board.getWhitePlayer();
        IPlayer blackPlayer = game.board.getBlackPlayer();

        String players = whitePlayer.getName() + " - " + blackPlayer.getName();
        String authors = whitePlayer.getAuthorName() + " - " + blackPlayer.getAuthorName();

        return format("%s %s (%s)", players, getResult(game), authors);
    }
}
