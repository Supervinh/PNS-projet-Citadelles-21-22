package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.cartes.CarteQuartier;

/**
 * Le pouvoir de l'architecte permet au personnage de piocher plus de quartiers.
 */
public class PouvoirArchitecte implements IPouvoir {

    /**
     * Le joueur pioche deux cartes quartiers supplémentaires.
     * Si le joueur a suffisamment de quartiers et suffisamment d'or il utilise les stratégies correspondantes
     * et construit des quartiers qu'il a en main.
     * Si on ne peut pas construire de quartier on affiche qu'on ne peut plus construire.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        int n = 2;
        Affichage.pouvoir(joueur);
        boolean construire = false;

        for (int i = 0; i < n; i++) {
            CarteQuartier cq = joueur.piocherQuartier();
            if (cq != null) {
                joueur.getQuartiers().add(cq);
            }
        }

        for (int i = 0; i < n; i++) {
            joueur.getStrategie().choisirType2Piochage();
            if (joueur.nombre2QuartiersConstructible() > 0 || joueur.getNomStrategie().equals("Suffisamment d'Or")) {
                joueur.construireQuartier();
                construire = true;
            }
        }

        if (!construire) {
            Affichage.plusQuartierSupp();
        }
    }
}