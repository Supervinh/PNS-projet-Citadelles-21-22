package fr.unice.polytech.startingpoint;

public class CartePersonnage {
    private double id;
    private String name;
    private String jewl;
    private String description;

    public CartePersonnage(double id, String name, String jewl) {
        this.id = id;
        this.name = name;
        this.jewl = jewl;
        this.description = null;
    }

    public CartePersonnage(double id, String name, String jewl, String description) {
        this.id = id;
        this.name = name;
        this.jewl = jewl;
        this.description = description;
    }

    @Override
    public String toString() {
        return "CartePersonnage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jewl='" + jewl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
