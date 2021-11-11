package fr.unice.polytech.startingpoint;

public class Argent {
    int nombreOr;


    public Argent() {
        this.nombreOr = 2;
    }

    public void piocherOr() {
        this.nombreOr += 2;
    }

    public void ajouterOr(int n) {
        this.nombreOr += n;
    }

    @Override
    public String toString() {
        return "" + nombreOr;
    }
}
