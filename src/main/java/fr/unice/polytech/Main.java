package fr.unice.polytech;

import fr.unice.polytech.lecteurFichiers.CsvReader;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {

    public static void main(String[] args) {
        MoteurDeJeu mj;
        Joueur gagnant = null;
        System.out.println("Meilleur Bot Contre default Bots");
        for (int i = 0; i < 1; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            gagnant = mj.obtenirGagnant(MoteurDeJeu.joueurs);
        }
        System.exit(0);
        System.out.println("Meilleur Bot Contre lui-même");
        for (int i = 0; i < 1000; i++) {
            mj = new MoteurDeJeu();
            mj.jouer();
            gagnant = mj.obtenirGagnant(MoteurDeJeu.joueurs);
        }
    }
}
