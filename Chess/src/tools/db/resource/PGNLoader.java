package tools.db.resource;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс для загрузки в базу данных файлов в формате PGN.
 */
public class PGNLoader {
    private static Logger log = Logger.getLogger(PGNLoader.class.toString());

    final private static String TABLE_NAME = "GAMES";

    PGNLoader(File pgnFile) {
    }

    public static void createTable() throws SQLException, FileNotFoundException {
        Connection connection = DBUtil.getConnection();

        String sql = GameProperties.INSTANCE.createTableSQL(TABLE_NAME);
        log.info(sql);

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();

        connection.commit();
        connection.close();

        dumpTable(TABLE_NAME);
    }

    public static void dumpTable(String tableName) throws SQLException, FileNotFoundException {
        String sql = String.format("SELECT * FROM %s;", TABLE_NAME);
        log.info(sql);

        Connection connection = DBUtil.getConnection();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        DBUtil.dumpResultSet(tableName + "_content", rs);

        connection.close();
    }

    public static Stream<String> getLinesStream(String fileName) {
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(fileName), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writePGNtoDb(InputStreamReader pgnFile) throws SQLException {
        Connection connection = DBUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into " + TABLE_NAME
                + "(event, site, date, round, white, black, result, moves) "
                + "values(?,?,?,?,?,?,?,?)");
        List<String> games = readFile(pgnFile);
        List<GameProperties> gamePropertiesList = games.stream().map(PGNLoader::createEntity).collect(Collectors.toList());
        gamePropertiesList.forEach(game -> {
            int i = 0;
            for (String property : GameProperties.MAIN_PROPERTIES) {
                try {
                    statement.setString(++i, game.get(property));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            try {
                statement.setString(++i, game.get("Moves"));
                statement.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        statement.executeBatch();
    }


    private static List<String> readFile(InputStreamReader reader) {
        StringBuilder sb = new StringBuilder();
        List<String> games = new ArrayList<>();
        try {
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.length() == 0) {
                        if (sb.length() != 0) {
                            if (sb.indexOf("[") == -1) {
                                String prev = games.get(games.size() - 1);
                                games.set(games.size() - 1, prev + "moves:" + sb.toString());
                            } else
                                games.add(sb.toString());
                        }
                        sb = new StringBuilder();
                    } else {
                        sb.append(line);
                        sb.append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;

    }

    private static GameProperties createEntity(String game) {
        GameProperties gameProperties = new GameProperties();
        Arrays.stream(GameProperties.MAIN_PROPERTIES).forEach(
                property -> gameProperties.put(property, findTagValue(game, "\\[" + property + ".*\\]")));
        gameProperties.put("Moves", findMoves(game));
        return gameProperties;
    }

    private static String findTagValue(String game, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(game);
        if (matcher.find()) {
            String tag = matcher.group();
            return tag.substring(tag.indexOf('"') + 1, tag.lastIndexOf('"'));
        }
        return "";
    }

    private static String findMoves(String game) {
        Pattern pattern = Pattern.compile("moves:([\\w.\\s-])*");
        Matcher matcher = pattern.matcher(game);
        if (matcher.find()) {
            return matcher.group().replace("moves:", "");
        }
        return "";
    }
}
