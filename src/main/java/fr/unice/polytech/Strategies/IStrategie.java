package fr.unice.polytech.Strategies;

import fr.unice.polytech.Joueur;

public interface IStrategie {

    void utiliserStrategie(Joueur joueur);

    String nomStrategie();
}