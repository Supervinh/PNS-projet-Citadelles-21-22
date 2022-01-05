package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

/**
 * Permet d'utiliser la stratégie qui dit si on a suffisamment d'or.
 */
public class SuffisammentOr implements IPiocher {

    /**
     * Permet d'utiliser la stratégie, ajoute le quartier en main et construit un quartier puisqu'il a suffisamment d'or.
     *
     * @param joueur Le joueur qui utilise cette stratégie.
     */
    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.ajouterQuartierEnMain();
        joueur.construireQuartier();
    }

    /**
     * Determine le nom de la stratégie.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String nomStrategie() {
        return "Suffisamment d'Or";
    }
}
