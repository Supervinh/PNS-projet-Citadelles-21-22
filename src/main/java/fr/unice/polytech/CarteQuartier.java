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
                "id=" + CouleurConsole.CYAN_BRIGHT + id + CouleurConsole.RESET +
                ", nom=" + CouleurConsole.GREEN + nom + CouleurConsole.RESET +
                ", gemme=" + CouleurConsole.PURPLE + gemme + CouleurConsole.RESET +
                ", prix=" + CouleurConsole.YELLOW_BRIGHT + prix + CouleurConsole.RESET +
                ", description=" + CouleurConsole.BLACK_BRIGHT + description + CouleurConsole.RESET +
                '}';
    }
}
