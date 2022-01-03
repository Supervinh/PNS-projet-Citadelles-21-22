package fr.unice.polytech;

import java.util.logging.Level;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {
    public static void main(String[] args) {
        Statistique statistique = new Statistique();
        MoteurDeJeu.setMessageLvl(Level.OFF);
        System.out.println("Meilleur Bot Contre default Bots");
        MoteurDeJeu mj;
        for (int i = 0; i < 100; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            statistique.ajoutGagnant(mj.obtenirGagnant(MoteurDeJeu.joueurs));
        }

        statistique.ajoutAuxCSVReader();

        System.exit(0);
        System.out.println("Meilleur Bot Contre lui-même");
        for (int i = 0; i < 1000; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            statistique.ajoutGagnant(mj.obtenirGagnant(MoteurDeJeu.joueurs));
        }
    }
}
