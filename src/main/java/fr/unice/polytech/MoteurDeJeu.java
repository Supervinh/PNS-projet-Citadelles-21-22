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
    private final ArrayList<CartePersonnage> cartesVisibles = new ArrayList<>();
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
        this.initialiseJoueurs(joueurs, false);
        this.printJoueursInitialises(joueurs);
        this.lancerTourDeJeu(joueurs);
        this.printGagnant(joueurs);
        this.printClassement(joueurs);
    }

    public void setJoueurs(ArrayList<Joueur> joueursAjoutes) {
        joueurs = joueursAjoutes;
        nombre2Joueur = joueurs.size();
    }

    public void hello() {
        System.out.println(CouleurConsole.printGold(" __  ___ ___  _  ___   ___          ___"));
        System.out.println(CouleurConsole.printGold("/     |   |  | | |  ╲  |    |   |   |") + "    " + CouleurConsole.printBlue("Grp.H"));
        System.out.println(CouleurConsole.printGold("|     |   |  |_| |   | |__  |   |   |__") + "  " + CouleurConsole.printWhite("Jeux de"));
        System.out.println(CouleurConsole.printGold("\\__  _|_  |  | | |__╱  |__  |__ |__ |__") + "  " + CouleurConsole.printRed("Bots IA"));
        System.out.println();
    }

    public void lancerTourDeJeu(ArrayList<Joueur> joueurs) {
        while (joueurs.stream().noneMatch(Joueur::isFirst)) {
            personnagesConnus = new ArrayList<>();
            System.out.println("\n\n\n" + CouleurConsole.seperateur2() + "Tour " + ++this.nb2Tours + CouleurConsole.seperateur2());
            this.trouverQuiEstRoi(joueurs);
            this.piocherPersonnage(joueurs);
            this.jouerDansLOrdreDesPersonnages(joueurs);
        }
    }

    public void jouerDansLOrdreDesPersonnages(ArrayList<Joueur> joueurs) {
        for (int i = 1; i <= this.nombre2Personnages; i++) {
            for (Joueur joueur : joueurs) {
                if (joueur.getPersonnage().getId() == i) this.tour2Jeu(joueur);
            }
        }
        this.avaitRoi = joueurs.stream().anyMatch(joueur -> joueur.getPersonnage().getNom().equals("Roi") && !joueur.isMort());
        joueurs.forEach(joueur -> deck.ajoutePersonnage(joueur.getPersonnage()));

        this.cartesVisibles.forEach(cp -> deck.ajoutePersonnage(cp));
        deck.ajoutePersonnage(this.carteCachee);
    }

    public void tour2Jeu(Joueur joueur) {
        System.out.println("\n\n" + CouleurConsole.seperateur1() + "Tour de " + joueur.getNomColoured() + CouleurConsole.seperateur1());
        joueur.jouer();
        personnagesConnus.add(joueur);
        if (verifieFini(joueur)) {
            System.out.println("\n" + CouleurConsole.printGold("##### ") + joueur.getNomColoured() + " a fini en " + CouleurConsole.printBlue("Premier") + CouleurConsole.printGold(" #####"));
        }
        joueur.calculePoints();
    }

    public void initialiseJoueurs(ArrayList<Joueur> joueurs, boolean nameless) {
        ExcelReader ER = new ExcelReader();
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            if (nameless) {
                joueurs.add(new Joueur());
            } else {
                joueurs.add(new Joueur(ER.getRandomName()));
            }
        }
        joueurs.get(0).setRoi(true);
    }

    public void initialisePileCartes() {
        this.cartesVisibles.clear();
        if (nombre2Joueur == 4) {
            this.choixCartesVisibles();
            this.choixCartesVisibles();
        }
        if (nombre2Joueur == 5) {
            this.choixCartesVisibles();
        }
        System.out.print("Carte Visible: ");
        this.cartesVisibles.forEach(cp -> System.out.print(cp.getNomColoured() + " "));
        this.carteCachee = deck.piocherPersonnage();
        System.out.println();
    }

    private void choixCartesVisibles() {
        CartePersonnage cp = deck.piocherPersonnage();
        if (cp.getNom().equals("Roi")) {
            this.cartesVisibles.add(deck.piocherPersonnage());
            deck.ajoutePersonnage(cp);
        } else {
            this.cartesVisibles.add(cp);
        }
    }

    public void trouverQuiEstRoi(ArrayList<Joueur> joueurs) {
        AtomicInteger k = new AtomicInteger(0);
        this.roiIndex = joueurs.stream().peek(v -> k.getAndIncrement()).anyMatch(Joueur::isRoi) ? k.get() - 1 : this.roiIndex;
        if (!this.avaitRoi) {
            joueurs.get(this.roiIndex).setRoi(false);
            this.roiIndex = (this.roiIndex + 1) % nombre2Joueur;
            joueurs.get(this.roiIndex).setRoi(true);
        }
    }

    public void piocherPersonnage(ArrayList<Joueur> joueurs) {
        initialisePileCartes();
        System.out.println(CouleurConsole.printGreen("\n| Piocher les Personnages"));
        for (int i = this.roiIndex; i < joueurs.size(); i++) {
            System.out.print(CouleurConsole.printGreen("| "));
            joueurs.get(i).piocherPersonnage();
        }
        for (int i = 0; i < this.roiIndex; i++) {
            System.out.print(CouleurConsole.printGreen("| "));
            joueurs.get(i).piocherPersonnage();
        }
    }

    public boolean verifieFini(Joueur joueur) {
        if (joueur.getQuartiersConstruits().size() >= MoteurDeJeu.nombre2QuartiersAConstruire && joueurs.stream().noneMatch(Joueur::isFirst)) {
            joueur.setFirst(true);
            return true;
        }
        return false;
    }

    public int obtenirScoreMax(ArrayList<Joueur> joueurs) {
        return joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
    }

    public Joueur obtenirGagnant(ArrayList<Joueur> joueurs) {
        ArrayList<Joueur> gagnants = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPoints() == obtenirScoreMax(joueurs)).toList());
        if (gagnants.size() > 1) {
            int premierJoueur = gagnants.stream().mapToInt(joueur -> (int) joueur.getPersonnage().getId()).min().orElse(0);
            gagnants = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPersonnage().getId() == premierJoueur).toList());
        }
        return gagnants.get(0);
    }

    public void printGagnant(ArrayList<Joueur> joueurs) {
        Joueur winner = obtenirGagnant(joueurs);
        System.out.println("\n");
        if (winner == null) {
            System.out.println("Pas de " + CouleurConsole.printRed("Gagnant"));
        } else {
            System.out.print("Le " + CouleurConsole.printRed("Gagnant") + " est ");
            System.out.println(winner.getNomColoured() + " avec " + CouleurConsole.printGold("" + winner.getPoints()) + " points");
        }
    }

    public void printJoueursInitialises(ArrayList<Joueur> joueurs) {
        System.out.println("\n" + CouleurConsole.seperateur1() + "Entrez Nom des Joueurs" + CouleurConsole.seperateur1());
        for (int i = 1; i <= joueurs.size(); i++) {
            System.out.println(CouleurConsole.tiret() + "Joueur " + i + ": " + joueurs.get(i - 1).getNomColoured());
        }
    }

    public void printClassement(ArrayList<Joueur> joueurs) {
        Collections.sort(joueurs);
        System.out.println("\n\n" + CouleurConsole.seperateur2() + CouleurConsole.printTurquoise("Classement apres " + this.nb2Tours + " Tours") + CouleurConsole.seperateur2());
        joueurs.forEach(joueur -> System.out.println(CouleurConsole.tiret() + joueur.getNomColoured() + " a " + CouleurConsole.printGold("" + joueur.getPoints()) + " points"));
    }
}
