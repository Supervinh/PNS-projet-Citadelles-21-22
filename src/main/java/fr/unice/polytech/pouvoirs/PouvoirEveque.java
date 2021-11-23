package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.concurrent.atomic.AtomicInteger;

public class PouvoirEveque implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        AtomicInteger cpt = new AtomicInteger();
        joueur.getQuartiersConstruits().stream()
                .filter(quartier -> quartier.getGemme().equals("Religion"))
                .forEach(quartier -> {
                    joueur.ajouteOr(1);
                    cpt.getAndIncrement();
                });
        boolean plurielle = cpt.get() > 1;
        System.out.println(joueur.getNom() + " a pioché " + CouleurConsole.printGold("" + cpt) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("or") + " supplémentaire" + (plurielle ? "s" : "") + ".");
    }
}

