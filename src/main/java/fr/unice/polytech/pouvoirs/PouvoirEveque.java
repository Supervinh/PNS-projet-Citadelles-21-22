package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;

/**
 * Le pouvoir de l'évêque permet au personnage de récupérer de l'or pour tous ses quartiers religieux de construit.
 */
public class PouvoirEveque implements IPouvoir {

    /**
     * On récupère les taxes pour les quartiers religieux de construit.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        this.recupererTaxes(joueur);
    }
}
