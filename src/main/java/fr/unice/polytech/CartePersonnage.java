package fr.unice.polytech;

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
                "id=" + id +
                ", name='" + nom + '\'' +
                ", jewl='" + gemme + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
