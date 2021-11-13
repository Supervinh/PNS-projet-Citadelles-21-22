package fr.unice.polytech.startingpoint;

public class Argent {
    private int nombreOr = 0;

    public Argent() {
        this.ajouterOr(2);
    }

    public void ajouterOr(int n) {
        this.nombreOr += n;
    }

    public void retirerOr(int n) { // Peut faire -n dans ajouterOr
        this.nombreOr -= n;
    }

    @Override
    public String toString() {
        return "" + nombreOr;
    }
}
