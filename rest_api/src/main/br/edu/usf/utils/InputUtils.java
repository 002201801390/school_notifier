package br.edu.usf.utils;

public class InputUtils {

    private InputUtils() {
        throw new AssertionError("No '" + InputUtils.class + "' instances for you!");
    }

    public static boolean validString(String s) {
        return s != null && !s.isBlank();
    }
}
