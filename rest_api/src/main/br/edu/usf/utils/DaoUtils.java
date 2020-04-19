package br.edu.usf.utils;

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
}