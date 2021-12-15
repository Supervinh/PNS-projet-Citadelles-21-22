package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

/**
 * Classe permettant d'initialiser les différents quartiers du jeu
 */
public class CarteQuartier implements Comparable<CarteQuartier> {
    private final double id;
    private final String nom;
    private final String gemme;
    private final int prix;
    private final String description;

    public CarteQuartier(double id, String nom, String gemme, double prix) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.prix = (int) prix;
        this.description = "None";
    }

    public CarteQuartier(double id, String nom, String gemme, double prix, String description) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.prix = (int) prix;
        this.description = description;
    }

    public double getId() {
        return this.id;
    }

    public String getIdColoured() {
        return CouleurConsole.printTurquoise("" + this.id);
    }

    public String getNom() {
        return this.nom;
    }

    public String getNomColoured() {
        return CouleurConsole.printGreen(this.nom);
    }

    public String getGemme() {
        return this.gemme;
    }

    public String getGemmeColoured() {
        return CouleurConsole.printPurple(this.gemme);
    }

    public int getPrix() {
        return this.prix;
    }

    public String getPrixColoured() {
        return CouleurConsole.printGold("" + this.prix) + " pièce" + (this.prix > 1 ? "s" : "") + " d'" + CouleurConsole.printGold("Or");
    }

    public String getDescription() {
        return this.description;
    }

    public String getDescriptionColoured() {
        return CouleurConsole.printGrey(this.description);
    }

    @Override
    public String toString() {
        return "CarteQuartier{" +
                "id=" + this.getIdColoured() +
                ", nom=" + this.getNomColoured() +
                ", gemme=" + this.getGemmeColoured() +
                ", prix=" + this.getPrixColoured() +
                ", description=" + this.getDescriptionColoured() +
                '}';
    }

    @Override
    public int compareTo(CarteQuartier cq) {
        return cq.prix - this.prix;
    }
}
