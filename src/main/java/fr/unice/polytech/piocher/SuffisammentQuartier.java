package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

public class SuffisammentQuartier implements IPiocher {

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
