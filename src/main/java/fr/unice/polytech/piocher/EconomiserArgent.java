package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

public class EconomiserArgent implements IPiocher {

    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.piocherOr();
    }

    @Override
    public String nomStrategie() {
        return "Economiser Argent";
    }
}
