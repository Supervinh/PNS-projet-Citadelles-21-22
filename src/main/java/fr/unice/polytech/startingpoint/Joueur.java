package fr.unice.polytech.startingpoint;

import java.util.List;

public class Joueur {
    String nom;
    Argent or;
    CartePersonnage personnage;
    List<CarteQuartier> quartiers;
    boolean estRoi;

    Joueur(String nom){
        this.nom = nom;
        this.estRoi = false;
    }
}
