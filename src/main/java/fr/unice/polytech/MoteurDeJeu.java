package fr.unice.polytech;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MoteurDeJeu {

    public static Deck deck = new Deck();
    public static int nombre2Joueur = 2;
    public static int or2Depart = 2;
    public static int orAPiocher = 2;
    public static int carte2Depart = 4;
    public static int carteAPiocher = 1;
    public static int nombre2QuartiersAConstruire = 8;
    public static Scanner sc = new Scanner(System.in);

    public static void pause(int x) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(x);
    }


    public static String hello() {
        return "Citadelle Grp.H - Jeux entre Bots";
    }

    public MoteurDeJeu() {
        System.out.println(MoteurDeJeu.hello());
        System.out.println(deck);

        // Creations des 'nombre2Joueur' Joueurs
        ArrayList<Joueur> list2Joueurs = new ArrayList<>();
        System.out.println("\n### Entrez Nom des Joueurs ###");
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            System.out.println(" - Joueur " + i + ": CPU" + i);
            list2Joueurs.add(new Joueur("CPU" + i));
        }

        int nb2Tours = 0;
        while (list2Joueurs.stream().anyMatch(joueur -> joueur.getQuartiersConstruits().size() < MoteurDeJeu.nombre2QuartiersAConstruire)) {
            System.out.println("\n##### Tour " + ++nb2Tours + " #####");
            list2Joueurs.forEach(Joueur::piocherPersonnage);
            for (Joueur joueur : list2Joueurs) {
                System.out.println("\n### Tour de " + joueur.getNom() + " ###");
                // Actions a faire

                if (joueur.getQuartiers().size() == 0) {
                    joueur.piocherQuartier();
                } else {
                    joueur.piocherOr();
                }
                joueur.construireQuartier();
            }
            list2Joueurs.forEach(joueur -> deck.ajoutePersonnage(joueur.getPersonnage()));
            System.out.println("\n" + list2Joueurs);
        }
        list2Joueurs.forEach(Joueur::calculePoints);
        int maxScore = list2Joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
        Joueur winner = list2Joueurs.stream().filter(joueur -> joueur.getPoints() == maxScore).findFirst().orElse(null);
        if (winner != null) {
            System.out.println("\nLe Gagnant est: " + winner.getNom() + " avec " + winner.getPoints() + " points");
        } else {
            System.out.println("\nPas de Gagnant");
        }
    }
}
