package fr.unice.polytech;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Joueur {
    private final String nom;
    private final List<CarteQuartier> quartiers = new ArrayList<>();
    private final List<CarteQuartier> quartiersConstruit = new ArrayList<>();
    private final boolean estIA;
    private int or = 0;
    private CartePersonnage personnage;
    private boolean estRoi = false;
    private int points = 0;

    public Joueur(String nom) {
        this.nom = nom;
        this.estIA = false;
        this.ajouteOr(MoteurDeJeu.or2Depart);
        for (int i = 0; i < MoteurDeJeu.carte2Depart; i++) {
            this.quartiers.add(MoteurDeJeu.deck.piocherQuartier());
        }
    }

    public Joueur(String nom, boolean b) {
        this.nom = nom;
        this.estIA = b;
        this.ajouteOr(MoteurDeJeu.or2Depart);
        for (int i = 0; i < MoteurDeJeu.carte2Depart; i++) {
            this.quartiers.add(MoteurDeJeu.deck.piocherQuartier());
        }
    }

    public void piocherQuartier() {
        for (int i = 0; i < MoteurDeJeu.carteAPiocher; i++) {
            CarteQuartier cq = MoteurDeJeu.deck.piocherQuartier();
            System.out.println("Vous avez pioché: " + cq);
            this.quartiers.add(cq);
        }
    }

    public void piocherPersonnage() {
        CartePersonnage cp = MoteurDeJeu.deck.piocherPersonnage();
        System.out.println("Vous avez pioché: " + cp.getName());
        this.personnage=cp;

    }

    public void construireQuartier() {
        ArrayList<CarteQuartier> quartiersAchetable = new ArrayList<>(this.quartiers.stream().filter(quartier -> quartier.getPrice() <= this.or).toList());
        if (quartiersAchetable.size() > 0) {
            System.out.println("Construire Quartier - Vous avez " + this.or + " pieces d'or");
            AtomicInteger i = new AtomicInteger(1);
            System.out.println(" - Choix 0: Ne pas construire");
            quartiersAchetable.forEach(quartier -> System.out.println(" - Choix " + (i.getAndIncrement()) + ": " + quartier));
            if (this.estIA) {
                CarteQuartier choix = quartiersAchetable.get(Math.min(new Random().nextInt(0, quartiersAchetable.size()), quartiersAchetable.size() - 1));
                this.ajouteOr(-1 * choix.getPrice());
                System.out.println("Vous avez construit: " + choix);
                this.quartiersConstruit.add(choix);
                this.quartiers.remove(choix);
            } else {
                System.out.print("Entrez le numéro de votre choix: ");
                int numChoix = MoteurDeJeu.sc.nextInt() - 1;
                if (0 <= numChoix && numChoix < quartiersAchetable.size()) {
                    CarteQuartier choix = quartiersAchetable.get(numChoix);
                    this.ajouteOr(-1 * choix.getPrice());
                    System.out.println("Vous avez construit: " + choix);
                    this.quartiersConstruit.add(choix);
                    this.quartiers.remove(choix);
                } else {
                    if (numChoix == -1) {
                        System.out.println("Vous avez choisi de ne pas construire de quartier");
                    } else {
                        System.out.println("Vous avez choisi un choix non proposé. Reessayez\n");
                        this.construireQuartier();
                    }
                }
            }
        } else {
            System.out.println("Vous n'avez pas assez de pieces d'or afin de construire.");
        }
    }

    public int nombre2QuartiersConstructible() {
        return new ArrayList<>(this.quartiers.stream().filter(quartier -> quartier.getPrice() <= this.or).toList()).size();
    }

    public void piocherOr() {
        System.out.println("Vous avez pioché: " + MoteurDeJeu.orAPiocher + " pieces d'or");
        this.ajouteOr(MoteurDeJeu.orAPiocher);
    }

    private void ajouteOr(int n) {
        this.or += n;
    }

    public void calculePoints() {
        this.points = this.quartiersConstruit.stream().mapToInt(CarteQuartier::getPrice).sum();
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

    public List<CarteQuartier> getQuartiers() {
        return quartiers;
    }

    public List<CarteQuartier> getQuartiersConstruit() {
        return quartiersConstruit;
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

    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom +
                ", or=" + or +
                ", personnage=" + personnage +
                ", quartiers=" + quartiers +
                ", quartiersConstruit=" + quartiersConstruit +
                ", estRoi=" + estRoi +
                '}';
    }
}
