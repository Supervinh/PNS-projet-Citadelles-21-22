package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

public class RechercheMeilleurQuartier implements IPiocher {

    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.piocherQuartier();
    }

    @Override
    public String nomStrategie() {
        return "Recherche Meilleur Quartier";
    }
}
