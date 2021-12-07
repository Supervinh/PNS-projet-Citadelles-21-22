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

    public String getDescription() {
        return this.description;
    }

    public String getDescriptionColoured() {
        return CouleurConsole.printGrey(this.description);
    }

    public String getArticle() {
        return this.article;
    }

    @Override
    public String toString() {
        return "CartePersonnage{" +
                "id=" + this.getIdColoured() +
                ", nom=" + this.getNomColoured() +
                ", gemme=" + this.getGemmeColoured() +
                ", description=" + this.getDescriptionColoured() +
                '}';
    }
}
