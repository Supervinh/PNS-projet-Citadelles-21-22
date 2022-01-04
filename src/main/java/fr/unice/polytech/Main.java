package fr.unice.polytech;

import java.util.logging.Level;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {
    public static int nombrePartie = 1000;

    public static void main(String[] args) {
        System.out.println("Meilleur Bot Contre default Bots");
        MoteurDeJeu mj;
        Statistique statistique = new Statistique();

        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.setMessageLvl(Level.WARNING);
            mj.jouer();
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();

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
