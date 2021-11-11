package fr.unice.polytech.startingpoint;

public class CarteQuartier {
    double iD;
    String name;
    int quantity;
    String jewl;
    int price;
    String effects;
    String description;

    public CarteQuartier(double iD, String name, int quantity, String jewl, int price) {
        this.iD = iD;
        this.name = name;
        this.quantity = quantity;
        this.jewl = jewl;
        this.price = price;
        this.effects = null;
        this.description = null;
    }
    public CarteQuartier(double iD, String name, int quantity, String jewl, int price, String effects, String description) {
        this.iD = iD;
        this.name = name;
        this.quantity = quantity;
        this.jewl = jewl;
        this.price = price;
        this.effects = effects;
        this.description = description;
    }

    @Override
    public String toString() {
        return "CarteQuartier{" +
                "iD=" + iD +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", jewl='" + jewl + '\'' +
                ", price=" + price +
                ", effects='" + effects + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
