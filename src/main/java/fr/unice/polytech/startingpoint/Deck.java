package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Deck {
    private ArrayList<CarteQuartier> quartiers;
    private ArrayList<CartePersonnage> personnages;

    public Deck() {
        ExcelReader ER = new ExcelReader();
        this.quartiers = ER.recupererQuartiers();
        this.personnages = ER.recupererPersonnage();
    }

    @Override
    public String toString() {
        return "Jeu2Carte{" +
                "quartiers=" + quartiers +
                ", personnages=" + personnages +
                '}';
    }
}
