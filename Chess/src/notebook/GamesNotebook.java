package notebook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import backgammon.ui.BackgammonGamePanel;
import backgammon.ui.images.BackgammonImages;
import breakthrough.ui.BreakThroughGamePanel;
import camelot.ui.CamelotPanel;
import checkers.ui.CheckersGamePanel;
import checkers.ui.images.CheckersImages;
import chess.ui.ChessGamePanel;
import chess.ui.images.ChessImages;
import chinachess.ui.ChinaChessGamePanel;
import chinachess.ui.images.ChinaChessImages;
import clobber.ui.ClobberPanel;
import coffee.ui.CoffeeGamePanel;
import coffee.ui.images.CoffeeImages;
import crazyhouse.ui.CrazyHousePanel;
import fisher.ui.FisherGamePanel;
import game.ui.images.GameImages;
import go.ui.GoGamePanel;
import go.ui.images.GoImages;
import halma.ui.HalmaGamePanel;
import halma.ui.images.HalmaImages;
import kalah.ui.KalahGamePanel;
import kalah.ui.images.KalahImages;
import kilkennycats.ui.KilkennyCatsPanel;
import lines.ui.LinesGamePanel;
import linesofaction.ui.LinesOfActionPanel;
import mingmang.ui.MingMangPanel;
import notebook.ui.images.NotebookImages;
import pettia.ui.PettiaGamePanel;
import points.ui.PointsGamePanel;
import rabbit.ui.RabbitGamePanel;
import rabbit.ui.images.RabbitImages;
import renju.ui.RenjuGamePanel;
import reversi.Reversi;
import reversi.ui.ReversiGamePanel;
import reversi.ui.images.ReversiImages;
import shogi.ui.ShogiGamePanel;
import shogi.ui.images.ShogiImages;
import snakegame.ui.SnakeBoard;
import snakegame.ui.SnakeImages;
import tamerlan.TamerlanChess;
import tamerlan.ui.TamerlanChessGamePanel;
import tamerlan.ui.images.TamerlanChessImages;
import threem.ui.ThreeMusketeersGamePanel;
import threem.ui.images.ThreeMusketeersImages;
import tools.db.ui.GameDBPanel;
import tools.db.ui.images.GameDBImages;
import tools.tourney.ui.CompetitionPanel;
import tshupu.ui.TshuPuGamePanel;
import vikings.ui.VikingsGamePanel;
import vikings.ui.images.VikingImages;
import viruswars.ui.ViruswarsPanel;
import viruswars.ui.images.ViruswarsImages;

/**
 * <b>Блокнот настольных игр.</b></br>
 * </br>
 * <p>
 * Запись и просмотр партии в настольной игре.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GamesNotebook {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);

		shell.setBackgroundMode(SWT.INHERIT_FORCE);

		shell.setSize(1000, 600);
		shell.setText("Games Notebook");
		shell.setImage(NotebookImages.iconNotebook);

		shell.setLayout(new FillLayout());

		TabFolder mainFolder = new TabFolder(shell, SWT.BOTTOM);
		addGames(mainFolder);
		addTools(mainFolder);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose(); // Удалить экземпляр класса после завершения цикла опроса
	}

	private static Image smallIcon(Image image) {
		return new Image(Display.getCurrent(), image.getImageData().scaledTo(20, 20));
	}

	// ============
	// === Игры ===
	// ============

	private static void addGames(TabFolder mainFolder) {
		TabFolder gamesFolder = new TabFolder(mainFolder, SWT.TOP);
		TabItem gamesItem = new TabItem(mainFolder, SWT.NONE);
		gamesItem.setControl(gamesFolder);
		gamesItem.setText("Игры");

		// Добавление вкладок - игр.
//        addCoffeeTab(gamesFolder);

		addPettiaTab(gamesFolder);
		addKilkennyCats(gamesFolder);
		addMingMangTab(gamesFolder);
		addTshuPuTab(gamesFolder);
		addRabbitTab(gamesFolder);
		addChinaChessTab(gamesFolder);
		addThreeMusketeers(gamesFolder);
		addSnakeTab(gamesFolder);
		addViruswarsTab(gamesFolder);
		addCamelotTab(gamesFolder);
		addCrazyHouseTab(gamesFolder);
		addClobberTab(gamesFolder);
		addLinesOfActionTab(gamesFolder);
		addLinesTab(gamesFolder);
		addPointsTab(gamesFolder);
		addRenjuTab(gamesFolder);
		addFisherTab(gamesFolder);
		addBreakThroughTab(gamesFolder);
		addKalahTab(gamesFolder);
		addShogiTab(gamesFolder);
		addBackgammonTab(gamesFolder);
		addChessTab(gamesFolder);
		addCheckersTab(gamesFolder);
		addVikingTab(gamesFolder);
		addTamerlanChessTab(gamesFolder);
		addReversiTab(gamesFolder);
//		addGoTab(gamesFolder);
		addHalma8x8Tab(gamesFolder);
	}

	private static void addPettiaTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new PettiaGamePanel(folder));
		tabItem.setImage(smallIcon(GameImages.stoneGreen));
		tabItem.setText("Pettia");
	}

	private static void addKilkennyCats(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new KilkennyCatsPanel(folder));
		tabItem.setImage(smallIcon(GameImages.stoneBlue));
		tabItem.setText("KilkennyCats");
	}

	private static void addMingMangTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new MingMangPanel(folder));
		tabItem.setImage(smallIcon(GameImages.stoneRed));
		tabItem.setText("MingMang");
	}

	private static void addTshuPuTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new TshuPuGamePanel(folder));
		tabItem.setImage(smallIcon(GameImages.stoneYellow));
		tabItem.setText("TshuPu");
	}

	private static void addRabbitTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new RabbitGamePanel(folder));
		tabItem.setImage(smallIcon(RabbitImages.icoRabbit));
		tabItem.setText("Волки и заяц");
	}

	private static void addThreeMusketeers(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new ThreeMusketeersGamePanel(folder));
		tabItem.setImage(smallIcon(ThreeMusketeersImages.icoThree));
		tabItem.setText("Три мушкетера");
	}

	private static void addSnakeTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new SnakeBoard(folder));
		tabItem.setImage(smallIcon(SnakeImages.iconSnakeNotebook));
		tabItem.setText("Змейка");
	}

	@SuppressWarnings("unused")
	private static void addCoffeeTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new CoffeeGamePanel(folder));
		tabItem.setImage(smallIcon(CoffeeImages.imageBeanBlackHorizontal));
		tabItem.setText("Coffee");
	}

	private static void addViruswarsTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new ViruswarsPanel(folder));
		tabItem.setImage(smallIcon(ViruswarsImages.imageVirusRed));
		tabItem.setText("Viruswars");
	}

	private static void addCamelotTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new CamelotPanel(folder));
		tabItem.setImage(smallIcon(ChessImages.imageQueenBlack));
		tabItem.setText("Camelot");
	}

	private static void addCrazyHouseTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new CrazyHousePanel(folder));
		tabItem.setImage(smallIcon(ChessImages.imageQueenBlack));
		tabItem.setText("CrazyHouse");
	}

	private static void addClobberTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new ClobberPanel(folder));
		tabItem.setImage(smallIcon(GameImages.stoneGreen));
		tabItem.setText("Clobber");
	}

	private static void addLinesOfActionTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new LinesOfActionPanel(folder));
		tabItem.setImage(smallIcon(GameImages.stoneYellow));
		tabItem.setText("Линии действия");
	}

	private static void addLinesTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new LinesGamePanel(folder));
		tabItem.setImage(smallIcon(GameImages.stoneRed));
		tabItem.setText("Линии");
	}

	private static void addPointsTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new PointsGamePanel(folder));
		tabItem.setImage(smallIcon(GoImages.icoGo));
		tabItem.setText("Точки");
	}

	private static void addRenjuTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new RenjuGamePanel(folder));
		tabItem.setImage(smallIcon(GoImages.icoGo));
		tabItem.setText("Рендзю");
	}

	private static void addFisherTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new FisherGamePanel(folder));
		tabItem.setImage(smallIcon(ChessImages.imageKingBlack));
		tabItem.setText("Фишер");
	}

	private static void addBreakThroughTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new BreakThroughGamePanel(folder));
		tabItem.setImage(smallIcon(ChessImages.imagePawnBlack));
		tabItem.setText("Прорыв");
	}

	private static void addKalahTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new KalahGamePanel(folder));
		tabItem.setImage(smallIcon(KalahImages.icoKalah));
		tabItem.setText("Калах");
	}

	private static void addShogiTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new ShogiGamePanel(folder));
		tabItem.setImage(smallIcon(ShogiImages.icoShogi));
		tabItem.setText("Сеги");
	}

	private static void addBackgammonTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new BackgammonGamePanel(folder));
		tabItem.setImage(smallIcon(BackgammonImages.iconBackgammon));
		tabItem.setText("Нарды");
	}

	private static void addTamerlanChessTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new TamerlanChessGamePanel(folder));
		tabItem.setImage(smallIcon(TamerlanChessImages.iconTamerlanChess));
		tabItem.setText("Тамерлан");
	}

	private static void addCheckersTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new CheckersGamePanel(folder));
		tabItem.setImage(smallIcon(CheckersImages.iconCheckers));
		tabItem.setText("Шашки");
	}

	private static void addChinaChessTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new ChinaChessGamePanel(folder));
		tabItem.setImage(smallIcon(ChinaChessImages.iconChinaChess));
		tabItem.setText("Сянци");
	}

	private static void addChessTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new ChessGamePanel(folder));
		tabItem.setImage(smallIcon(ChessImages.icoChess));
		tabItem.setText("Шахматы");
	}

	private static void addVikingTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new VikingsGamePanel(folder, 9));
		tabItem.setImage(smallIcon(VikingImages.icoVikings9));
		tabItem.setText("Викинги");
	}

	private static void addReversiTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new ReversiGamePanel(folder, 0));
		tabItem.setImage(smallIcon(ReversiImages.icoReversi));
		tabItem.setText("Реверси");
	}

	@SuppressWarnings("unused")
	private static void addGoTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new GoGamePanel(folder, 8));
		tabItem.setImage(smallIcon(GoImages.icoGo));
		tabItem.setText("Го");
	}

	private static void addHalma8x8Tab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new HalmaGamePanel(folder, 8));
		tabItem.setImage(smallIcon(HalmaImages.icoHalma));
		tabItem.setText("Халма 8x8");
	}

	// ===================
	// === Инструменты ===
	// ===================

	private static void addTools(TabFolder mainFolder) {
		TabFolder toolsFolder = new TabFolder(mainFolder, SWT.TOP);
		TabItem toolsItem = new TabItem(mainFolder, SWT.NONE);
		toolsItem.setControl(toolsFolder);
		toolsItem.setText("Инструменты");

		// Добавление вкладок - инструментов.
//        addEditorTab(toolsFolder);
//		addGameDBTab(toolsFolder);
		addCompetitionTamerlanTab(toolsFolder);
		addCompetitionReversiTab(toolsFolder);
	}

	@SuppressWarnings("unused")
	private static void addGameDBTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new GameDBPanel(folder));
		tabItem.setImage(smallIcon(GameDBImages.icoGameDB));
		tabItem.setText("База игр");
	}

	private static void addCompetitionReversiTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new CompetitionPanel(folder, Reversi.class));
		tabItem.setImage(smallIcon(ReversiImages.icoReversi));
		tabItem.setText("Турниры");
	}

	private static void addCompetitionTamerlanTab(TabFolder folder) {
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setControl(new CompetitionPanel(folder, TamerlanChess.class));
		tabItem.setImage(smallIcon(TamerlanChessImages.iconTamerlanChess));
		tabItem.setText("Турниры");
	}
}