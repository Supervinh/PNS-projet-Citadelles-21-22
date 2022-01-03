package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

/**
 * Le pouvoir du marchand permet au personnage de recevoir une pièce d'or supplémentaire et il reçoit les taxes pour les quartiers marchands.
 */
public class PouvoirMarchand implements IPouvoir {

    /**
     * On reçoit une pièce d'or et les taxes pour ses quartiers marchand.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        int montant = 1;
        System.out.println(CouleurConsole.red("| Pouvoir " + joueur.getPersonnage().getNomColoured()));
        System.out.println(CouleurConsole.red("| ") + joueur.getNomColoured() + " a pioché " + CouleurConsole.gold("" + montant) + " pièce d'" + CouleurConsole.gold("Or") + " supplémentaire");
        joueur.ajouteOr(montant);
        System.out.println(CouleurConsole.red("|"));
        this.recupererTaxes(joueur);
    }
}
