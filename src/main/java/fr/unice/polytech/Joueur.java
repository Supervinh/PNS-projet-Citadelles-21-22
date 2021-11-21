package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Joueur implements Comparable<Joueur> {
    private final String nom;
    private final ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    private final ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();
    private final Strategie strat;
    private int or = 0;
    private CartePersonnage personnage;
    private boolean estRoi = false;
    private boolean estTue = false;
    private boolean first = false;
    private int points = 0;

    public Joueur(String nom) {
        this.nom = nom;
        this.ajouteOr(MoteurDeJeu.or2Depart);
        for (int i = 0; i < MoteurDeJeu.carte2Depart; i++) {
            this.quartiers.add(MoteurDeJeu.deck.piocherQuartier());
        }
        this.strat = new Strategie(this);
    }

    public void piocherQuartier() {
        for (int i = 0; i < MoteurDeJeu.carteAPiocher; i++) {
            CarteQuartier cq = MoteurDeJeu.deck.piocherQuartier();
            System.out.println(this.getNom() + " a pioché: " + cq);
            this.quartiers.add(cq);
        }
    }

    public void piocherPersonnage() {
        this.estTue = false;
        CartePersonnage cp = MoteurDeJeu.deck.piocherPersonnage();
        System.out.println(this.getNom() + " a pioché: " + CouleurConsole.RED + cp.getNom() + CouleurConsole.RESET);
        this.personnage = cp;
    }

    public void construireQuartier() {
        ArrayList<CarteQuartier> quartiersAchetable = this.quartiersConstructible();
        if (quartiersAchetable.size() > 0) {
            System.out.println("Construire Quartier - " + this.getNom() + " a " + this.or + " pieces d'" + CouleurConsole.YELLOW_BOLD_BRIGHT + "or" + CouleurConsole.RESET);
            AtomicInteger i = new AtomicInteger(1);
            System.out.println(MoteurDeJeu.cc.tire() + "Choix 0: Ne pas construire");
            quartiersAchetable.forEach(quartier -> System.out.println(MoteurDeJeu.cc.tire() + "Choix " + (i.getAndIncrement()) + ": " + quartier));
            CarteQuartier choix = quartiersAchetable.get(Math.min(new Random().nextInt(0, quartiersAchetable.size()), quartiersAchetable.size() - 1));
            this.ajouteOr(-1 * choix.getPrix());
            System.out.println(this.getNom() + " a construit: " + choix);
            this.quartiersConstruits.add(choix);
            this.quartiers.remove(choix);
        } else {
            System.out.println(this.getNom() + " n'a pas assez de pieces d'" + CouleurConsole.YELLOW_BOLD_BRIGHT + "or" + CouleurConsole.RESET + " pour construire.");
        }
    }

    public ArrayList<CarteQuartier> quartiersConstructible() {
        return new ArrayList<>(this.quartiers.stream().filter(quartier -> (quartier.getPrix() <= this.or) && (!this.contientQuartier(quartier.getNom()))).toList());
    }

    public boolean contientQuartier(String nom) {
        return this.quartiersConstruits.stream().anyMatch(quartier -> quartier.getNom().equals(nom));
    }

    public int nombre2QuartiersConstructible() {
        return new ArrayList<>(this.quartiers.stream().filter(quartier -> quartier.getPrix() <= this.or).toList()).size();
    }

    public void piocherOr() {
        this.ajouteOr(MoteurDeJeu.orAPiocher);
        System.out.println(this.getNom() + " a pioché: " + CouleurConsole.YELLOW_BOLD_BRIGHT + MoteurDeJeu.orAPiocher + CouleurConsole.RESET + " pieces d'" + CouleurConsole.YELLOW_BOLD_BRIGHT + "or" + CouleurConsole.RESET);
    }

    public void ajouteOr(int n) {
        this.or += n;
    }

    public void calculePoints() {
        this.points = this.quartiersConstruits.stream().mapToInt(CarteQuartier::getPrix).sum();
        this.points += 2 * this.quartiersConstruits.stream().filter(quartier -> quartier.getId() > 25).count();
        if (this.quartiersConstruits.size() >= MoteurDeJeu.nombre2QuartiersAConstruire) {
            this.points += 2;
            if (this.first) {
                this.points += 2;
            }
        }
        if (this.quartiersConstruits.stream().collect(Collectors.groupingBy(CarteQuartier::getGemme, Collectors.counting())).size() >= 5) {
            this.points += 3;
        }
    }

    public void jouer() {
        if (!this.estTue) this.strat.prochainTour();
        else System.out.println(this.getNom() + " est " + CouleurConsole.RED + "Mort" + CouleurConsole.RESET);
    }

    public String getNom2Strategie() {
        return this.strat.toString();
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

    public boolean isEstTue() {
        return estTue;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean b) {
        this.first = b;
    }

    public void tue(Joueur joueur) {
        joueur.estTue = true;
        if (joueur.getPersonnage().getNom().equals("Roi")) joueur.setEstRoi(false);
    }

    public int getPoints() {
        return points;
    }

    public String getNom2QuartierDansListe(ArrayList<CarteQuartier> list) {
        StringBuilder txt = new StringBuilder("[" + CouleurConsole.GREEN);
        for (int i = 0; i < list.size(); i++) {
            txt.append(list.get(i).getNom());
            if (i != list.size() - 1) {
                txt.append(", ");
            }
        }
        return txt.append(CouleurConsole.RESET + "]").toString();
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "nom=" + nom +
                ", or=" + CouleurConsole.YELLOW_BRIGHT + or + CouleurConsole.RESET +
                ", estRoi=" + CouleurConsole.BLUE + estRoi + CouleurConsole.RESET +
                ", personnage=" + CouleurConsole.RED + this.getPersonnage().getNom() + CouleurConsole.RESET +
                ", quartiers=" + this.getNom2QuartierDansListe(quartiers) +
                ", quartiersConstruits=" + this.getNom2QuartierDansListe(quartiersConstruits) +
                '}';
    }

    @Override
    public int compareTo(Joueur j) {
        return j.getPoints() - this.getPoints();
    }
}
