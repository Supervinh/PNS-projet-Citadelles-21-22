package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.Collections;
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
    private int nb2Tours = 0;

    public MoteurDeJeu() {
        System.out.println(this.hello());
        System.out.println(deck);

        this.initialiseJoueur();
        while (this.pasFini()) {
            System.out.println("\n" + cc.seperateur2() + CouleurConsole.RESET + "Tour " + ++this.nb2Tours + cc.seperateur2());
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
        return cc.seperateur1() + "Citadelle Grp.H - Jeux entre Bots" + cc.seperateur1();
    }

    public void initialiseJoueur() {
        System.out.println("\n" + cc.seperateur1() + CouleurConsole.RESET + "Entrez Nom des Joueurs" + cc.seperateur1());
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            System.out.println(cc.tire() + "Joueur " + i + ": CPU" + i);
            this.joueurs.add(new Joueur(CouleurConsole.CYAN_BOLD + "CPU" + i + CouleurConsole.RESET));
        }
    }

    public boolean pasFini() {
        return (this.joueurs.stream().anyMatch(joueur -> joueur.getQuartiersConstruits().size() < MoteurDeJeu.nombre2QuartiersAConstruire));
    }

    public void tour2Jeu(Joueur joueur) {
        System.out.println("\n" + cc.seperateur1() + CouleurConsole.RESET + "Tour de " + joueur.getNom() + cc.seperateur1());
        System.out.println(joueur.getNom2Strategie());
        joueur.jouer();

       /*if (joueur.getQuartiers().size() == 0) {
            joueur.piocherQuartier();
        } else {
            joueur.piocherOr();
        }
        joueur.construireQuartier();*/
    }

    public void obtenirGagnant() {
        this.joueurs.forEach(Joueur::calculePoints);
        int maxScore = this.joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
        Joueur winner = this.joueurs.stream().filter(joueur -> joueur.getPoints() == maxScore).findFirst().orElse(null);
        if (winner != null) {
            System.out.println("\nLe " + CouleurConsole.RED + "Gagnant" + CouleurConsole.RESET + " est: " + winner.getNom() + " avec " + CouleurConsole.YELLOW_BRIGHT + winner.getPoints() + CouleurConsole.RESET + " points");
        } else {
            System.out.println("\nPas de " + CouleurConsole.RED + "Gagnant" + CouleurConsole.RESET);
        }
        this.montrerClassement();
    }

    public void montrerClassement() {
        Collections.sort(this.joueurs);
        System.out.println("\n" + cc.seperateur2() + CouleurConsole.CYAN_BRIGHT + "Classement apres " + this.nb2Tours + " Tours" + cc.seperateur2());
        this.joueurs.forEach(joueur -> System.out.println(cc.tire() + joueur.getNom() + " a " + CouleurConsole.YELLOW_BRIGHT + joueur.getPoints() + CouleurConsole.RESET + " points"));
    }
}
