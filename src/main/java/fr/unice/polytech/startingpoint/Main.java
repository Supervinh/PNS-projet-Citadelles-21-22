package fr.unice.polytech.startingpoint;


public class Main {

    public static Deck deck = new Deck();

    public static String hello() {
        return "Citadelle Grp.H";
    }

    public static void main(String... args) {
        System.out.println(deck);

        Joueur J1 = new Joueur("Test");
        J1.setEstRoi(true);
        System.out.println(J1);
    }

}