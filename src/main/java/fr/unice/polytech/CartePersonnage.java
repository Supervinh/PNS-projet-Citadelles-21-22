package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

public class CartePersonnage {
    private final double id;
    private final String nom;
    private final String gemme;
    private final String description;

    public CartePersonnage(double id, String nom, String gemme) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.description = null;
    }

    public CartePersonnage(double id, String nom, String gemme, String description) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
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

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CartePersonnage{" +
                "id=" + CouleurConsole.printTurquoise("" + id) +
                ", nom=" + CouleurConsole.printGreen(nom) +
                ", gemme=" + CouleurConsole.printPurple(gemme) +
                ", description=" + CouleurConsole.printGrey(description) +
                '}';
    }
}
