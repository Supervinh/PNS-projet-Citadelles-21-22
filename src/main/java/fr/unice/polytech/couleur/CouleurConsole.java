package fr.unice.polytech.couleur;

/**
 * @author https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println & Matis Herrmann
 * Pouvant être amélioré
 */
public class CouleurConsole {
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN

    public static String seperateur1() {
        return RED + " #" + RESET + "#" + RED + "#" + RESET + "#" + RED + "# " + RESET;
    }

    public static String seperateur2() {
        return BLUE + " #" + BLACK_BRIGHT + "#" + BLUE + "#" + BLACK_BRIGHT + "#" + BLUE + "# " + RESET;
    }

    public static String tiret() {
        return PURPLE_BRIGHT + " - " + RESET;
    }

    public static String printDefault(String text) {
        return RESET + text + RESET;
    }

    public static String red(String text) {
        return RED + text + RESET;
    }

    public static String green(String text) {
        return GREEN + text + RESET;
    }

    public static String blue(String text) {
        return BLUE + text + RESET;
    }

    public static String purple(String text) {
        return PURPLE + text + RESET;
    }

    public static String cyan(String text) {
        return CYAN + text + RESET;
    }

    public static String white(String text) {
        return WHITE + text + RESET;
    }

    public static String gold(String text) {
        return YELLOW_BRIGHT + text + RESET;
    }

    public static String turquoise(String text) {
        return CYAN_BRIGHT + text + RESET;
    }

    public static String grey(String text) {
        return BLACK_BRIGHT + text + RESET;
    }

    public static String pink(String text) {
        return PURPLE_BRIGHT + text + RESET;
    }
}
