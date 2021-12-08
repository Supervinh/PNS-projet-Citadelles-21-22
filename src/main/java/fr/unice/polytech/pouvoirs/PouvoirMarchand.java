package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

public class PouvoirMarchand implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        int montant = 1;
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNomColoured()));
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a pioché " + CouleurConsole.printGold("" + montant) + " pièce d'" + CouleurConsole.printGold("Or") + " supplémentaire");
        joueur.ajouteOr(montant);
        System.out.println(CouleurConsole.printRed("|"));
        this.recupererTaxes(joueur);
    }
}
