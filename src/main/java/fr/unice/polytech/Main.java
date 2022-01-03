package fr.unice.polytech;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Meilleur Bot Contre default Bots");
        for (int i = 0; i < 1; i++) {
            new MoteurDeJeu().jouer();
        }
        System.exit(0);
        System.out.println("Meilleur Bot Contre lui-même");
        for (int i = 0; i < 1000; i++) {
            new MoteurDeJeu().jouer();
        }
    }
}
