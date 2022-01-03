package fr.unice.polytech.piocher;

import fr.unice.polytech.Joueur;

/**
 * Permet d'utiliser la stratégie qui économise de l'or.
 */
public class EconomiserArgent implements IPiocher {

    /**
     * Permet d'utiliser la startégie et pioche donc de l'or.
     *
     * @param joueur Le joueur qui utilise cette stratégie.
     */
    @Override
    public void utiliserStrategie(Joueur joueur) {
        joueur.piocherOr();
    }

    /**
     * Determine le nom de la stratégie.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String nomStrategie() {
        return "Economiser Argent";
    }
}
