package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileSortImpl implements FileSorter {
    private final DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        File result = new File("sortedData.txt");
        String selectSql =
                "SELECT val " +
                        "FROM numbers " +
                        "ORDER BY val DESC;";

        addNumbersWithBatchProcessing(data);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
             ResultSet resultSet = preparedStatement.executeQuery();
             FileWriter fileWriter = new FileWriter(result, false)) {
            while (resultSet.next()) {
                long value = resultSet.getLong("val");
                fileWriter.write(value + "\n");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void addNumbersWithBatchProcessing(File data) {
        final int BATCH_SIZE = 35;

        String insertSql =
                "INSERT INTO numbers (val) VALUES (?);";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(data));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            connection.setAutoCommit(false);
            int i = 1;
            while (bufferedReader.ready()) {
                long value = Long.parseLong(bufferedReader.readLine());
                preparedStatement.setLong(1, value);
                preparedStatement.addBatch();
                if (i % BATCH_SIZE == 0 || !bufferedReader.ready()) {
                    preparedStatement.executeBatch();
                    connection.commit();
                }
                ++i;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addNumbersWithOutBatchProcessing(File data) throws IOException, SQLException {
        String insertSql =
                "INSERT INTO numbers (val) VALUES (?);";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(data));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            while (bufferedReader.ready()) {
                long value = Long.parseLong(bufferedReader.readLine());
                preparedStatement.setLong(1, value);
                preparedStatement.executeUpdate();
            }
        }
    }
}
