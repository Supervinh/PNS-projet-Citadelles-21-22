package fr.unice.polytech.couleur;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
    private final Date date = new Date();

    @Override
    public String format(LogRecord record) {
        String couleurLevel = switch (record.getLevel().toString().toUpperCase()) {
            case "SEVERE" -> CouleurConsole.RED_BRIGHT;
            case "WARNING" -> CouleurConsole.RED;
            case "INFO" -> CouleurConsole.YELLOW_BRIGHT;
            case "CONFIG" -> CouleurConsole.YELLOW;
            case "FINE" -> CouleurConsole.BLUE;
            case "FINER" -> CouleurConsole.GREEN_BRIGHT;
            case "FINEST" -> CouleurConsole.GREEN;
            case "ALL" -> CouleurConsole.PURPLE_BRIGHT;
            default -> CouleurConsole.BLACK_BRIGHT;
        };

        date.setTime(record.getMillis());
        String dateString = "[" + new SimpleDateFormat("dd/MM/yyyy").format(date) + " " + new SimpleDateFormat("HH:mm:ss").format(date) + "]";
        return String.format(CouleurConsole.RED + "%1$s" + couleurLevel + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + record.getLevel().getLocalizedName() + "]", formatMessage(record));
    }
}