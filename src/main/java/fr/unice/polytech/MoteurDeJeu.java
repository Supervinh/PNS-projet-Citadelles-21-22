package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MoteurDeJeu {

    public static Deck deck;
    public static int nombre2Joueur = 5;
    public static int or2Depart = 2;
    public static int orAPiocher = 2;
    public static int carte2Depart = 4;
    public static int carteAPiocher = 1;
    public static int nombre2QuartiersAConstruire = 8;
    public static ArrayList<Joueur> joueurs;
    private int nb2Tours = 0;
    private int roiIndex = 0;
    private boolean avaitRoi = true;
    private final int nombre2Personnages;

    public MoteurDeJeu() {
        deck = new Deck();
        joueurs = new ArrayList<>();
        this.nombre2Personnages = deck.getPersonnages().size();
    }

    public void jouer() {
        this.hello();
        System.out.println(deck);
        this.initialiseJoueur(joueurs);
        this.lancerJeux(joueurs);
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

    void initialiseJoueur(ArrayList<Joueur> joueurs) {
        System.out.println("\n" + CouleurConsole.seperateur1() + "Entrez Nom des Joueurs" + CouleurConsole.seperateur1());
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            System.out.println(CouleurConsole.tire() + "Joueur " + i + ": " + CouleurConsole.printCyan("CPU" + i));
            joueurs.add(new Joueur(CouleurConsole.printCyan("CPU" + i)));
            joueurs.get(i-1).setId(i);
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

    void piocherPersonnage(ArrayList<Joueur> joueurs) {
        for (int i = this.roiIndex; i < joueurs.size(); i++) {
            joueurs.get(i).piocherPersonnage();
        }
        for (int i = 0; i < this.roiIndex; i++) {
            joueurs.get(i).piocherPersonnage();
        }
    }

    void jouerDansLOrdreDesPersonnages(ArrayList<Joueur> joueurs) {
        for (int i = 1; i <= this.nombre2Personnages; i++) {
            for (Joueur joueur : joueurs) {
                if (joueur.getPersonnage().getId() == i) this.tour2Jeu(joueur);
            }
        }
        // System.out.println("\n" + joueurs);
        this.avaitRoi = joueurs.stream().anyMatch(joueur -> joueur.getPersonnage().getNom().equals("Roi") && !joueur.isEstTue());
        joueurs.forEach(joueur -> deck.ajoutePersonnage(joueur.getPersonnage()));
    }

    void tour2Jeu(Joueur joueur) {
        System.out.println("\n\n" + CouleurConsole.seperateur1() + "Tour de " + joueur.getNom() + CouleurConsole.seperateur1());
        joueur.jouer();
        if (joueur.getQuartiersConstruits().size() >= MoteurDeJeu.nombre2QuartiersAConstruire && joueurs.stream().noneMatch(Joueur::isFirst)) {
            joueur.setFirst(true);
            System.out.println("\n" + joueur.getNom() + " a fini en " + CouleurConsole.printBlue("Premier"));
        }
        joueur.calculePoints();
    }

    void lancerJeux(ArrayList<Joueur> joueurs) {
        while (joueurs.stream().noneMatch(Joueur::isFirst)) {
            System.out.println("\n\n\n" + CouleurConsole.seperateur2() + "Tour " + ++this.nb2Tours + CouleurConsole.seperateur2());
            this.trouverQuiEstRoi(joueurs);
            this.piocherPersonnage(joueurs);
            this.jouerDansLOrdreDesPersonnages(joueurs);
        }
    }

    int obtenirMaxPoints(ArrayList<Joueur> joueurs){
        int maxScore = joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
        return maxScore;
    }

    ArrayList<Joueur> obtenirGagnant(ArrayList<Joueur> joueurs) {
        ArrayList<Joueur> winners = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPoints() == obtenirMaxPoints(joueurs)).toList());
        return winners;
    }

    private void printGagnant(ArrayList<Joueur> joueurs){
        ArrayList<Joueur> winners = obtenirGagnant(joueurs);
        System.out.println("\n");
        switch (winners.size()) {
            case 0 -> System.out.println("Pas de " + CouleurConsole.printRed("Gagnant"));
            case 1 -> System.out.print("Le " + CouleurConsole.printRed("Gagnant") + " est ");
            default -> System.out.println("Les " + CouleurConsole.printRed("Gagnants") + " sont: ");
        }
        winners.forEach(winner -> System.out.println(winner.getNom() + " avec " + CouleurConsole.printGold("" + winner.getPoints()) + " points"));
    }


    void montrerClassement(ArrayList<Joueur> joueurs) {
        Collections.sort(joueurs);
        System.out.println("\n\n" + CouleurConsole.seperateur2() + CouleurConsole.printTurquoise("Classement apres " + this.nb2Tours + " Tours") + CouleurConsole.seperateur2());
        joueurs.forEach(joueur -> System.out.println(CouleurConsole.tire() + joueur.getNom() + " a " + CouleurConsole.printGold("" + joueur.getPoints()) + " points"));
    }

    boolean joueursDifferents(ArrayList<Joueur> joueurs){
        Set<Joueur> setjoueurs = new HashSet<>();
        for(Joueur joueur : joueurs){
            if(setjoueurs.add(joueur)==false){
                return false;
            }
            setjoueurs.add(joueur);
        }
        return true;}
}
