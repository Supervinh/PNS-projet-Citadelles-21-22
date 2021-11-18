package fr.unice.polytech.Strategies;

import fr.unice.polytech.Joueur;

public class EconomiserArgent implements IStrategie {

    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.piocherOr();
    }

    @Override
    public String nomStrategie() {
        return "Economiser Argent";
    }
}
