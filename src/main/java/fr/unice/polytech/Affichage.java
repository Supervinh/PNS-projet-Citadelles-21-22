package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
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

    public static void citadelle() {
        log.info(CouleurConsole.printGold("  __  ___ ___  _  ___   ___          ___"));
        log.info(CouleurConsole.printGold(" /     |   |  | | |  ╲  |    |   |   |") + "    " + CouleurConsole.printBlue("Groupe.H"));
        log.info(CouleurConsole.printGold(" |     |   |  |_| |   | |__  |   |   |__") + "  " + CouleurConsole.printWhite("Polytech Edition™"));
        log.info(CouleurConsole.printGold(" \\__  _|_  |  | | |__╱  |__  |__ |__ |__") + "  " + CouleurConsole.printRed("Jeu de Bots & IA"));
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
        log.info(CouleurConsole.printGold(" ##### ") + joueur.getNomColoured() + CouleurConsole.printDefault(" a fini en ") + CouleurConsole.printBlue("Premier") + CouleurConsole.printGold(" #####"));
    }

    public static void piocherPersonnages(ArrayList<Joueur> joueurs) {
        log.info("");
        log.info(CouleurConsole.printGreen("| Piocher les Personnages"));
    }

    public static void piocherPersonnage(Joueur joueur) {

    }

    public static void carteVisibleEtCachee(ArrayList<CartePersonnage> visibles, CartePersonnage cachee) {
        StringBuilder message = new StringBuilder(CouleurConsole.printDefault(CouleurConsole.printGreen("| ") + "Carte Visible:"));
        visibles.forEach(cp -> message.append(" ").append(cp.getNomColoured()));
        log.info(message.toString());
        log.info(CouleurConsole.printDefault(CouleurConsole.printGreen("| ") + "Carte Cachée: ") + cachee.getNomColoured());
    }

    public static void remettreCachee(CartePersonnage cachee) {
        log.info(CouleurConsole.printGreen("| ") + CouleurConsole.printDefault("On remet la carte cachée: ") + cachee.getNomColoured() + CouleurConsole.printDefault(" dans le deck"));
    }

    public static void nouvelleCachee(CartePersonnage cachee) {
        log.info(CouleurConsole.printGreen("| ") + CouleurConsole.printDefault("Nouvelle carte cachée: ") + cachee.getNomColoured());
    }

    public static void gagnant(Joueur gagnant) {
        log.info("");
        log.info("");
        if (gagnant == null) {
            log.info(CouleurConsole.printDefault("Pas de ") + CouleurConsole.printRed("Gagnant"));
        } else {
            log.info(CouleurConsole.printDefault("Le ") + CouleurConsole.printRed("Gagnant") + CouleurConsole.printDefault(" est ") + gagnant.getNomColoured() + CouleurConsole.printDefault(" avec ") + CouleurConsole.printGold("" + gagnant.getPoints()) + CouleurConsole.printDefault(" points"));
        }
    }

    public static void classement(ArrayList<Joueur> joueurs, int numTour) {
        log.info("");
        log.info("");
        log.info(CouleurConsole.seperateur2() + CouleurConsole.printTurquoise("Classement apres " + numTour + " Tours") + CouleurConsole.seperateur2());
        joueurs.forEach(joueur -> log.info(CouleurConsole.tiret() + joueur.getNomColoured() + CouleurConsole.printDefault(" a ") + CouleurConsole.printGold("" + joueur.getPoints()) + CouleurConsole.printDefault(" points")));
    }

    public static void initialisation(ArrayList<Joueur> joueurs) {
        log.info("");
        log.info(CouleurConsole.seperateur1() + CouleurConsole.printDefault("Entrez Nom des Joueurs") + CouleurConsole.seperateur1());
        for (int i = 1; i <= joueurs.size(); i++) {
            log.info(CouleurConsole.tiret() + CouleurConsole.printDefault("Joueur " + i + ": " )+ joueurs.get(i - 1).getNomColoured());
        }
    }
}
