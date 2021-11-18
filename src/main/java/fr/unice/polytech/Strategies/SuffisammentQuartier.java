package fr.unice.polytech.Strategies;

import fr.unice.polytech.Joueur;

public class SuffisammentQuartier implements IStrategie {

    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.piocherOr();
        joueur.construireQuartier();
    }

    @Override
    public String nomStrategie() {
        return "Suffisamment de Quartiers";
    }
}
