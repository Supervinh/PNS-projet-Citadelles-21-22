package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;

/**
 * Le pouvoir de l'assassin permet au personnage de tuer un autre personnage.
 */
public class PouvoirAssassin implements IPouvoir {

    /**
     * On sélectionne un personnage. Si le personnage existe on tue le joueur ayant ce personnage sinon il ne se passe rien.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {

        CartePersonnage cibleNomPersonnage = cibleAleatoire(joueur);
        Joueur cible = cibleExistante(cibleNomPersonnage);
        Affichage.pouvoir(joueur);
        if (cible != null) {
            Affichage.aTue(joueur, cible);
            this.tue(cible);
        } else {
            Affichage.essayerDeTuer(joueur, cibleNomPersonnage);
        }
    }

    /**
     * Permet de sélectionner une cible existante, donc un joueur.
     *
     * @param cibleNomPersonnage Une carte de personnage.
     * @return Retourne un joueur.
     */
    public Joueur cibleExistante(CartePersonnage cibleNomPersonnage) {
        return MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().getNom().equals(cibleNomPersonnage.getNom()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Sélectionne un personnage de manière aléatoire, mais pas lui-même.
     *
     * @param joueur Le joueur en question.
     * @return Retourne une carte de personnage.
     */
    public CartePersonnage cibleAleatoire(Joueur joueur) {
        return joueur.getStrategie().getIStrategie().choixDeCibleCartePersonnage(joueur, MoteurDeJeu.deck.getPersonnagesPossibles());
    }

    /**
     * On tue le joueur.
     *
     * @param joueur Le joueur en question.
     */
    public void tue(Joueur joueur) {
        joueur.setMort(true);
        if (joueur.getPersonnage().getNom().equals("Roi")) joueur.setRoi(false);
    }
}
