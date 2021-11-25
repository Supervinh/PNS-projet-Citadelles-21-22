package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

public interface IPiocher {

    void utiliserStrategie(Joueur joueur);

    String nomStrategie();
}