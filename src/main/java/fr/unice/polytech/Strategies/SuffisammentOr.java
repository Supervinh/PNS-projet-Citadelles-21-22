package fr.unice.polytech.Strategies;

import fr.unice.polytech.Joueur;

public class SuffisammentOr implements IStrategie {

    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.piocherQuartier();
        joueur.construireQuartier();
    }

    @Override
    public String nomStrategie() {
        return "Suffisamment d'Or";
    }
}
