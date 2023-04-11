package fifthWeek.io.ylab.intensive.lesson05.sqlquerybuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private final DataSource dataSource;

    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        StringBuilder result = new StringBuilder("SELECT ");
        List<String> columns = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             ResultSet rs = connection.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString(4));
            }
        }
        for (int i = 0; i < columns.size(); i++) {
            if (i == columns.size() - 1) {
                result.append(columns.get(i)).append(" ");
            } else {
                result.append(columns.get(i)).append(", ");
            }
        }
        result.append("FROM ").append(tableName).append(";");
        return result.toString();
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> result = new ArrayList<>();
        String[] types = {"TABLE"};
        try (Connection connection = dataSource.getConnection();
             ResultSet rs = connection.getMetaData().getTables(null, null, "%", types)) {
            while (rs.next()) {
                result.add(rs.getString("TABLE_NAME"));
            }
        }
        return result;
    }
}
