package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.concurrent.atomic.AtomicInteger;

public class PouvoirEveque implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        this.recupererTaxes(joueur);
    }
}
