package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * The type Scoreboard db.
 * The database need to have a "scoreboard" table, what has 2 columns: name (varchar) and score (small_int).
 */
public final class ScoreboardDB {

    /**
     * The constructor is private (and throws a single exception) to ensure it's Utility class behavior
     * and make sure its constructor is never called.
     */
    private ScoreboardDB() {
        throw new IllegalCallerException();
    }

    private static Connection setConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scoreboard", "postgres", "postgres");
        } catch (SQLException e) {
            System.out.println("Connection to database failed! ");
        }

        return connection;
    }

    /**
     * Increment score by name.
     *
     * @param name the name
     */
    public static void incrementScoreByName(String name) {


        try (Connection connection = setConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(String.format("INSERT INTO scoreboard VALUES ('%s', 0) ON CONFLICT DO NOTHING", name));
            statement.executeUpdate(String.format("UPDATE scoreboard SET score = score + 1 WHERE name = '%s'", name));
        } catch (SQLException e) {
            System.out.println("Increment failed [" + name + "]!");
        }
    }

    /**
     * Gets last ten scores.
     *
     * @return the last ten scores
     */
    public static List<PlayerData> getLastTenScores() {
        List<PlayerData> playerDataList = new ArrayList<>();

        try (Connection connection = setConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM scoreboard ORDER BY score DESC");
        ) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int score = resultSet.getInt("score");

                playerDataList.add(new PlayerData(name, score));
            }

        } catch (SQLException e) {
            System.out.println("Listing from database failed!");
        }

        return playerDataList;

    }

}

