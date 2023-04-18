package io.ylab.intensive.lesson03.datedMap;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap {
    private final Map<String, ValueWithDate> map = new HashMap<>();

    @Override
    public void put(String key, String value) {
        ValueWithDate newValue = new ValueWithDate(
                value,
                new Date(Instant.now().toEpochMilli())
        );
        map.put(key, newValue);
    }

    @Override
    public String get(String key) {
        String result = null;
        if (containsKey(key)) {
            result = map.get(key).getValue();
        }
        return result;
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        Date result = null;
        if (containsKey(key)) {
            result = map.get(key).getDate();
        }
        return result;
    }
}
