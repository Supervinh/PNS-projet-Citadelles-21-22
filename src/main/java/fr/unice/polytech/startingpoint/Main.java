package fr.unice.polytech.startingpoint;

import org.apache.poi.ss.formula.functions.T;

public class Main {


    public static String hello() {
        return "Citadelle Grp.H";
    }

    public static void main(String... args) {
        System.out.println(hello());

        Deck deck = new Deck();
        System.out.println(deck);

        Joueur J1 = new Joueur("Test");
        J1.getOr().ajouterOr(8);
        J1.setEstRoi(true);
        System.out.println(J1);
    }

}