package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

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

    public String getIdColoured() {
        return CouleurConsole.printTurquoise("" + this.id);
    }

    public String getNom() {
        return nom;
    }

    public String getNomColoured() {
        return CouleurConsole.printGreen(this.nom);
    }

    public String getGemme() {
        return gemme;
    }

    public String getGemmeColoured() {
        return CouleurConsole.printPurple(this.gemme);
    }

    public int getPrix() {
        return prix;
    }

    public String getPrixColoured() {
        return CouleurConsole.printGold("" + prix) + "pieces d'" + CouleurConsole.printGold("Or");
    }

    public String getDescription() {
        return description;
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
