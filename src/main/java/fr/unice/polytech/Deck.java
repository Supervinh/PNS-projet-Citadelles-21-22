package fr.unice.polytech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private final ArrayList<CarteQuartier> quartiers;
    private final ArrayList<CartePersonnage> personnages;
    private final ArrayList<CartePersonnage> personnagesPossibles;

    public Deck() {
        ExcelReader ER = new ExcelReader();
        this.quartiers = ER.recupererQuartiers();
        this.personnages = ER.recupererPersonnage();
        this.melangerQuartiers();
        this.melangerPersonnage();
        this.personnagesPossibles = new ArrayList<>(List.copyOf(this.personnages));
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
        return this.personnages.remove(new Random().nextInt(this.personnages.size()));
    }

    public void ajoutePersonnage(CartePersonnage personnage) {
        if (!this.personnages.contains(personnage)) {
            this.personnages.add(personnage);
        }
    }

    public ArrayList<CarteQuartier> getQuartiers() {
        return quartiers;
    }

    public ArrayList<CartePersonnage> getPersonnages() {
        return personnages;
    }

    public ArrayList<CartePersonnage> getPersonnagesPossibles() {
        return personnagesPossibles;
    }

    @Override
    public String toString() {
        return "Jeu2Carte{" +
                "quartiers=" + quartiers +
                ", personnages=" + personnages +
                '}';
    }
}
