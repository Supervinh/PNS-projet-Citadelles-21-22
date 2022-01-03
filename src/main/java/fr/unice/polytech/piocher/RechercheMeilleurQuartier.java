package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

/**
 * Permet d'utiliser la stratégie de la recherche du meilleur quartier.
 */
public class RechercheMeilleurQuartier implements IPiocher {

    /**
     * Permet d'utiliser la startégie et ajoute le quartier en main.
     *
     * @param joueur Le joueur qui utilise cette stratégie.
     */
    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.ajouterQuartierEnMain();
    }

    /**
     * Determine le nom de la stratégie.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String nomStrategie() {
        return "Recherche Meilleur Quartier";
    }
}
