package io.ylab.intensive.lesson04.filesort;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

public class FileSorterTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        File data = new File("src/main/java/thirdWeek/fileSort/data.txt");
        FileSorter fileSorter = new FileSortImpl(dataSource);

        double startTime = System.currentTimeMillis();
        File res = fileSorter.sort(data);
        double endTime = System.currentTimeMillis();

        System.out.printf("Время выполнения: %s мс.\n", (endTime - startTime));
        System.out.printf("Время выполнения: %f сек.\n", (endTime - startTime) / 1000);
        System.out.printf("Время выполнения: %f мин.", (endTime - startTime) / 1000 / 60);
        // С batch processing: 8 сек;
        // Без batch processing: 104 сек
    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = ""
                + "drop table if exists numbers;"
                + "CREATE TABLE if not exists numbers (\n"
                + "\tval bigint\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
