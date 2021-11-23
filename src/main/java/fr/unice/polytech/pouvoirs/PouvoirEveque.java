package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;

public class PouvoirEveque implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        joueur.getQuartiersConstruits().stream()
                .filter(quartier -> quartier.getGemme().equals("Religion"))
                .forEach(quartier -> joueur.ajouteOr(1));
    }
}
