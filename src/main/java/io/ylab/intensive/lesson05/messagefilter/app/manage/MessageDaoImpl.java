package io.ylab.intensive.lesson05.messagefilter.app.manage;

import io.ylab.intensive.lesson05.DbUtil;
import io.ylab.intensive.lesson05.messagefilter.FileInfo;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDaoImpl implements MessageDao {
    private final DataSource dataSource;

    public MessageDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        init();
    }

    @Override
    public boolean containsWorld(String world) {
        boolean result = true;
        String selectSql = "" +
                "SELECT * " +
                "FROM words " +
                "WHERE word ILIKE ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, world);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                result = false;
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.print("Ошибка при проверке существование слова:");
            System.out.println(e.getMessage());
        }

        return result;
    }

    private void init() {
        try (Connection connection = dataSource.getConnection();
             ResultSet rs = connection.getMetaData().getTables(null, null, "words", null)) {
            if (rs.next()) {
                dropColumns(connection);
            } else {
                createTable();
            }
            insertWordsFromFile(connection);
        } catch (SQLException e) {
            System.out.print("Ошибка при инициализации данных из файла:");
            System.out.println(e.getMessage());
        }
    }

    private void insertWordsFromFile(Connection connection) {
        File source = new File(FileInfo.PATH);
        String insertSql = "INSERT INTO words (word) VALUES (?);";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(source));
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            connection.setAutoCommit(false);
            int i = 1;
            while (bufferedReader.ready()) {
                String world = bufferedReader.readLine();
                preparedStatement.setString(1, world);
                preparedStatement.addBatch();
                if (i % FileInfo.BATCH_SIZE == 0 || !bufferedReader.ready()) {
                    preparedStatement.executeBatch();
                    connection.commit();
                }
                ++i;
            }
        } catch (Exception e) {
            System.out.print("Ошибка при вставке данных из файла:");
            System.out.println(e.getMessage());
        }
    }


    private void createTable() throws SQLException {
        String ddl = ""
                + "create table words (\n"
                + "person_id bigserial primary key,\n"
                + "word varchar NOT NULL\n"
                + ");";
        DbUtil.applyDdl(ddl, dataSource);
    }

    private void dropColumns(Connection connection) throws SQLException {
        String deleteSql = "TRUNCATE TABLE words;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.executeUpdate();
        }
    }
}
