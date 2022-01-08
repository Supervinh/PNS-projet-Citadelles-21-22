package fr.unice.polytech;

import java.util.logging.Level;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {

    public static void main(String[] args) {
        int nombrePartie = 10000;
        MoteurDeJeu mj;

        MoteurDeJeu.nbJoueurs = 7;
        MoteurDeJeu.setMessageLvl(Level.INFO);
        Affichage.citadelle();
        long startTime = System.nanoTime();
        Statistique statistique = new Statistique("Meilleur Bot contre les autres");
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.tousLesStrategies();
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();
        Affichage.chrono(startTime);

        MoteurDeJeu.nbJoueurs = 5;
        MoteurDeJeu.setMessageLvl(Level.INFO);
        startTime = System.nanoTime();
        statistique = new Statistique("Meilleur Bot contre lui-même");
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