package fourthWeek.io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PersistentMap {

    void init(String name) throws SQLException;

    boolean containsKey(String key) throws SQLException;

    List<String> getKeys() throws SQLException;

    Optional<String> get(String key) throws SQLException;

    void remove(String key) throws SQLException;

    void put(String key, String value) throws SQLException;

    void clear() throws SQLException;
}
