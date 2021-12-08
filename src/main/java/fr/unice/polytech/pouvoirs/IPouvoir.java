package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;


/**
 * Interface pour les différents pouvoirs des personnages.
 */
public interface IPouvoir {

    /**
     * Méthode abstraite pour utiliser les pouvoirs des personnages.
     *
     * @param joueur Le joueur en question.
     */
    void utiliserPouvoir(Joueur joueur);

    /**
     * Permet de récupérer les taxes par rapport à des cartes et des pouvoirs spécifiques.
     * Par exemple, le roi recupère une pièce d'or pour chaque quartier noble qu'il possède.
     *
     * @param joueur Le joueur en question.
     */
    default void recupererTaxes(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| ") + "Recuperation des " + CouleurConsole.printGold("Taxes") + " des Gemmes " + joueur.getPersonnage().getGemmeColoured());
        int count = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals(joueur.getPersonnage().getGemme())).count();
        if (joueur.contientQuartier("École de magie")) {
            count += 1;
        }
        joueur.ajouteOr(count);
        boolean plurielle = count > 1;
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a pioché " + CouleurConsole.printGold("" + count) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("Or") + " supplémentaire" + (plurielle ? "s" : "") + ".");
    }
}
