package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;

import java.util.concurrent.atomic.AtomicInteger;

public class PouvoirMarchand implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        joueur.ajouteOr(1);
        AtomicInteger cpt = new AtomicInteger(1);
        joueur.getQuartiersConstruits().stream()
                .filter(quartier -> quartier.getGemme().equals("Commerce et Artisanat"))
                .forEach(quartier -> {
                    joueur.ajouteOr(1);
                    cpt.getAndIncrement();
                });
        System.out.println(joueur.getNom() + " a pioché " + cpt + " pièce(s) d'or supplémentaire(s).");
    }
}
