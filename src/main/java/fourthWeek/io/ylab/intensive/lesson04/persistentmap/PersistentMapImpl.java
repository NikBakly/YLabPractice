package fourthWeek.io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private final DataSource dataSource;
    private String nameMap;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.nameMap = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        boolean result = true;
        String selectContainsKeySql =
                "SELECT key " +
                        "FROM persistent_map " +
                        "WHERE key = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectContainsKeySql)) {
            preparedStatement.setString(1, key);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    result = false;
                }
            }
        }
        return result;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> result = new ArrayList<>();
        String selectAllKeysSql =
                "SELECT key " +
                        "FROM persistent_map;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllKeysSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(resultSet.getString("key"));
            }
        }

        return result;
    }

    @Override
    public Optional<String> get(String key) throws SQLException {
        String result = null;

        String selectValueSql =
                "SELECT value " +
                        "FROM persistent_map " +
                        "WHERE key = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectValueSql)) {
            preparedStatement.setString(1, key);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getString("value");
                }
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void remove(String key) throws SQLException {
        String deleteByKeySql =
                "DELETE FROM persistent_map " +
                        "WHERE key = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByKeySql)) {
            preparedStatement.setString(1, key);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            if (containsKey(key)) {
                updateValue(key, value, connection);
            } else {
                addValue(key, value, connection);
            }
        }
    }

    @Override
    public void clear() throws SQLException {
        String deleteSql =
                "DELETE FROM persistent_map " +
                        "WHERE map_name = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            if (nameMap == null) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, nameMap);
            }
            preparedStatement.executeUpdate();
        }
    }

    private void addValue(String key, String value, Connection connection) throws SQLException {
        String insertSql =
                "INSERT INTO persistent_map (map_name, key, value) " +
                        "VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            if (nameMap == null || nameMap.isEmpty()) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, nameMap);
            }

            if (key.isBlank()) {
                preparedStatement.setNull(2, Types.VARCHAR);
            } else {
                preparedStatement.setString(2, key);
            }

            if (value.isBlank() || value.isEmpty()) {
                preparedStatement.setNull(3, Types.VARCHAR);
            } else {
                preparedStatement.setString(3, value);
            }

            preparedStatement.executeUpdate();
        }

    }

    private void updateValue(String key, String value, Connection connection) throws SQLException {
        String updateSql =
                "UPDATE persistent_map set value = ? " +
                        "WHERE key = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            if (value == null || value.isBlank()) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, value);
            }
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        }

    }
}
