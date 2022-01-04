package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.couleur.CouleurConsole;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Affichage {

    public static Logger log;

    static {
        InputStream stream = MoteurDeJeu.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            log = Logger.getLogger(MoteurDeJeu.class.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.setLevel(MoteurDeJeu.messageLvl);
    }

    public static void levelUpdate() {
        log.setLevel(MoteurDeJeu.messageLvl);
    }

    // MoteurDeJeu.java

    public static void citadelle() {
        log.info(CouleurConsole.gold("  __  ___ ___  _  ___   ___          ___"));
        log.info(CouleurConsole.gold(" /     |   |  | | |  ╲  |    |   |   |") + "    " + CouleurConsole.blue("Groupe.H"));
        log.info(CouleurConsole.gold(" |     |   |  |_| |   | |__  |   |   |__") + "  " + CouleurConsole.white("Polytech Edition™"));
        log.info(CouleurConsole.gold(" \\__  _|_  |  | | |__╱  |__  |__ |__ |__") + "  " + CouleurConsole.red("Jeu de Bots & IA"));
    }

    public static void malInitialise() {
        log.warning("Jeu pas initié correctement.");
    }

    public static void tourNumX(int numTour) {
        log.info("");
        log.info("");
        log.info("");
        log.info(CouleurConsole.seperateur2() + CouleurConsole.printDefault("Tour " + numTour) + CouleurConsole.seperateur2());
    }

    public static void tourAX(Joueur joueur) {
        log.info("");
        log.info("");
        log.info(CouleurConsole.seperateur1() + CouleurConsole.printDefault("Tour de ") + joueur.getNomColoured() + CouleurConsole.seperateur1());
    }

    public static void premierFini(Joueur joueur) {
        log.info("");
        log.info(CouleurConsole.gold(" ##### ") + joueur.getNomColoured() + CouleurConsole.printDefault(" a fini en ") + CouleurConsole.blue("Premier") + CouleurConsole.gold(" #####"));
    }

    public static void carteVisibleEtCachee(ArrayList<CartePersonnage> visibles, CartePersonnage cachee) {
        StringBuilder message = new StringBuilder(CouleurConsole.printDefault(CouleurConsole.green("| ") + CouleurConsole.printDefault("Carte Visible:")));
        visibles.forEach(cp -> message.append(" ").append(cp.getNomColoured()));
        log.info(message.toString());
        log.info(CouleurConsole.printDefault(CouleurConsole.green("| ") + CouleurConsole.printDefault("Carte Cachée: ") + cachee.getNomColoured()));
    }

    public static void remettreCachee(CartePersonnage cachee) {
        log.info(CouleurConsole.green("| ") + CouleurConsole.printDefault("On remet la carte cachée: ") + cachee.getNomColoured() + CouleurConsole.printDefault(" dans le deck"));
    }

    public static void nouvelleCachee(CartePersonnage cachee) {
        log.info(CouleurConsole.green("| ") + CouleurConsole.printDefault("Nouvelle carte cachée: ") + cachee.getNomColoured());
    }

    public static void gagnant(Joueur gagnant) {
        log.info("");
        log.info("");
        if (gagnant == null) {
            log.info(CouleurConsole.printDefault("Pas de ") + CouleurConsole.red("Gagnant"));
        } else {
            log.info(CouleurConsole.printDefault("Le ") + CouleurConsole.red("Gagnant") + CouleurConsole.printDefault(" est ") + gagnant.getNomColoured() + CouleurConsole.printDefault(" avec ") + CouleurConsole.gold("" + gagnant.getPoints()) + CouleurConsole.printDefault(" points"));
        }
    }

    public static void classement(ArrayList<Joueur> joueurs, int numTour) {
        log.info("");
        log.info("");
        log.info(CouleurConsole.seperateur2() + CouleurConsole.turquoise("Classement apres " + numTour + " Tours") + CouleurConsole.seperateur2());
        joueurs.forEach(joueur -> log.info(CouleurConsole.tiret() + joueur.getNomColoured() + CouleurConsole.printDefault(" a ") + CouleurConsole.gold("" + joueur.getPoints()) + CouleurConsole.printDefault(" points")));
    }

    public static void initialisation(ArrayList<Joueur> joueurs) {
        log.info("");
        log.info(CouleurConsole.seperateur1() + CouleurConsole.printDefault("Entrez Nom des Joueurs") + CouleurConsole.seperateur1());
        for (int i = 1; i <= joueurs.size(); i++) {
            log.info(CouleurConsole.tiret() + CouleurConsole.printDefault("Joueur " + i + ": ") + joueurs.get(i - 1).getNomColoured());
        }
    }

    // Joueur.java

    public static void mort(Joueur joueur) {
        log.info(joueur.getNomColoured() + CouleurConsole.printDefault(" est ") + CouleurConsole.red("Mort"));
    }

    public static void orTitre() {
        log.info(CouleurConsole.gold("| Piocher Or"));
    }

    public static void personnageTitre() {
        log.info(CouleurConsole.green("| Piocher Personnages"));
    }

    public static void quartierTitre() {
        log.info(CouleurConsole.purple("| Piocher Quartier"));
    }

    public static void or(Joueur joueur, int or) {
        log.info(CouleurConsole.gold("| ") + joueur.getNomColoured() + CouleurConsole.printDefault(" a pioché ") + CouleurConsole.gold("" + or) + CouleurConsole.printDefault(" pièce" + (or > 1 ? "s" : "") + " d'") + CouleurConsole.gold("Or"));
    }

    public static void personnage(Joueur joueur, CartePersonnage cp) {
        log.info(CouleurConsole.green("| ") + joueur.getNomColoured() + " a pioché: " + cp.getNomColoured());
    }

    public static void quartier(Joueur joueur, CarteQuartier cq) {
        log.info(CouleurConsole.purple("| ") + joueur.getNomColoured() + CouleurConsole.printDefault(" a pioché: ") + cq.getNomColoured());
    }

    public static void titreFormatted(String titre) {
        log.warning("");
        log.warning("");
        log.warning(CouleurConsole.printDefault(titre));
    }

    public static void ligneFormatted(String ligne) {
        log.warning(CouleurConsole.printDefault(ligne));
    }
}
