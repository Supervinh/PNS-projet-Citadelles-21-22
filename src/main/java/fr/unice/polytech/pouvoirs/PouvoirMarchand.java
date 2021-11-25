package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

public class PouvoirMarchand implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        int montant = 1;
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a pioché " + CouleurConsole.printGold("" + montant) + " pièce d'" + CouleurConsole.printGold("or") + " supplémentaire");
        joueur.ajouteOr(montant);
        this.recupererTaxes(joueur);
    }
}
