package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Joueur implements Comparable<Joueur> {
    private final String nom;
    private final ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();
    private final Strategie strat;
    private ArrayList<CarteQuartier> quartiers = new ArrayList<>();
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
        System.out.println(this.getNom() + " a pioché: " + CouleurConsole.printRed(cp.getNom()));
        this.personnage = cp;
    }

    public void construireQuartier() {
        ArrayList<CarteQuartier> quartiersAchetable = this.quartiersConstructible();
        if (quartiersAchetable.size() > 0) {
            System.out.println("\n" + CouleurConsole.printPink("| Construire Quartier") + " - " + this.getNom() + " a " + this.or + " pieces d'" + CouleurConsole.printGold("or"));
            AtomicInteger i = new AtomicInteger(1);
            System.out.println(CouleurConsole.printPink("| ") + CouleurConsole.tire() + "Choix 0: Ne pas construire");
            quartiersAchetable.forEach(quartier -> System.out.println(CouleurConsole.printPink("| ") + CouleurConsole.tire() + "Choix " + (i.getAndIncrement()) + ": " + quartier));
            CarteQuartier choix = quartiersAchetable.get(Math.min(new Random().nextInt(0, quartiersAchetable.size()), quartiersAchetable.size() - 1));
            this.ajouteOr(-1 * choix.getPrix());
            System.out.println(CouleurConsole.printPink("| ") + this.getNom() + " a construit: " + choix);
            this.quartiersConstruits.add(choix);
            this.quartiers.remove(choix);
        } else {
            System.out.println(CouleurConsole.printPink("| ") + this.getNom() + " n'a pas assez de pieces d'" + CouleurConsole.printGold("or") + " pour construire.");
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
        System.out.println(this.getNom() + " a pioché: " + CouleurConsole.printGold("" + MoteurDeJeu.orAPiocher) + " pieces d'" + CouleurConsole.printGold("or"));
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
        this.printDetails();
        if (!this.estTue) this.strat.prochainTour();
        else System.out.println(this.getNom() + " est " + CouleurConsole.printRed("Mort"));
    }

    private void printDetails() {
        System.out.println();
        System.out.println(CouleurConsole.printBlue("| Details Joueur"));
        System.out.println(CouleurConsole.printBlue("| ") + "Personnage: " + CouleurConsole.printRed(this.getPersonnage().getNom()));
        System.out.println(CouleurConsole.printBlue("| ") + "Pièces d'or: " + CouleurConsole.printGold("" + this.getOr()));
        System.out.println(CouleurConsole.printBlue("| ") + "Stratégie: " + CouleurConsole.printPurple(this.getNom2Strategie()));
        System.out.println(CouleurConsole.printBlue("| ") + "Quartiers dans la main: " + this.getQuartiers().stream().map(CarteQuartier::getNom).map(CouleurConsole::printGreen).toList());
        System.out.println(CouleurConsole.printBlue("| ") + "Quartiers construit: " + this.getQuartiersConstruits().stream().map(CarteQuartier::getNom).map(CouleurConsole::printGreen).toList());
        System.out.println();
    }

    public String getNom2Strategie() {
        return this.strat.getiStrategie().nomStrategie();
    }

    public String getNom() {
        return nom;
    }

    public int getOr() {
        return or;
    }

    public void setOr(int or) {
        this.or = or;
    }

    public void setPersonnage(CartePersonnage personnage) {this.personnage = personnage; }

    public CartePersonnage getPersonnage() {
        return personnage;
    }

    public ArrayList<CarteQuartier> getQuartiers() {
        return quartiers;
    }

    public void setQuartiers(ArrayList<CarteQuartier> quartiers) {
        this.quartiers = quartiers;
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
                ", or=" + CouleurConsole.printGold("" + or) +
                ", estRoi=" + CouleurConsole.printBlue("" + estRoi) +
                ", personnage=" + CouleurConsole.printRed(this.getPersonnage().getNom()) +
                ", quartiers=" + this.getNom2QuartierDansListe(quartiers) +
                ", quartiersConstruits=" + this.getNom2QuartierDansListe(quartiersConstruits) +
                '}';
    }

    @Override
    public int compareTo(Joueur j) {
        return j.getPoints() - this.getPoints();
    }
}
