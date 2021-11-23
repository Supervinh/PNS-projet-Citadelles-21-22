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

    public void recupererTaxes(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| ") + "Recuperation des " + CouleurConsole.printGold("Taxes") + " des Gemmes " + CouleurConsole.printPurple(joueur.getPersonnage().getGemme()));
        int count = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals(joueur.getPersonnage().getGemme())).count();
        joueur.ajouteOr(count);
        boolean plurielle = count > 1;
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a pioché " + CouleurConsole.printGold("" + count) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("or") + " supplémentaire" + (plurielle ? "s" : "") + ".");
    }
}
