package fr.unice.polytech;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Joueur {
    private final String nom;
    private final ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    private final ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();
    private int or = 0;
    private CartePersonnage personnage;
    private boolean estRoi = false;
    private int points = 0;

    public Joueur(String nom) {
        this.nom = nom;
        this.ajouteOr(MoteurDeJeu.or2Depart);
        for (int i = 0; i < MoteurDeJeu.carte2Depart; i++) {
            this.quartiers.add(MoteurDeJeu.deck.piocherQuartier());
        }
    }

    public void piocherQuartier() {
        for (int i = 0; i < MoteurDeJeu.carteAPiocher; i++) {
            CarteQuartier cq = MoteurDeJeu.deck.piocherQuartier();
            System.out.println(this.getNom() + " a pioché: " + cq);
            this.quartiers.add(cq);
        }
    }

    public void piocherPersonnage() {
        CartePersonnage cp = MoteurDeJeu.deck.piocherPersonnage();
        System.out.println(this.getNom() + " a pioché: " + cp.getNom());
        this.personnage = cp;

    }

    public void construireQuartier() {
        ArrayList<CarteQuartier> quartiersAchetable = new ArrayList<>(this.quartiers.stream().filter(quartier -> quartier.getPrix() <= this.or).toList());
        if (quartiersAchetable.size() > 0) {
            System.out.println("Construire Quartier -" + this.getNom() + " a " + this.or + " pieces d'or");
            AtomicInteger i = new AtomicInteger(1);
            System.out.println(" - Choix 0: Ne pas construire");
            quartiersAchetable.forEach(quartier -> System.out.println(" - Choix " + (i.getAndIncrement()) + ": " + quartier));
            CarteQuartier choix = quartiersAchetable.get(Math.min(new Random().nextInt(0, quartiersAchetable.size()), quartiersAchetable.size() - 1));
            this.ajouteOr(-1 * choix.getPrix());
            System.out.println(this.getNom() + " a construit: " + choix);
            this.quartiersConstruits.add(choix);
            this.quartiers.remove(choix);
        } else {
            System.out.println(this.getNom() + " n'a pas assez de pieces d'or pour construire.");
        }
    }

    public int nombre2QuartiersConstructible() {
        return new ArrayList<>(this.quartiers.stream().filter(quartier -> quartier.getPrix() <= this.or).toList()).size();
    }

    public void piocherOr() {
        System.out.println(this.getNom() + " a pioché: " + MoteurDeJeu.orAPiocher + " pieces d'or");
        this.ajouteOr(MoteurDeJeu.orAPiocher);
    }

    private void ajouteOr(int n) {
        this.or += n;
    }

    public void calculePoints() {
        this.points = this.quartiersConstruits.stream().mapToInt(CarteQuartier::getPrix).sum();
    }

    public String getNom() {
        return nom;
    }

    public int getOr() {
        return or;
    }

    public CartePersonnage getPersonnage() {
        return personnage;
    }

    public ArrayList<CarteQuartier> getQuartiers() {
        return quartiers;
    }

    public ArrayList<CarteQuartier> getQuartiersConstruits() {
        return quartiersConstruits;
    }

    public boolean isEstRoi() {
        return estRoi;
    }

    public void setEstRoi(boolean b) {
        this.estRoi = b;
    }

    public int getPoints() {
        return points;
    }

    public String getNom2QuartierDansListe(ArrayList<CarteQuartier> list) {
        StringBuilder txt = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            txt.append(list.get(i).getNom());
            if (i != list.size() - 1) {
                txt.append(", ");
            }
        }
        return txt.append("]").toString();
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom +
                ", or=" + or +
                ", estRoi=" + estRoi +
                ", personnage=" + this.getPersonnage().getNom() +
                ", quartiers=" + this.getNom2QuartierDansListe(quartiers) +
                ", quartiersConstruits=" + this.getNom2QuartierDansListe(quartiersConstruits) +
                '}';
    }
}
