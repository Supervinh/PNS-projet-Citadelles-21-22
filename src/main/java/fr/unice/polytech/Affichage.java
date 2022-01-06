package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.couleur.CouleurConsole;
import fr.unice.polytech.couleur.CustomFormatter;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * La classe permettant d'avoir notre affichage.
 */
public class Affichage {

    public static Logger log;

    static {
        try {
            log = Logger.getLogger(MoteurDeJeu.class.getName());
            Handler CustomHandler = new StreamHandler(System.out, new CustomFormatter());
            CustomHandler.setLevel(MoteurDeJeu.messageLvl);
            log.setLevel(MoteurDeJeu.messageLvl);
            log.addHandler(CustomHandler);
            log.setUseParentHandlers(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void levelUpdate() {
        log.setLevel(MoteurDeJeu.messageLvl);
    }

    public static void affiche(String string) {
        log.info(string);
    }

    // MoteurDeJeu.java

    public static void citadelle() {
        log.fine(CouleurConsole.gold("   __  ___ ___  _  ___   ___          ___"));
        log.fine(CouleurConsole.gold("  /     |   |  | | |  ╲  |    |   |   |") + "    " + CouleurConsole.blue("Groupe.H"));
        log.fine(CouleurConsole.gold(" |      |   |  |_| |   | |__  |   |   |__") + "  " + CouleurConsole.white("Polytech Edition™"));
        log.fine(CouleurConsole.gold("  \\__  _|_  |  | | |__╱  |__  |__ |__ |__") + "  " + CouleurConsole.red("Jeu de Bots & IA"));
        log.fine("");
    }

    public static void tourNumX(int numTour) {
        log.finest("");
        log.finer("");
        log.finer("");
        log.finer(CouleurConsole.seperateur2() + "Tour " + numTour + CouleurConsole.seperateur2());
    }

    public static void tourAX(Joueur joueur) {
        log.finest("");
        log.finer("");
        log.finer(CouleurConsole.seperateur1() + "Tour de " + joueur.getNomColoured() + CouleurConsole.seperateur1());
    }

    public static void premierFini(Joueur joueur) {
        log.finer("");
        log.finer(CouleurConsole.gold(" ##### ") + joueur.getNomColoured() + " a fini en " + CouleurConsole.blue("Premier") + CouleurConsole.gold(" #####"));
    }

    public static void carteVisibleEtCachee(ArrayList<CartePersonnage> visibles, CartePersonnage cachee) {
        StringBuilder message = new StringBuilder(CouleurConsole.green("| ") + "Carte Visible:");
        visibles.forEach(cp -> message.append(" ").append(cp.getNomColoured()));
        log.finest(message.toString());
        log.finest(CouleurConsole.green("| ") + "Carte Cachée: " + cachee.getNomColoured());
    }

    public static void remettreCachee(CartePersonnage cachee) {
        log.finest(CouleurConsole.green("| ") + "On remet la carte cachée: " + cachee.getNomColoured() + " dans le deck");
    }

    public static void nouvelleCachee(CartePersonnage cachee) {
        log.finest(CouleurConsole.green("| ") + "Nouvelle carte cachée: " + cachee.getNomColoured());
    }

    public static void gagnant(Joueur gagnant) {
        log.finer("");
        log.finer("");
        if (gagnant == null) {
            log.finer("Pas de " + CouleurConsole.red("Gagnant"));
        } else {
            log.finer("Le " + CouleurConsole.red("Gagnant") + " est " + gagnant.getNomColoured() + " avec " + CouleurConsole.gold("" + gagnant.getPoints()) + " points");
        }
    }

    public static void classement(ArrayList<Joueur> joueurs, int numTour) {
        log.finer("");
        log.finer("");
        log.finer(CouleurConsole.seperateur2() + CouleurConsole.turquoise("Classement apres " + numTour + " Tours") + CouleurConsole.seperateur2());
        joueurs.forEach(joueur -> log.finer(CouleurConsole.tiret() + joueur.getNomColoured() + " a " + CouleurConsole.gold("" + joueur.getPoints()) + " points"));
    }

    public static void initialisation(ArrayList<Joueur> joueurs) {
        log.finer("");
        log.finer("");
        log.finer(CouleurConsole.seperateur1() + "Entrez Nom des Joueurs" + CouleurConsole.seperateur1());
        for (int i = 1; i <= joueurs.size(); i++) {
            log.finer(CouleurConsole.tiret() + "Joueur " + i + ": " + joueurs.get(i - 1).getNomColoured());
        }
    }

    // Joueur.java

    public static void mort(Joueur joueur) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " est " + CouleurConsole.red("Mort"));
    }

    public static void orTitre() {
        log.finest("");
        log.finer(CouleurConsole.gold("| Piocher Or"));
    }

    public static void personnageTitre() {
        log.finest("");
        log.finer(CouleurConsole.green("| Piocher Personnages"));
    }

    public static void quartierTitre() {
        log.finest("");
        log.finer(CouleurConsole.purple("| Piocher Quartier"));
    }

    public static void or(Joueur joueur, int or) {
        log.finer(CouleurConsole.gold("| ") + joueur.getNomColoured() + " a pioché " + CouleurConsole.gold("" + or) + " pièce" + (or > 1 ? "s" : "") + " d'" + CouleurConsole.gold("Or"));
    }

    public static void personnage(Joueur joueur, CartePersonnage cp) {
        log.finer(CouleurConsole.green("| ") + joueur.getNomColoured() + " a pioché: " + cp.getNomColoured());
    }

    public static void quartier(Joueur joueur, CarteQuartier cq) {
        log.finer(CouleurConsole.purple("| ") + joueur.getNomColoured() + " a pioché: " + cq.getNomColoured());
    }


    public static void titreFormatted(String titre) {
        log.info("");
        log.info(titre);
    }

    public static void ligneFormatted(String ligne) {
        log.info(ligne);
    }

    public static void choisirCarteQuartier(Joueur joueur, CarteQuartier cq) {
        log.finest(CouleurConsole.purple("| ") + (joueur.getNomColoured() + " a choisi: " + cq.getNomColoured()));
    }


    public static void sauterLigne() {
        log.finest("");
    }

    public static void choixQuartierConstruit(Joueur joueur, int or, ArrayList<CarteQuartier> quartiersAchetable) {
        AtomicInteger i = new AtomicInteger(1);
        log.finest("");
        log.finer(CouleurConsole.pink("| Construire Quartier") + " - " + joueur.getNomColoured() + " à " + joueur.getOrColoured() + " pièce" + (or > 1 ? "s" : "") + " d'" + CouleurConsole.gold("Or"));
        log.finest(CouleurConsole.pink("| ") + CouleurConsole.tiret() + "Choix 0: Ne pas construire");
        quartiersAchetable.forEach(quartier -> log.finest(CouleurConsole.pink("| ") + CouleurConsole.tiret() + "Choix " + (i.getAndIncrement()) + ": " + quartier.getNomColoured() + ", " + quartier.getPrixColoured() + ", " + quartier.getGemmeColoured() + (quartier.getDescription().equals("None") ? "" : ", " + quartier.getDescriptionColoured())));
    }

    public static void construitQuartier(Joueur joueur, CarteQuartier cq) {
        log.finer(CouleurConsole.pink("| ") + joueur.getNomColoured() + " a construit: " + cq.getNomColoured());
    }

    public static void pasAssezDor(Joueur joueur) {
        log.finest(CouleurConsole.pink("| ") + joueur.getNomColoured() + " n'a pas assez de pièces d'" + CouleurConsole.gold("Or") + " pour construire.");

    }

    public static void infoJoueur(Joueur joueur, CartePersonnage personnage) {
        log.finest("");
        log.finer(CouleurConsole.blue("| Details Joueur"));
        log.finer(CouleurConsole.blue("| ") + "Personnage: " + personnage.getNomColoured());
        log.finer(CouleurConsole.blue("| ") + "Pièces d'Or: " + joueur.getOrColoured());
        log.finest(CouleurConsole.blue("| ") + "Stratégie: " + joueur.getNomStrategieColoured());
        log.finest(CouleurConsole.blue("| ") + "Type de Pioche: " + joueur.getNomPiocheColoured());
        log.finest(CouleurConsole.blue("| ") + "Quartiers dans la main: " + joueur.getQuartiers().stream().map(CarteQuartier::getNomColoured).toList());
        log.finest(CouleurConsole.blue("| ") + "Quartiers construit: " + joueur.getQuartiersConstruits().stream().map(CarteQuartier::getNomColoured).toList());
    }

    // Deck.java

    public static void plusDeQuartiers() {
        log.finer(CouleurConsole.purple("| ") + "Plus de Quartiers...");
    }

    public static void plusDePersonnages() {
        log.finer(CouleurConsole.red("| ") + "Plus de Personnages...");
    }

    public static void dejaCarteQuartier(CarteQuartier cq) {
        log.finer("Le Deck contient déjà: " + cq.getNomColoured());
    }

    public static void dejaCartePersonnage(CartePersonnage personnage) {
        log.finer("Le Deck Contiens deja: " + personnage.getNomColoured());
    }


    // Les pouvoirs
    // IPouvoir.java

    public static void recuperationGemmes(Joueur joueur) {
        log.finer(CouleurConsole.red("| ") + "Recuperation des " + CouleurConsole.gold("Taxes") + " des Gemmes " + joueur.getPersonnage().getGemmeColoured());
    }

    public static void piocherOrSupp(Joueur joueur, boolean plurielle, int count) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " a pioché " + CouleurConsole.gold("" + count) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.gold("Or") + " supplémentaire" + (plurielle ? "s" : "") + ".");
    }

    // PouvoirArchitecte.java

    public static void pouvoir(Joueur joueur) {
        log.finest("");
        log.finer(CouleurConsole.red("| Pouvoir " + joueur.getPersonnage().getNomColoured()));
    }

    public static void barreRouge() {
        log.finest(CouleurConsole.red("| "));
    }

    public static void plusQuartierSupp() {
        log.finest(CouleurConsole.red("| ") + "Aucun quartier supplémentaire construit");
    }

    // PouvoirAssassin.java

    public static void aTue(Joueur joueur, Joueur cible) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " a " + CouleurConsole.red("tué " + cible.getNomColoured()));
    }

    public static void essayerDeTuer(Joueur joueur, CartePersonnage cibleNomPersonnage) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " a essayé de " + CouleurConsole.red("tuer ") + cibleNomPersonnage.getArticle().toLowerCase() + cibleNomPersonnage.getNomColoured());
    }


    // PouvoirCondottiere.java

    public static void detructionQuatier(Joueur joueur, CarteQuartier cq, Joueur cible) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " a détruit le quartier " + cq.getNomColoured() + " de " + cible.getNomColoured());
    }

    public static void recupererQuartier(Joueur cible, CarteQuartier cq) {
        log.finer(CouleurConsole.red("| ") + cible.getNomColoured() + " a récupéré le quartier " + cq.getNomColoured() + "contre une pièce d'or.");
    }

    public static void pasRecupererQuartier() {
        log.finest(CouleurConsole.red("| ") + "pas de récupération de quartier.");
    }

    public static void pasDetruitQuartier(Joueur joueur) {
        log.finest(CouleurConsole.red("| ") + joueur.getNomColoured() + " n'a pas détruit de quartier.");
    }

    // PouvoirMagicien.java

    public static void echangeCartePiocheOuJoueur() {
        log.finest(CouleurConsole.red("| ") + CouleurConsole.tiret() + "Choix 1: échanger ses cartes avec un joueur ");
        log.finest(CouleurConsole.red("| ") + CouleurConsole.tiret() + "Choix 2: échanger n cartes avec la pioche");
        log.finest(CouleurConsole.red("|"));
    }

    public static void choixEchangeJoueur(Joueur joueur) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " a choisi d'échanger ses cartes avec un joueur");
    }

    public static void echangeAvecJoueur(Joueur joueur, Joueur cible) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " a échangé ses cartes avec " + cible.getNomColoured());
    }

    public static void choixEchangePioche(Joueur joueur) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " a choisi d'échanger des cartes avec la pioche");
    }

    public static void echangeAvecPioche(Joueur joueur, int nb) {
        log.finest(CouleurConsole.red("| ") + joueur.getNomColoured() + " a échangé " + CouleurConsole.purple("" + nb) + " cartes avec la pioche.");
    }


    // PouvoirRoi.java

    public static void estRoi(Joueur joueur) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " est le Nouveau " + CouleurConsole.gold("Roi"));
    }


    // PouvoirVoleur.java

    public static void volerOr(Joueur joueur, Joueur cible, int montant, boolean plurielle) {
        log.finer(CouleurConsole.red("| ") + joueur.getNomColoured() + " a volé " + CouleurConsole.gold("" + montant) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.gold("Or") + " à " + cible.getNomColoured());
    }

    public static void essayerVolerOr(Joueur joueur, String article, CartePersonnage personnage) {
        log.finest(CouleurConsole.red("| ") + joueur.getNomColoured() + " a essayé de voler les pièces d'" + CouleurConsole.gold("Or") + article + personnage.getNomColoured());
    }

    // Chronomètre

    public static void chrono(long start) {
        long duration = (long) ((System.nanoTime() - start) / (double) 1000000000);
        log.info("");
        log.info("Temps execution: " + duration + "s.");
        log.info("");
    }

    public static void info(String message) {
        log.info(message);
    }

    public static void warning(String message) {
        log.warning(CouleurConsole.red(message));
    }

    public static void severe(String message) {
        log.severe(CouleurConsole.red(message));
        System.exit(-1);
    }
}
