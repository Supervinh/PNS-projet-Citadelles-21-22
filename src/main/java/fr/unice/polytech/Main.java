package fr.unice.polytech;

import java.util.logging.Level;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {
    public static int nombrePartie = 1000;

    public static void main(String[] args) {
        MoteurDeJeu.nbJoueurs = 7;
        MoteurDeJeu mj;

        MoteurDeJeu.setMessageLvl(Level.ALL);
        Affichage.citadelle();
        long startTime = System.nanoTime();
        Statistique statistique = new Statistique("Meilleure Bot contre les autres");
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.tousLesStrategies();
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();
        Affichage.chrono(startTime);

        MoteurDeJeu.setMessageLvl(Level.INFO);
        startTime = System.nanoTime();
        statistique = new Statistique("Meilleure Bot contre lui-même");
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.seulementMeilleurStrategie();
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();
        Affichage.chrono(startTime);
    }
}