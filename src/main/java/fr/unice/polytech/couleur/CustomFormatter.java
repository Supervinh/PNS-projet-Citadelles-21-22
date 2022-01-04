package fr.unice.polytech.couleur;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {

    private static final String couleurDate = CouleurConsole.RED;
    private static final String format = "%1$s[%2$tF %2$tT]\033[0m %3$s%4$6s:\033[0m   %5$s%n\033[0m";
    private final Date date = new Date();

    public static void main(String[] args) {
        CustomFormatter customFormatter = new CustomFormatter();
        System.out.print(customFormatter.format(new LogRecord(Level.OFF, "Message...")));
        System.out.print(customFormatter.format(new LogRecord(Level.SEVERE, "Message...")));
        System.out.print(customFormatter.format(new LogRecord(Level.WARNING, "Message...")));
        System.out.print(customFormatter.format(new LogRecord(Level.INFO, "Message...")));
        System.out.print(customFormatter.format(new LogRecord(Level.CONFIG, "Message...")));
        System.out.print(customFormatter.format(new LogRecord(Level.FINE, "Message...")));
        System.out.print(customFormatter.format(new LogRecord(Level.FINER, "Message...")));
        System.out.print(customFormatter.format(new LogRecord(Level.FINEST, "Message...")));
        System.out.print(customFormatter.format(new LogRecord(Level.ALL, "Message...")));
    }

    @Override
    public String format(LogRecord record) {
        date.setTime(record.getMillis());
        String message = formatMessage(record);

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
        return String.format(format, couleurDate, date, couleurLevel, record.getLevel().getLocalizedName(), message);
    }
}