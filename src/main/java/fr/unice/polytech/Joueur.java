package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Joueur implements Comparable<Joueur> {
    private final ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();
    private final String nom;
    private final Strategie strategie;
    private int or = 0;
    private int points = 0;
    private ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    private CartePersonnage personnage;
    private boolean roi = false;
    private boolean mort = false;
    private boolean first = false;

    public Joueur(String nom) {
        this.nom = nom;
        this.ajouteOr(MoteurDeJeu.or2Depart);
        for (int i = 0; i < MoteurDeJeu.carte2Depart; i++) {
            this.quartiers.add(MoteurDeJeu.deck.piocherQuartier());
        }
        this.strategie = new Strategie(this);
    }

    public Joueur(String nom, String NomStrategie) {
        this(nom);
        this.getStrategie().setStrategie(NomStrategie);
    }

    public String getNom() {
        return this.nom;
    }

    public String getNomColoured() {
        return CouleurConsole.printCyan(this.nom);
    }

    public int getOr() {
        return this.or;
    }

    public void setOr(int or) {
        this.or = or;
    }

    public String getOrColoured() {
        return CouleurConsole.printGold("" + this.or);
    }

    public CartePersonnage getPersonnage() {
        return this.personnage;
    }

    public void setPersonnage(CartePersonnage personnage) {
        this.personnage = personnage;
    }

    public ArrayList<CarteQuartier> getQuartiers() {
        return this.quartiers;
    }

    public void setQuartiers(ArrayList<CarteQuartier> quartiers) {
        this.quartiers = quartiers;
    }

    public ArrayList<CarteQuartier> getQuartiersConstruits() {
        return this.quartiersConstruits;
    }

    public boolean isRoi() {
        return this.roi;
    }

    public void setRoi(boolean b) {
        this.roi = b;
    }

    public String isRoiColoured() {
        return CouleurConsole.printBlue("" + this.roi);
    }

    public boolean isMort() {
        return this.mort;
    }

    public void setMort(boolean t) {
        this.mort = t;
    }

    public boolean isFirst() {
        return this.first;
    }

    public void setFirst(boolean b) {
        this.first = b;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int p) {
        this.points = p;
    }

    public Strategie getStrategie() {
        return this.strategie;
    }

    public String getNomStrategie() {
        return this.strategie.getiPiocher().nomStrategie();
    }

    public String getNomStrategieColoured() {
        return CouleurConsole.printPurple(this.strategie.getiPiocher().nomStrategie());
    }

    public void jouer() {
        this.printDetails();
        if (!this.mort) this.strategie.prochainTour();
        else System.out.println(this.getNomColoured() + " est " + CouleurConsole.printRed("Mort"));
    }

    public void piocherOr() {
        this.ajouteOr(MoteurDeJeu.orAPiocher);
        System.out.println(CouleurConsole.printGold("| Piocher Or"));
        System.out.println(CouleurConsole.printGold("| ") + this.getNomColoured() + " a pioché: " + CouleurConsole.printGold("" + MoteurDeJeu.orAPiocher) + " pièce" + (MoteurDeJeu.orAPiocher > 1 ? "s" : "") + " d'" + CouleurConsole.printGold("Or"));
    }

    public void ajouteOr(int n) {
        this.or += n;
    }

    public void calculePoints() {
        this.points = this.quartiersConstruits.stream().mapToInt(CarteQuartier::getPrix).sum();
        this.points += 2 * this.quartiersConstruits.stream().filter(quartier -> quartier.getNom().equals("Université") || quartier.getNom().equals("Dracoport")).count();
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

    public void piocherPersonnage() {
        this.mort = false;
        CartePersonnage cp = MoteurDeJeu.deck.piocherPersonnage();
        System.out.println(this.getNomColoured() + " a pioché: " + cp.getNomColoured());
        this.personnage = cp;
    }

    // Quelque Chose à faire Pour les methods Quartiers

    public void ajouterQuartierEnMain() {
        System.out.println(CouleurConsole.printPurple("| Piocher Quartier"));
        ArrayList<CarteQuartier> quartiersPioches = new ArrayList<>();

        this.quartiersConstruits.forEach(quartier -> {
            if (quartier.getNom().equals("Manufacture") && this.getOr()>=3) {
                this.setOr(getOr()-3);
                MoteurDeJeu.setCarteAPiocher(3);
            }
        });

        for (int i = 0; i < MoteurDeJeu.carteAPiocher; i++) {
            System.out.print(CouleurConsole.printPurple("| "));
            quartiersPioches.add(piocherQuartier());
        }

        System.out.print(CouleurConsole.printPurple("| "));
        this.quartiers.add(this.choixQuartier(quartiersPioches));
    }

    public CarteQuartier piocherQuartier() {
        CarteQuartier cq = MoteurDeJeu.deck.piocherQuartier();
        System.out.println(this.getNomColoured() + " a pioché: " + cq.getNomColoured());
        return cq;
    }

    public CarteQuartier choixQuartier(ArrayList<CarteQuartier> quartiersPioches) {
        int k = new Random().nextInt(quartiersPioches.size());
        CarteQuartier cq = quartiersPioches.get(k);
        for (int i = 0; i < quartiersPioches.size(); i++) {
            if (!this.getQuartiers().contains(quartiersPioches.get(i)) && !this.getQuartiersConstruits().contains(quartiersPioches.get(i))) {
                cq = quartiersPioches.get(i);
                k = i;
                break;
            }
        }
        for (int i = 0; i < quartiersPioches.size(); i++) {
            if (i != k) {
                MoteurDeJeu.deck.ajouterQuartierDeck(quartiersPioches.get(i));
            }
        }
        System.out.println(this.getNomColoured() + " a choisi: " + cq.getNomColoured());
        return cq;
    }

    public void construireQuartier() {
        ArrayList<CarteQuartier> quartiersAchetable = this.quartiersConstructible();
        if (quartiersAchetable.size() > 0) {
            AtomicInteger i = new AtomicInteger(1);
            System.out.println("\n" + CouleurConsole.printPink("| Construire Quartier") + " - " + this.getNomColoured() + " à " + this.getOrColoured() + " pièce" + (this.or > 1 ? "s" : "") + " d'" + CouleurConsole.printGold("Or"));
            System.out.println(CouleurConsole.printPink("| ") + CouleurConsole.tiret() + "Choix 0: Ne pas construire");
            quartiersAchetable.forEach(quartier -> System.out.println(CouleurConsole.printPink("| ") + CouleurConsole.tiret() + "Choix " + (i.getAndIncrement()) + ": " + quartier.getNomColoured() + ", " + quartier.getPrixColoured() + ", " + quartier.getGemmeColoured() + (quartier.getDescription().equals("None") ? "" : ", " + quartier.getDescriptionColoured())));
            CarteQuartier choix = quartiersAchetable.get(Math.min(new Random().nextInt(0, quartiersAchetable.size()), quartiersAchetable.size() - 1));
            System.out.println(CouleurConsole.printPink("| ") + this.getNomColoured() + " a construit: " + choix.getNomColoured());
            this.ajouteOr(-1 * choix.getPrix());
            this.quartiersConstruits.add(choix);
            this.quartiers.remove(choix);
        } else {
            System.out.println(CouleurConsole.printPink("| ") + this.getNomColoured() + " n'a pas assez de pièces d'" + CouleurConsole.printGold("Or") + " pour construire.");
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

    private void printDetails() {
        System.out.println();
        System.out.println(CouleurConsole.printBlue("| Details Joueur"));
        System.out.println(CouleurConsole.printBlue("| ") + "Personnage: " + this.personnage.getNomColoured());
        System.out.println(CouleurConsole.printBlue("| ") + "Pièces d'Or: " + this.getOrColoured());
        System.out.println(CouleurConsole.printBlue("| ") + "Stratégie: " + this.getNomStrategieColoured());
        System.out.println(CouleurConsole.printBlue("| ") + "Quartiers dans la main: " + this.getQuartiers().stream().map(CarteQuartier::getNomColoured).toList());
        System.out.println(CouleurConsole.printBlue("| ") + "Quartiers construit: " + this.getQuartiersConstruits().stream().map(CarteQuartier::getNomColoured).toList());
        System.out.println();
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "nom=" + this.getNomColoured() +
                ", or=" + this.getOrColoured() +
                ", estRoi=" + this.isRoiColoured() +
                ", personnage=" + this.personnage.getNomColoured() +
                ", quartiers=" + this.quartiers.stream().map(CarteQuartier::getNomColoured).toList() +
                ", quartiersConstruits=" + this.quartiersConstruits.stream().map(CarteQuartier::getNomColoured).toList() +
                '}';
    }

    @Override
    public int compareTo(Joueur j) {
        return j.getPoints() - this.getPoints();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
