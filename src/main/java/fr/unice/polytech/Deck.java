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
        this.personnagesPossibles = new ArrayList<>(List.copyOf(this.personnages));
        this.melagerArrayList(this.quartiers);
        this.melagerArrayList(this.personnages);
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

    public void melagerArrayList(ArrayList<?> arrayList) {
        for (Object e : arrayList) {
            Collections.shuffle(arrayList);
        }
    }

    public CarteQuartier piocherQuartier() {
        if (!this.quartiers.isEmpty()) {
            return this.quartiers.remove(0);
        } else {
            System.out.println("Plus de Quartiers...");
            return null;
        }
    }

    public CartePersonnage piocherPersonnage() {
        if (!this.personnages.isEmpty()) {
            return this.personnages.remove(new Random().nextInt(this.personnages.size()));
        } else {
            System.out.println("Plus de Personnages...");
            return null;
        }

    }

    public void ajouterQuartierDeck(CarteQuartier cq) {
        if (!this.quartiers.contains(cq)) {
            this.quartiers.add(cq);
        } else {
            System.out.println("Le Deck Contiens deja: " + cq.getNomColoured());
        }
    }

    public void ajoutePersonnage(CartePersonnage personnage) {
        if (!this.personnages.contains(personnage)) {
            this.personnages.add(personnage);
        } else {
            System.out.println("Le Deck Contiens deja: " + personnage.getNomColoured());
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
