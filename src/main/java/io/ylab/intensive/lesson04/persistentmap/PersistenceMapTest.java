package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        persistentMap.init("Finish");
        System.out.println(persistentMap.getKeys()); // []
        persistentMap.put("1", "Maks");
        persistentMap.put("2", "Vova");
        persistentMap.put("3", "Petya");
        persistentMap.put("4", "Masha");
        persistentMap.put("2", "Gosha");
        System.out.println(persistentMap.getKeys()); // [1, 3, 4, 2]
        System.out.println(persistentMap.containsKey("3")); // true
        System.out.println(persistentMap.containsKey("8")); // false
        System.out.println(persistentMap.get("2")); // Optional[Gosha]
        persistentMap.remove("4");
        System.out.println(persistentMap.get("4")); // Optional.empty
        persistentMap.clear();
        System.out.println(persistentMap.getKeys()); // []
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar UNIQUE,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
