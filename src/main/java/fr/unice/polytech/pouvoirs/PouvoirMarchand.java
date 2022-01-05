package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.Joueur;

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
        Affichage.pouvoir(joueur);
        Affichage.piocherOrSupp(joueur, false, montant);
        joueur.ajouteOr(montant);
        Affichage.barreRouge();
        this.recupererTaxes(joueur);
    }
}
