package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;

public class PouvoirAssassin implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur, Joueur cible) {
        joueur.tue(cible);
    }
}
