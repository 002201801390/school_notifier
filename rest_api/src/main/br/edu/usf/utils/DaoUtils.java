package br.edu.usf.utils;

import java.time.*;

public class DaoUtils {

    private DaoUtils() {
        throw new AssertionError("No " + DaoUtils.class + " instances for you!");
    }

    public static java.sql.Date convertDateField(java.time.LocalDate date) {
        if (date == null) {
            return null;
        }

        return java.sql.Date.valueOf(date);
    }

    public static java.time.LocalDate stringToLocalDate(String date) {
        if (date == null) {
            return null;
        }

        Instant instant = Instant.parse(date);
        LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

        return result.toLocalDate();
    }
}