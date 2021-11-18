package fr.unice.polytech.Strategies;

import fr.unice.polytech.Joueur;

public class RechercheMeilleurQuartier implements IStrategie {

    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.piocherQuartier();
    }

    @Override
    public String nomStrategie() {
        return "Recherche Meilleur Quartier";
    }
}
