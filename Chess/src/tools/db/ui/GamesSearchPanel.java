package tools.db.ui;

import java.sql.*;
import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import tools.db.resource.DBUtil;

/**
 * Панель для задания параметров поиска в базе игр.
 */
public class GamesSearchPanel extends Composite {
    private static final Color COLOR_BLACK = new Color(null, 0, 0, 0);
    private static final Color COLOR_GREEN = new Color(null, 0, 100, 0);
    private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);

    private Map<String, List<String>> players;
    private List<String> years;

    private CCombo whitePlayers;
    private CCombo blackPlayers;
    private CCombo year;
    private Button searchButton;

    public GamesSearchPanel(Composite parent) {
        super(parent, SWT.DM_FILL_NONE);
        setBackground(COLOR_GREEN);
        setLayout(new RowLayout(SWT.HORIZONTAL));

        players = getAvailablePlayers();
        years = setYears();

        whitePlayers = addSelectionList(this, "Белые", players.get("WHITE"));
        whitePlayers.addListener(SWT.DefaultSelection, e -> {
        });

        blackPlayers = addSelectionList(this, "Черные", players.get("BLACK"));
        blackPlayers.addListener(SWT.DefaultSelection, e -> {
        });

        year = addSelectionList(this, "Год", years);
        year.addListener(SWT.DefaultSelection, e -> {
        });


        searchButton = new Button(this, SWT.SEARCH);
        searchButton.setText("Найти");
        searchButton.addListener(SWT.Selection, event -> System.out.println("Button pressed"));

    }

    private CCombo addSelectionList(Composite parent, String title, List<String> list) {
        Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
        group.setForeground(COLOR_WHITE);
        group.setLayout(new GridLayout());
        group.setText(title);

        CCombo combo = new CCombo(group, SWT.READ_ONLY | SWT.FLAT | SWT.BORDER);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        combo.setText(list.get(0));
        combo.setForeground(COLOR_BLACK);
        combo.setBackground(COLOR_WHITE);

        list.forEach(combo::add);

        return combo;
    }

    private Map<String, List<String>> getAvailablePlayers() {
        Connection connection;
        Map<String, List<String>> playerMap = new HashMap<>();

        try {
            List<String> playerList = new ArrayList<>();
            connection = DBUtil.getConnection();
            Statement statement = connection.createStatement();
            String sqlWhite = "select DISTINCT(WHITE) from GAMES order by WHITE";
            ResultSet rs = statement.executeQuery(sqlWhite);
            while (rs.next()) {
                playerList.add(rs.getString(1));
            }
            playerMap.put("WHITE", new ArrayList<>(playerList));
            playerList = new ArrayList<>();
            String sqlBlack = "select DISTINCT(BLACK) from	GAMES order by BLACK";
            rs = statement.executeQuery(sqlBlack);
            while (rs.next()) {
                playerList.add(rs.getString(1));
            }
            playerMap.put("BLACK", new ArrayList<>(playerList));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerMap;
    }


    private List<String> setYears() {
        List<String> list = new ArrayList<>();
        for (int k = 1914; k <= 2019; k++)
            list.add("" + k);

        return list;
    }
}