package fr.unice.polytech;

import java.util.logging.Level;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {
    public static int nombrePartie = 1000;

    public static void main(String[] args) {
        MoteurDeJeu mj;
        MoteurDeJeu.setMessageLvl(Level.INFO);

        Affichage.citadelle();
        Statistique statistique = new Statistique("Meilleure Bot contre les autres");
        long startTime = System.nanoTime();
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();
        Affichage.chrono(startTime);

        startTime = System.nanoTime();
        statistique = new Statistique("Meilleure Bot contre lui-même");
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();
        Affichage.chrono(startTime);
    }
}
