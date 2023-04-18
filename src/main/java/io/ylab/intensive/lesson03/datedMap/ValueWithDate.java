package io.ylab.intensive.lesson03.datedMap;

import java.util.Date;

public class ValueWithDate {
    private final String value;
    private final Date date;

    public ValueWithDate(String value, Date date) {
        this.value = value;
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

}
