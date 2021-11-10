package fr.unice.polytech.startingpoint;

import java.util.List;

public class Joueur {
    String nom;
    Argent or;
    CartePersonnage personnage;
    List<CarteQuartier> quartiers;

    Joueur(String nom){
        this.nom = nom;
    }
}
