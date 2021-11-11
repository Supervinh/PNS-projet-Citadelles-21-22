package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    String nom;
    Argent or = new Argent();
    CartePersonnage personnage;
    List<CarteQuartier> quartiers = new ArrayList<>();
    boolean estRoi = false;

    Joueur(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom +
                ", or=" + or +
                ", personnage=" + personnage +
                ", quartiers=" + quartiers +
                ", estRoi=" + estRoi +
                '}';
    }
}
