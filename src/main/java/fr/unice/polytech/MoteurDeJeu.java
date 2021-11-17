package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MoteurDeJeu {

    public static Deck deck = new Deck();
    public static int nombre2Joueur = 2;
    public static int or2Depart = 2;
    public static int orAPiocher = 2;
    public static int carte2Depart = 4;
    public static int carteAPiocher = 1;
    public static int nombre2QuartiersAConstruire = 8;
    public static CouleurConsole cc = new CouleurConsole();
    public ArrayList<Joueur> joueurs = new ArrayList<>();

    public MoteurDeJeu() {
        System.out.println(this.hello());
        System.out.println(deck);

        this.initialiseJoueur();
        int nb2Tours = 0;
        while (this.pasFini()) {
            System.out.println("\n##### Tour " + ++nb2Tours + " #####");
            this.joueurs.forEach(Joueur::piocherPersonnage);
            for (Joueur joueur : this.joueurs) { //Chaque joueur joue les uns après les autres
                this.tour2Jeu(joueur);
            }
            this.joueurs.forEach(joueur -> deck.ajoutePersonnage(joueur.getPersonnage()));
            System.out.println("\n" + this.joueurs);
        }
        this.obtenirGagnant();

    }

    public static void pause(int x) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(x);
    }

    public String hello() {
        return "Citadelle Grp.H - Jeux entre Bots";
    }

    public void initialiseJoueur() {
        System.out.println("\n" + cc.seperateur1() + CouleurConsole.WHITE_BRIGHT + "Entrez Nom des Joueurs" + cc.seperateur1());
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            System.out.println(" - Joueur " + i + ": CPU" + i);
            this.joueurs.add(new Joueur("CPU" + i));
        }
    }

    public boolean pasFini() {
        return (this.joueurs.stream().anyMatch(joueur -> joueur.getQuartiersConstruits().size() < MoteurDeJeu.nombre2QuartiersAConstruire));
    }

    public void tour2Jeu(Joueur joueur) {
        System.out.println("\n### Tour de " + joueur.getNom() + " ###");
        if (joueur.getQuartiers().size() == 0) {
            joueur.piocherQuartier();
        } else {
            joueur.piocherOr();
        }
        joueur.construireQuartier();
    }

    public void obtenirGagnant() {
        this.joueurs.forEach(Joueur::calculePoints);
        int maxScore = this.joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
        Joueur winner = this.joueurs.stream().filter(joueur -> joueur.getPoints() == maxScore).findFirst().orElse(null);
        if (winner != null) {
            System.out.println("\nLe Gagnant est: " + winner.getNom() + " avec " + winner.getPoints() + " points");
        } else {
            System.out.println("\nPas de Gagnant");
        }
    }
}
