package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Classe permettant d'initialiser les joueurs.
 */

public class Joueur implements Comparable<Joueur> {
    /**
     * Le numéro du joueur.
     */
    private static int numJoueur = 0;

    /**
     * La liste de ses quartiers construits.
     */
    private final ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();

    /**
     * La stratégie utilisée par le joueur.
     */
    private final Strategie strategie;

    /**
     * Le nom du joueur.
     */
    private String nom;

    /**
     * L'or qu'a le joueur.
     */
    private int or = 0;

    /**
     * Les points du joueur.
     */
    private int points = 0;

    /**
     * La liste des quartiers en main.
     */
    private ArrayList<CarteQuartier> quartiers = new ArrayList<>();

    /**
     * La liste des couleurs des gemmes des quartiers.
     */
    private ArrayList<String> gemmesQuartiers = new ArrayList<>();

    /**
     * La carte personnage du joueur.
     */
    private CartePersonnage personnage;

    /**
     * Vrai si le joueur devient roi et donc commence la partie, faux sinon.
     */
    private boolean roi = false;

    /**
     * Vrai si le joueur est tué par un autre joueur, faux sinon.
     */
    private boolean mort = false;

    /**
     * Vrai si le joueur a gagné la partie en premier, faux sinon.
     */
    private boolean first = false;

    /**
     * Le constructeur du joueur.
     * On lui donne un nom, on initialise son nombre d'or et de cartes quartiers en début de partie et on lui associe une stratégie.
     */
    public Joueur() {
        this.nom = "CPU" + ++numJoueur;
        this.ajouteOr(MoteurDeJeu.or2Depart);
        for (int i = 0; i < MoteurDeJeu.carte2Depart; i++) {
            this.quartiers.add(MoteurDeJeu.deck.piocherQuartier());
        }
        this.strategie = new Strategie(this);
    }

    /**
     * Le constructeur du joueur et on lui associe le nom passé en paramètre.
     * On lui initialise son nombre d'or et de cartes quartiers en début de partie et on lui associe une stratégie.
     *
     * @param nom Le nom du joueur.
     */
    public Joueur(String nom) {
        this();
        this.nom = nom;
    }

    /**
     * Le constructeur du joueur et on lui associe le nom et le nom de la stratégie passé en paramètre.
     * On lui initialise son nombre d'or et de cartes quartiers en début de partie.
     *
     * @param nom Le nom du joueur.
     * @param NomStrategie Le nom de la stratégie utilisée.
     */
    public Joueur(String nom, String NomStrategie) {
        this(nom);
        this.getStrategie().setStrategie(NomStrategie);
    }

    /**
     * Permet de récupérer le nom du joueur.
     *
     * @return Le nom du joueur.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Permet de récupérer le nom du joueur mais avec des couleurs pour l'affichage.
     *
     * @return Le nom du joueur en cyan.
     */
    public String getNomColoured() {
        return CouleurConsole.printCyan(this.nom);
    }

    /**
     * Permet de récupérer le nombre d'or que possède le joueur.
     *
     * @return Le nombre d'or que possède le joueur.
     */
    public int getOr() {
        return this.or;
    }

    public void setOr(int or) {
        this.or = or;
    }

    /**
     * Permet de récupérer le nombre d'or que possède le joueur mais avec des couleurs pour l'affichage.
     *
     * @return Le nombre d'or que possède le joueur en or.
     */
    public String getOrColoured() {
        return CouleurConsole.printGold("" + this.or);
    }

    /**
     * Permet de récupérer la carte personnage du joueur.
     *
     * @return La carte personnage du joueur.
     */
    public CartePersonnage getPersonnage() {
        return this.personnage;
    }

    /**
     * Permet d'affecter une nouvelle carte personnage au joueur.
     *
     * @param personnage La carte personnage du joueur.
     */
    public void setPersonnage(CartePersonnage personnage) {
        this.personnage = personnage;
    }

    /**
     * Permet de récupérer la liste des quartiers en main du joueur.
     *
     * @return La liste des quartiers dans la main du joueur.
     */
    public ArrayList<CarteQuartier> getQuartiers() {
        return this.quartiers;
    }

    /**
     * Permet d'affecter une nouvelle liste de quartiers dans la main du joueur.
     *
     * @param quartiers La liste des quartiers dans la main du joueur.
     */
    public void setQuartiers(ArrayList<CarteQuartier> quartiers) {
        this.quartiers = quartiers;
    }

    /**
     * Permet de récupérer la liste des quartiers construits par le joueur.
     *
     * @return La liste des quartiers construits par le joueur.
     */
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

    public ArrayList<String> getGemmesQuartiersConstruit() {
        this.calculerGemmesQuartiers();
        return this.gemmesQuartiers;
    }

    public ArrayList<String> getGemmesQuartiersColoured() {
        this.calculerGemmesQuartiers();
        return new ArrayList<>(this.gemmesQuartiers.stream().map(CouleurConsole::printPurple).toList());
    }

    private void calculerGemmesQuartiers() {
        this.gemmesQuartiers = new ArrayList<>(this.quartiersConstruits.stream().map(CarteQuartier::getGemme).distinct().toList());
    }

    public void pouvoirCourDesMiracles(){
        if(this.contientQuartier("Cour des miracles")) {
            List<String> gemmesPossibles = Arrays.asList("Noblesse", "Commerce et Artisanat", "Soldatesque", "Prestige", "Religion");
            String gemmeManquante;
            if (gemmesQuartiers.size() == 4 && this.quartiersConstruits.stream().filter(quartier -> quartier.getGemme().equals("Prestige")).count() == 2) {
                gemmeManquante = gemmesPossibles.stream().filter(gemme -> !gemmesQuartiers.contains(gemme)).toString();
                gemmesQuartiers.add(gemmeManquante);
            }
        }
    }

    public void jouer() {
        this.printDetails();
        if (!this.mort) this.strategie.prochainTour();
        else System.out.println(this.getNomColoured() + " est " + CouleurConsole.printRed("Mort"));
    }

    public void piocherOr() {
        this.ajouteOr(MoteurDeJeu.orAPiocher);
        System.out.println(CouleurConsole.printGold("| Piocher Or"));
        System.out.println(CouleurConsole.printGold("| ") + this.getNomColoured() + " a pioché " + CouleurConsole.printGold("" + MoteurDeJeu.orAPiocher) + " pièce" + (MoteurDeJeu.orAPiocher > 1 ? "s" : "") + " d'" + CouleurConsole.printGold("Or"));
    }

    public void ajouteOr(int n) {
        this.or += MoteurDeJeu.banque.transaction(n);
    }

    public void calculePoints() {
        this.pouvoirCourDesMiracles();
        this.getGemmesQuartiersConstruit();
        this.points = this.quartiersConstruits.stream().mapToInt(CarteQuartier::getPrix).sum();
        this.points += 2 * this.quartiersConstruits.stream().filter(quartier -> quartier.getNom().equals("Université") || quartier.getNom().equals("Dracoport")).count();
        if (this.quartiersConstruits.size() >= MoteurDeJeu.quartiersAConstruire) {
            this.points += 2;
            if (this.first) {
                this.points += 2;
            }
        }
        if (this.gemmesQuartiers.size() == 5) {
            this.points += 3;
        }
    }

    public void piocherPersonnage() {
        this.mort = false;
        CartePersonnage cp = MoteurDeJeu.deck.piocherPersonnage();
        System.out.println(this.getNomColoured() + " a pioché: " + cp.getNomColoured());
        this.personnage = cp;
    }

    public void ajouterQuartierEnMain() {
        System.out.println(CouleurConsole.printPurple("| Piocher Quartier"));
        ArrayList<CarteQuartier> quartiersPioches = new ArrayList<>();
        int nbCartes = MoteurDeJeu.carteAPiocher;

        if (this.contientQuartier("Manufacture") && this.getOr() >= 3) {
            this.ajouteOr(-3);
            nbCartes = 3;
        }

        if (this.contientQuartier("Laboratoire")) {
            this.ajouteOr(1);
            int taille = quartiers.size();
            if (!(taille == 0)) this.quartiers.remove(new Random().nextInt(taille));
        }

        if (this.contientQuartier("Observatoire")) {
            nbCartes = 3;
        }

        for (int i = 0; i < nbCartes; i++) {
            System.out.print(CouleurConsole.printPurple("| "));
            quartiersPioches.add(piocherQuartier());
        }


        if (this.contientQuartier("Bibliothèque")) {
            for (int i = 0; i < 2; i++) {
                quartiers.add(quartiersPioches.get(i));
                System.out.print(CouleurConsole.printPurple("| "));
                System.out.println(this.getNomColoured() + " a choisi: " + quartiersPioches.get(i).getNomColoured());
            }
        } else {
            System.out.print(CouleurConsole.printPurple("| "));
            this.quartiers.add(this.choixQuartier(quartiersPioches));
        }
    }

    public CarteQuartier piocherQuartier() {
        CarteQuartier cq = MoteurDeJeu.deck.piocherQuartier();
        if (cq != null) System.out.println(this.getNomColoured() + " a pioché: " + cq.getNomColoured());
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
        if (cq != null) System.out.println("\n" + CouleurConsole.printPurple("| ") + this.getNomColoured() + " a choisi: " + cq.getNomColoured());
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
                ", strategie=" + this.getNomStrategieColoured() +
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
