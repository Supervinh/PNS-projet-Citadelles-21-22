package fr.unice.polytech.startingpoint;

public class CarteQuartier {
    private double id;
    private String name;
    private String jewl;
    private int price;
    private String description;

    public CarteQuartier(double id, String name, String jewl, double price) {
        this.id = id;
        this.name = name;
        this.jewl = jewl;
        this.price = (int) price;
        this.description = null;
    }
    public CarteQuartier(double id, String name, String jewl, double price, String description) {
        this.id = id;
        this.name = name;
        this.jewl = jewl;
        this.price = (int) price;
        this.description = description;
    }

    @Override
    public String toString() {
        return "CarteQuartier{" +
                "iD=" + id + '\'' +
                ", name='" + name + '\'' +
                ", jewl='" + jewl + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
