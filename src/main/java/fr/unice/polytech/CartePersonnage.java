package fr.unice.polytech;

public class CartePersonnage {
    private final double id;
    private final String name;
    private final String jewl;
    private final String description;

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

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
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
                ", name='" + name + '\'' +
                ", jewl='" + jewl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
