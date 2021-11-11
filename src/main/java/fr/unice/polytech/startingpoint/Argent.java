package fr.unice.polytech.startingpoint;

public class Argent {
    private int nombreOr = 0;

    public Argent() {
        this.piocherOr();
    }

    public Argent(int n) {
        this.ajouterOr(n);
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
