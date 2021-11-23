package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

public class CartePersonnage {
    private final double id;
    private final String nom;
    private final String gemme;
    private final String description;
    private final String article;

    public CartePersonnage(double id, String nom, String gemme) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.description = "";
        this.article = "";
    }

    public CartePersonnage(double id, String nom, String gemme, String description) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.description = description;
        this.article = "";
    }

    public CartePersonnage(double id, String nom, String gemme, String description, String article) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.description = description;
        this.article = article;
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

    public String getArticle() {
        return article;
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
