package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class MoteurDeJeu {

    public static Deck deck;
    public static int nombre2Joueur = 5;
    public static int or2Depart = 2;
    public static int orAPiocher = 2;
    public static int carte2Depart = 4;
    public static int carteAPiocher = 2;
    public static int nombre2QuartiersAConstruire = 8;
    public static ArrayList<Joueur> joueurs;
    public static ArrayList<Joueur> personnagesConnus;
    private final int nombre2Personnages;
    public ArrayList<CartePersonnage> cartesVisibles = new ArrayList<>();
    private int nb2Tours = 0;
    private int roiIndex = 0;
    private boolean avaitRoi = true;
    private CartePersonnage carteCachee;

    public MoteurDeJeu() {
        deck = new Deck();
        joueurs = new ArrayList<>();
        personnagesConnus = new ArrayList<>();
        this.nombre2Personnages = deck.getPersonnages().size();
    }

    public void jouer() {
        this.hello();
        this.initialiseJoueurs(joueurs);
        this.printJoueursInitialises(joueurs);
        this.lancerTourDeJeu(joueurs);
        this.printGagnant(joueurs);
        this.montrerClassement(joueurs);
    }

    void hello() {
        System.out.println(CouleurConsole.printGold(" __  ___ ___  _  ___   ___          ___"));
        System.out.println(CouleurConsole.printGold("/     |   |  | | |  ╲  |    |   |   |") + "    " + CouleurConsole.printBlue("Grp.H"));
        System.out.println(CouleurConsole.printGold("|     |   |  |_| |   | |__  |   |   |__") + "  " + CouleurConsole.printWhite("Jeux de"));
        System.out.println(CouleurConsole.printGold("\\__  _|_  |  | | |__╱  |__  |__ |__ |__") + "  " + CouleurConsole.printRed("Bots IA"));
        System.out.println();
    }

    void printJoueursInitialises(ArrayList<Joueur> joueurs) {
        System.out.println("\n" + CouleurConsole.seperateur1() + "Entrez Nom des Joueurs" + CouleurConsole.seperateur1());
        for (int i = 1; i <= joueurs.size(); i++) {
            System.out.println(CouleurConsole.tiret() + "Joueur " + i + ": " + joueurs.get(i - 1).getNom());
        }
    }

    public void initialiseJoueurs(ArrayList<Joueur> joueurs) {
        ExcelReader ER = new ExcelReader();
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            joueurs.add(new Joueur(CouleurConsole.printCyan(ER.getRandomName())));
        }
        joueurs.get(0).setEstRoi(true);
    }

    void trouverQuiEstRoi(ArrayList<Joueur> joueurs) {
        AtomicInteger k = new AtomicInteger(0);
        this.roiIndex = joueurs.stream().peek(v -> k.getAndIncrement()).anyMatch(Joueur::isEstRoi) ? k.get() - 1 : this.roiIndex;
        if (!this.avaitRoi) {
            joueurs.get(this.roiIndex).setEstRoi(false);
            this.roiIndex = (this.roiIndex + 1) % nombre2Joueur;
            joueurs.get(this.roiIndex).setEstRoi(true);
        }
    }

    void joueurPiochePersonnage(Joueur joueur) {
        joueur.piocherPersonnage();
    }

    public void piocherPersonnage(ArrayList<Joueur> joueurs) {
        initialisePileCartes();
        System.out.println(CouleurConsole.printGreen("\n| Piocher les Personnages"));
        for (int i = this.roiIndex; i < joueurs.size(); i++) {
            System.out.print(CouleurConsole.printGreen("| "));
            joueurPiochePersonnage(joueurs.get(i));
        }
        for (int i = 0; i < this.roiIndex; i++) {
            System.out.print(CouleurConsole.printGreen("| "));
            joueurPiochePersonnage(joueurs.get(i));
        }
    }

    void jouerDansLOrdreDesPersonnages(ArrayList<Joueur> joueurs) {
        for (int i = 1; i <= this.nombre2Personnages; i++) {
            for (Joueur joueur : joueurs) {
                if (joueur.getPersonnage().getId() == i) this.tour2Jeu(joueur);
            }
        }
        this.avaitRoi = joueurs.stream().anyMatch(joueur -> joueur.getPersonnage().getNom().equals("Roi") && !joueur.isEstTue());
        joueurs.forEach(joueur -> deck.ajoutePersonnage(joueur.getPersonnage()));

        this.cartesVisibles.forEach(cp -> deck.ajoutePersonnage(cp));
        deck.ajoutePersonnage(this.carteCachee);
    }

    void tour2Jeu(Joueur joueur) {
        System.out.println("\n\n" + CouleurConsole.seperateur1() + "Tour de " + joueur.getNom() + CouleurConsole.seperateur1());
        joueur.jouer();
        personnagesConnus.add(joueur);
        if (verifieFini(joueur)) {
            System.out.println("\n" + CouleurConsole.printGold("##### ") + joueur.getNom() + " a fini en " + CouleurConsole.printBlue("Premier") + CouleurConsole.printGold(" #####"));
        }
        joueur.calculePoints();
    }

    boolean verifieFini(Joueur joueur) {
        if (joueur.getQuartiersConstruits().size() >= MoteurDeJeu.nombre2QuartiersAConstruire && joueurs.stream().noneMatch(Joueur::isFirst)) {
            joueur.setFirst(true);
            return true;
        }
        return false;
    }

    void lancerTourDeJeu(ArrayList<Joueur> joueurs) {
        while (joueurs.stream().noneMatch(Joueur::isFirst)) {
            personnagesConnus = new ArrayList<>();
            System.out.println("\n\n\n" + CouleurConsole.seperateur2() + "Tour " + ++this.nb2Tours + CouleurConsole.seperateur2());
            this.trouverQuiEstRoi(joueurs);
            this.piocherPersonnage(joueurs);
            this.jouerDansLOrdreDesPersonnages(joueurs);
        }
    }

    int obtenirMaxPoints(ArrayList<Joueur> joueurs) {
        return joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
    }

    Joueur obtenirGagnant(ArrayList<Joueur> joueurs) {
        ArrayList<Joueur> gagnants = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPoints() == obtenirMaxPoints(joueurs)).toList());
        if (gagnants.size() > 1) {
            int premierJoueur = gagnants.stream().mapToInt(joueur -> (int) joueur.getPersonnage().getId()).min().orElse(0);
            gagnants = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPersonnage().getId() == premierJoueur).toList());
        }
        return gagnants.get(0);
    }

    private void printGagnant(ArrayList<Joueur> joueurs) {
        Joueur winner = obtenirGagnant(joueurs);
        System.out.println("\n");
        if (winner == null) {
            System.out.println("Pas de " + CouleurConsole.printRed("Gagnant"));
        } else {
            System.out.print("Le " + CouleurConsole.printRed("Gagnant") + " est ");
            System.out.println(winner.getNom() + " avec " + CouleurConsole.printGold("" + winner.getPoints()) + " points");
        }
    }

    private void montrerClassement(ArrayList<Joueur> joueurs) {
        Collections.sort(joueurs);
        System.out.println("\n\n" + CouleurConsole.seperateur2() + CouleurConsole.printTurquoise("Classement apres " + this.nb2Tours + " Tours") + CouleurConsole.seperateur2());
        joueurs.forEach(joueur -> System.out.println(CouleurConsole.tiret() + joueur.getNom() + " a " + CouleurConsole.printGold("" + joueur.getPoints()) + " points"));
    }

    public void setJoueurs(ArrayList<Joueur> joueursAjoutes) {
        joueurs = joueursAjoutes;
    }

    public void initialisePileCartes() {
        this.cartesVisibles.clear();
        if (nombre2Joueur == 4) {
            this.cartesVisibles.add(deck.piocherPersonnage());
            this.cartesVisibles.add(deck.piocherPersonnage());
        }
        if (nombre2Joueur == 5) {
            this.cartesVisibles.add(deck.piocherPersonnage());
        }
        System.out.print("Carte Visible: ");
        this.cartesVisibles.forEach(cp -> System.out.print(CouleurConsole.printRed(cp.getNom()) + " "));
        this.carteCachee = deck.piocherPersonnage();
        System.out.println();
    }
}
