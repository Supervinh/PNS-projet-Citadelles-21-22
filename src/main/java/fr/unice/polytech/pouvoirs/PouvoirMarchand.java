package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

/**
 * Le pouvoir du marchand permet au personnage de recevoir une pièce d'or supplémentaire et il reçoit les taxes pour les quartiers marchand.
 */
public class PouvoirMarchand implements IPouvoir {

    /**
     * On reçoit une pièce d'or et les taxes pour ses quartiers marchand.
     * @param joueur Le joueur en question.
     */
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
