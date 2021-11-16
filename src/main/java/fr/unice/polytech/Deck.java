package fr.unice.polytech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private final ArrayList<CarteQuartier> quartiers;
    private ArrayList<CartePersonnage> personnages;

    public Deck() {
        ExcelReader ER = new ExcelReader();
        this.quartiers = ER.recupererQuartiers();
        this.personnages = ER.recupererPersonnage();
        this.melangerQuartiers();
        this.melangerPersonnage();
    }

    public void melangerQuartiers() {
        for (CarteQuartier cq : this.quartiers) {
            Collections.shuffle(this.quartiers);
        }
    }

    public void melangerPersonnage() {
        for (CartePersonnage cp : this.personnages) {
            Collections.shuffle(this.personnages);
        }
    }

    public CarteQuartier piocherQuartier() {
        if (!this.quartiers.isEmpty()) {
            return this.quartiers.remove(0);
        }
        System.out.println("Plus de Quartiers");
        return null;
    }

    public CartePersonnage piocherPersonnage() {
        Random random = new Random();
        int nb;
        nb = random.nextInt(7);
        if (!this.personnages.isEmpty()) {
            return this.personnages.remove(nb);
        }
        System.out.println("Plus de Personnages");
        return null;
    }

    public void ajoutePersonnage(CartePersonnage personnage) {
        if (!this.personnages.contains(personnage)) {
            this.personnages.add(personnage);
        }
    }

    public void recreerDeckPersonnages(){
        ExcelReader ER = new ExcelReader();
        this.personnages=ER.recupererPersonnage();
    }

    @Override
    public String toString() {
        return "Jeu2Carte{" +
                "quartiers=" + quartiers +
                ", personnages=" + personnages +
                '}';
    }
}
