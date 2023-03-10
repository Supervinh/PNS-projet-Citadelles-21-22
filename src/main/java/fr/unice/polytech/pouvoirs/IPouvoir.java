package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.Joueur;


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
     * Par exemple, le roi récupère une pièce d'or pour chaque quartier noble qu'il possède.
     *
     * @param joueur Le joueur en question.
     */
    default void recupererTaxes(Joueur joueur) {
        Affichage.recuperationGemmes(joueur);
        int count = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals(joueur.getPersonnage().getGemme())).count();
        if (joueur.contientQuartier("École de magie")) {
            count += 1;
        }
        joueur.ajouteOr(count);
        boolean plurielle = count > 1;
        Affichage.piocherOrSupp(joueur, plurielle, count);
    }
}
