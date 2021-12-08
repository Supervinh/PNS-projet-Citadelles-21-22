package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

/**
 * Le pouvoir du roi permet au personnage de récupérer la couronne et reçoit les taxes pour ses quartiers noble.
 */
public class PouvoirRoi implements IPouvoir {

    /**
     * On devient roi et on retire la couronne à la personne qui l'avait. Puis on récupère les taxes pour les quartiers noble.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        MoteurDeJeu.joueurs.forEach(j -> j.setRoi(false));
        joueur.setRoi(true);
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNomColoured()));
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " est le Nouveau " + CouleurConsole.printGold("Roi"));
        System.out.println(CouleurConsole.printRed("|"));
        this.recupererTaxes(joueur);
    }
}
