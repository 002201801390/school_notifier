package log;

import com.github.logger.Log;
import org.junit.AfterClass;
import org.junit.Test;

import static java.lang.System.out;

import java.util.logging.Level;

public class TestLogger {

    @Test
    public void logAll() {

        out.println("Logging all\n\n");

        Log.setLevel(Level.ALL);
        Log.ERRO("Log error");
        Log.WARN("Log warning");
        Log.INFO("Log info");
    }

    @Test
    public void logError() {

        out.println("Logging errors\n\n");

        Log.setLevel(Level.SEVERE);
        Log.ERRO("Log error");
        Log.WARN("Log warning");
        Log.INFO("Log info");
    }

    @Test
    public void logWarning() {

        out.println("Logging warnings\n\n");

        Log.setLevel(Level.WARNING);
        Log.ERRO("Log error");
        Log.WARN("Log warning");
        Log.INFO("Log info");
    }

    @Test
    public void logInfo() {

        out.println("Logging infos\n\n");

        Log.setLevel(Level.INFO);
        Log.ERRO("Log error");
        Log.WARN("Log warning");
        Log.INFO("Log info");
    }

    @AfterClass
    public static void endTest() {
        Log.close();
    }
}