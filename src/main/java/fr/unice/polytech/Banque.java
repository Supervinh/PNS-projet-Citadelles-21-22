package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

/**
 * La classe de la banque, qui permet le bon nombre d'or en circulation dans le jeu.
 */
public class Banque {
    /**
     * Les fonds de la banque.
     */
    private int fonds;

    /**
     * Le constructeur de la banque qui initialise les fonds à 30.
     */
    public Banque() {
        this.fonds = MoteurDeJeu.piecesEnJeu;
    }

    /**
     * Permet de récupérer les fonds restant dans la banque.
     *
     * @return Les fonds.
     */
    public int getFonds() {
        return this.fonds;
    }

    /**
     * Permet de récupérer les fonds restant dans la banque.
     *
     * @return Les fonds restant dans la banque.
     */
    public String getPieceEnJeu() {
        return "" + this.fonds;
    }

    /**
     * Permet de récupérer les fonds restant dans la banque, mais en couleur pour l'affichage.
     *
     * @return Les fonds restant dans la banque, mais en or.
     */
    public String getPieceEnJeuColoured() {
        return CouleurConsole.gold(this.getPieceEnJeu());
    }

    /**
     * Permet de savoir s'il y a encore de l'or dans la banque.
     *
     * @return Vrai si il y a encore de l'or, faux sinon.
     */
    public boolean resteArgent() {
        return this.fonds > 0;
    }

    /**
     * Permet les transactions d'argent, quand le joueur pioche ou construit un quartier.
     *
     * @param somme La somme piocher ou remis à la banque.
     * @return La somme que le joueur pioche ou remet à la banque.
     */
    public int transaction(int somme) {
        if (this.fonds - somme >= 0) {
            this.fonds -= somme;
        } else {
            somme += this.fonds - somme;
            this.fonds = 0;
        }
        return somme;
    }

    /**
     * Permet de calculer le nombre de pièces d'or dans le jeu.
     *
     * @return Le nombre de pieces d'or.
     */
    private int sommeArgentEnCirculation() {
        return this.fonds + MoteurDeJeu.joueurs.stream().mapToInt(Joueur::getOr).sum();
    }

    /**
     * Vérifie que le nombre de pièces d'or en circulation est correcte, qu'aucun joueur n'a volé ou que l'on a perdu des pièces.
     *
     * @return Vrai si le nombre d'or est égal à 30, faux sinon.
     */
    public boolean sommeArgentCirculationCorrecte() {
        return this.sommeArgentEnCirculation() == MoteurDeJeu.piecesEnJeu;
    }

    /**
     * Affiche les informations sur le nombre de pièces en jeu, il devrait toujours être égal à 30.
     *
     * @return Les informations sur la banque.
     */
    @Override
    public String toString() {
        return "Banque{" +
                "pieceEnJeu=" + this.getPieceEnJeuColoured() +
                '}';
    }
}
