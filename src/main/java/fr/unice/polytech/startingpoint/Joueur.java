package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private final String nom;
    private final Argent or = new Argent();
    private CartePersonnage personnage;
    private final List<CarteQuartier> quartiers = new ArrayList<>();
    private boolean estRoi = false;

    public Joueur(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public Argent getOr() {
        return or;
    }

    public CartePersonnage getPersonnage() {
        return personnage;
    }

    public List<CarteQuartier> getQuartiers() {
        return quartiers;
    }

    public boolean isEstRoi() {
        return estRoi;
    }

    public void setEstRoi(boolean b) {
        this.estRoi = b;
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
