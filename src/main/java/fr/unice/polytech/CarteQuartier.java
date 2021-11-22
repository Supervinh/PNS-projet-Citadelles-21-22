package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

public class CarteQuartier {
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
        this.description = null;
    }

    public CarteQuartier(double id, String nom, String gemme, double prix, String description) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.prix = (int) prix;
        this.description = description;
    }

    public double getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getGemme() {
        return gemme;
    }

    public int getPrix() {
        return prix;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CarteQuartier{" +
                "id=" + CouleurConsole.printTurquoise("" + id) +
                ", nom=" + CouleurConsole.printGreen(nom) +
                ", gemme=" + CouleurConsole.printPurple(gemme) +
                ", prix=" + CouleurConsole.printGold("" + prix) +
                ", description=" + CouleurConsole.printGrey(description) +
                '}';
    }
}
