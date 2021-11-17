package fr.unice.polytech;

public class CartePersonnage {
    private final double id;
    private final String nom;
    private final String jewl;
    private final String description;

    public CartePersonnage(double id, String nom, String jewl) {
        this.id = id;
        this.nom = nom;
        this.jewl = jewl;
        this.description = null;
    }

    public CartePersonnage(double id, String nom, String jewl, String description) {
        this.id = id;
        this.nom = nom;
        this.jewl = jewl;
        this.description = description;
    }

    public double getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getJewl() {
        return jewl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CartePersonnage{" +
                "id=" + id +
                ", name='" + nom + '\'' +
                ", jewl='" + jewl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
