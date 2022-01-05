package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;

/**
 * Le pouvoir du roi permet au personnage de récupérer la couronne et reçoit les taxes pour les quartiers nobles.
 */
public class PouvoirRoi implements IPouvoir {

    /**
     * On devient roi et on retire la couronne à la personne qui l'avait. Puis on récupère les taxes pour les quartiers nobles.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        MoteurDeJeu.joueurs.forEach(j -> j.setRoi(false));
        joueur.setRoi(true);
        Affichage.pouvoir(joueur);
        Affichage.estRoi(joueur);
        Affichage.barreRouge();
        this.recupererTaxes(joueur);
    }
}
