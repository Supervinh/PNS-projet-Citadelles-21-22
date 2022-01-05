package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

/**
 * Interface pour les différentes stratégies relative à la pioche.
 */
public interface IPiocher {

    /**
     * Permet d'utiliser la stratégie.
     *
     * @param joueur Le joueur qui utilise la stratégie.
     */
    void utiliserStrategie(Joueur joueur);

    /**
     * Determine le nom de la stratégie.
     *
     * @return Le nom de la stratégie.
     */
    String nomStrategie();
}