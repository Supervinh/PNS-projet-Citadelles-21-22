package fr.unice.polytech;

public class CarteQuartier {
    private final double id;
    private final String name;
    private final String jewl;
    private final int price;
    private final String description;

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

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJewl() {
        return jewl;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
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
