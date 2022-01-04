package fr.unice.polytech;

import java.util.logging.Level;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {
    public static int nombrePartie = 1000;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        System.out.println("Meilleur Bot Contre default Bots");
        MoteurDeJeu mj;
        MoteurDeJeu.setMessageLvl(Level.INFO);
        Statistique statistique = new Statistique();

        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();

        long duration = (long) ((System.nanoTime() - startTime) / (double) 1000000000);
        System.out.println("Temps execution: " + duration + "s.");
        System.out.println("Temps par partie: " + duration / (double) Main.nombrePartie + "s/game");
        System.exit(0);

        System.out.println("Meilleur Bot Contre lui-même");
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();
    }
}
