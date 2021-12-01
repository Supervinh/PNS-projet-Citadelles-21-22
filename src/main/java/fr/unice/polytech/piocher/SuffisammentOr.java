package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

public class SuffisammentOr implements IPiocher {

    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.ajouterQuartierEnMain();
        joueur.construireQuartier();
    }

    @Override
    public String nomStrategie() {
        return "Suffisamment d'Or";
    }
}
