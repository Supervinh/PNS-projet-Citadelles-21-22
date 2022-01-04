package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

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
        Affichage.barreRouge();
        this.recupererTaxes(joueur);
    }
}
