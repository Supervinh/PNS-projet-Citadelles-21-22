package fr.unice.polytech;

import java.util.logging.Level;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {
    public static int nombrePartie = 1000;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        MoteurDeJeu mj;
        MoteurDeJeu.setMessageLvl(Level.FINER);
        Statistique statistique = new Statistique();

        System.out.println("Meilleur Bot Contre default Bots");
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();

        Affichage.chrono(startTime);
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
