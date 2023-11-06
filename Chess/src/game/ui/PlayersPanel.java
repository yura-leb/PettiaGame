package game.ui;

import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import game.core.Board;
import game.core.Game;
import game.players.IPlayer;

/**
 * Панель выбора игроков для игры.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class PlayersPanel extends Composite {
	private static final Font font = 
			new Font(Display.getCurrent(), "mono", 10, SWT.BOLD | SWT.ITALIC);

	private static final Color WHITE_COLOR   = new Color(null, 255, 255, 255);
	private static final Color BLACK_COLOR   = new Color(null,   0,   0,   0);
	
	private static final Color TITLE_COLOR   = new Color(null, 255, 255,   0);
	private static final Color LIST_COLOR    = new Color(null, 255, 255, 255);
	private static final Color BORDER_COLOR  = new Color(null,   0,   0,   0);
	
	private final Group group;

	/**
	 * Список игроков для заданной игры.
	 */
	private final java.util.List<IPlayer> players;

	/**
	 * Создание панели выбора игроков для игры.
	 * @param parent
	 * @param game
	 */
	public PlayersPanel(Composite parent, Game game) {
		super(parent, SWT.NONE);
		setLayout( new GridLayout(1, true) );
		
		Board board = game.board;

		// Получим всех игроков для этой игры.
		Class<? extends Game> gameClass = game.getClass();
		players = game.getPlayers(gameClass);
		
		// Получим текущих белого и черного игроков для этой игры.
		IPlayer wPlayer = board.getWhitePlayer();
		IPlayer bPlayer = board.getBlackPlayer();
		
		// Получим индексы в списке игроков для текущих 
		// белого и черного игроков этой игры.
		int wPlayerNumber = getPlayerIndex(wPlayer, players);
		int bPlayerNumber = getPlayerIndex(bPlayer, players);
		
		GridData groupData = new GridData(SWT.FILL, SWT.FILL, true, true);
		
		group = new Group(this, SWT.SHADOW_IN);
		group.setText("Игроки");
		group.setForeground(TITLE_COLOR);
		group.setLayout( new GridLayout(1, false) );
		group.setLayoutData(groupData);
	
		// Список для выбора игроков черными фигурами.
		List bList = getPlayersList(bPlayerNumber, "Черные", BLACK_COLOR);
		bList.addListener(SWT.Selection, event -> 
			board.setBlackPlayer( getSelectedPlayer(event) )
		);

		// Список для выбора игроков белыми фигурами.
		List wList = getPlayersList(wPlayerNumber, "Белые", WHITE_COLOR);
		wList.addListener(SWT.Selection, event ->  
			board.setWhitePlayer( getSelectedPlayer(event) )
		);
		
		// Кнопка запуска игры.
		//
		GridData data = new GridData(SWT.CENTER, SWT.TOP, true, false);

		Button startButton = new Button(group, SWT.NONE);
		startButton.setText("Старт");
		startButton.setLayoutData(data);
		startButton.addListener(SWT.Selection, event -> {
			game.initBoardDefault();
			board.startGame();
		});
	}

	/**
	 * Выдать игрока выбранного в списке игроков.
	 * 
	 * @param e
	 *            - событие выбора в списке.
	 * @return выбраный из списка игрок.
	 */
	private IPlayer getSelectedPlayer(Event e) {
		List list = (List) e.widget;
		int selection = list.getSelectionIndices()[0];
		return players.get(selection);
	}

	/**
	 * Выдать управляющий элемент - список игроков.
	 * 
	 * @param playerNumber
	 *            - номер выделенного в списке игрока.
	 * @param titleText
	 *            - текст заголовка списка.
	 * @param titleColor
	 * 
	 * @return управляющий элемент - список игроков.
	 */
	private List getPlayersList(int playerNumber, String titleText, Color titleColor) 
	{
		GridData titleData = new GridData(SWT.FILL, SWT.TOP, true, false);
		
		Label title = new Label(group, SWT.CENTER);
		title.setText(titleText);
		title.setFont(font);
		title.setForeground(titleColor);
		title.setLayoutData(titleData);

		GridData listData = new GridData(SWT.FILL, SWT.TOP, true, false);
		
		List list = new List(group, SWT.BORDER | SWT.SINGLE);
		players.forEach(p -> list.add( p.getName() ));

		list.setForeground(BORDER_COLOR);
		list.setBackground(LIST_COLOR);
		list.setLayoutData(listData);
		list.select(playerNumber);
		
		return list;
	}

	/**
	 * Найти номер игрока в списке игроков.
	 * 
	 * @param player
	 *            - заданный игрок.
	 * @param players
	 *            - список игроков.
	 * 
	 * @return номер найденного игрока.
	 */
	private int getPlayerIndex(IPlayer player, java.util.List<IPlayer> players) {
		Optional<IPlayer> x = 
			players.stream()
				.filter(p -> p.getClass() == player.getClass())
				.findFirst();
		
		return x.isPresent() ? players.indexOf(x.get()) : 0;
	}
}
