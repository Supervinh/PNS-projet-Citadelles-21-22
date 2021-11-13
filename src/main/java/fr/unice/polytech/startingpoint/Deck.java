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

    public CarteQuartier piocherQuartier() {
        if (!this.quartiers.isEmpty()) {
            return this.quartiers.remove(0);
        }
        System.out.println("Plus de Quartiers");
        return null;
    }

    public CartePersonnage piocherPersonnage() {
        if (!this.personnages.isEmpty()) {
            return this.personnages.remove(0);
        }
        System.out.println("Plus de Personnages");
        return null;
    }

    public void ajoutePersonnage(CartePersonnage personnage) {
        if (!this.personnages.contains(personnage)) {
            this.personnages.add(personnage);
        }
    }

    @Override
    public String toString() {
        return "Jeu2Carte{" +
                "quartiers=" + quartiers +
                ", personnages=" + personnages +
                '}';
    }
}
