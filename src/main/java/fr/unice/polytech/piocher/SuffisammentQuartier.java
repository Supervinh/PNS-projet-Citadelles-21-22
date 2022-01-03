package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

/**
 * Permet d'utiliser la stratégie qui pioche de l'or et construit un quartier puisqu'il en a plusieurs dans sa main.
 */
public class SuffisammentQuartier implements IPiocher {

    /**
     * Permet d'utiliser la stratégie, de piocher de l'or et de construire un quartier.
     *
     * @param joueur Le joueur qui utilise la stratégie.
     */
    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.piocherOr();
        joueur.construireQuartier();
    }

    /**
     * Determine le nom de la stratégie.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String nomStrategie() {
        return "Suffisamment de Quartiers";
    }
}
