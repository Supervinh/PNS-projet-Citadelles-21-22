package fr.unice.polytech;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            MoteurDeJeu m = new MoteurDeJeu();
            m.jouer();
        }
    }
}
