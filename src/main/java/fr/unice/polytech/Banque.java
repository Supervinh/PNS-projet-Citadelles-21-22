package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

public class Banque {
    private int fonds;

    public Banque() {
        this.fonds = MoteurDeJeu.piecesEnJeu;
    }

    public int getFonds() {
        return this.fonds;
    }

    public String getPieceEnJeuColoured() {
        return CouleurConsole.printGold("" + this.fonds);
    }

    public boolean resteArgent() {
        return this.fonds > 0;
    }

    public int transaction(int somme) {
        if (this.fonds - somme >= 0) {
            this.fonds -= somme;
        } else {
            somme += this.fonds - somme;
            this.fonds = 0;
        }
        return somme;
    }

    public int sommeArgentEnCirculation() {
        return this.fonds + MoteurDeJeu.joueurs.stream().mapToInt(Joueur::getOr).sum();
    }

    public boolean sommeArgentCirculationCorrecte() {
        return this.sommeArgentEnCirculation() == MoteurDeJeu.piecesEnJeu;
    }

    @Override
    public String toString() {
        return "Banque{" +
                "pieceEnJeu=" + this.getPieceEnJeuColoured() +
                '}';
    }
}
