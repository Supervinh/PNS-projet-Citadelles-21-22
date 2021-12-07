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
    private final int nombre2Personnages;
    private int nb2Tours = 0;
    private int roiIndex = 0;
    private boolean avaitRoi = true;
    private CartePersonnage carteCachee;
    public CartePersonnage[] cartesVisibles= new CartePersonnage[2] ;
    public static ArrayList<Joueur> personnagesConnus =new ArrayList<>();

    public MoteurDeJeu() {
        deck = new Deck();
        joueurs = new ArrayList<>();
        this.nombre2Personnages = deck.getPersonnages().size();
    }

    public void jouer() {
        this.hello();
        this.initialiseJoueurs(joueurs);
        this.printJoueursInitialises(joueurs);
        this.lancerJeu(joueurs);
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
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            joueurs.add(new Joueur(CouleurConsole.printCyan(new ExcelReader().getRandomName())));
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
        for (int i = this.roiIndex; i < joueurs.size(); i++) {
            joueurPiochePersonnage(joueurs.get(i));
        }
        for (int i = 0; i < this.roiIndex; i++) {
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
        if(MoteurDeJeu.nombre2Joueur<=5){deck.ajoutePersonnage(cartesVisibles[0]);}
        if(MoteurDeJeu.nombre2Joueur==4){deck.ajoutePersonnage(cartesVisibles[1]);}
        deck.ajoutePersonnage(carteCachee);
    }

    void tour2Jeu(Joueur joueur) {
        System.out.println("\n\n" + CouleurConsole.seperateur1() + "Tour de " + joueur.getNom() + CouleurConsole.seperateur1());
        joueur.jouer();
        personnagesConnus.add(joueur);
        if (verifieFini(joueur)) {
            System.out.println("\n" + joueur.getNom() + " a fini en " + CouleurConsole.printBlue("Premier"));
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

    void lancerJeu(ArrayList<Joueur> joueurs) {
        while (joueurs.stream().noneMatch(Joueur::isFirst)) {
            personnagesConnus =new ArrayList<>();
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

    public void initialisePileCartes(){
        if(nombre2Joueur==4){
            cartesVisibles[0]= deck.piocherPersonnage();
            cartesVisibles[1]= deck.piocherPersonnage();
            System.out.println("Cartes visibles: "+cartesVisibles[0].getNom()+cartesVisibles[1].getNom());
        }
        if(nombre2Joueur==5){
            cartesVisibles[0]= deck.piocherPersonnage();
            System.out.println("Carte visible:"+cartesVisibles[0].getNom());
        }
        carteCachee = deck.piocherPersonnage();
    }
}
