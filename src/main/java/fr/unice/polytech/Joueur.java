package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe permettant d'initialiser les joueurs.
 */
public class Joueur implements Comparable<Joueur> {
    /**
     * Le numéro du joueur.
     */
    private static int numJoueur = 0;

    /**
     * La stratégie utilisée par le joueur.
     */
    private final Strategie strategie;

    /**
     * La liste de ses quartiers construits.
     */
    private final ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();

    /**
     * La liste des quartiers en main.
     */
    private ArrayList<CarteQuartier> quartiers = new ArrayList<>();

    /**
     * La carte personnage du joueur.
     */
    private CartePersonnage personnage;

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
     * @param nom          Le nom du joueur.
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

    /**
     * Permet de savoir si le joueur est roi ou non.
     *
     * @return Vrai si le joueur est roi, faux sinon.
     */
    public boolean isRoi() {
        return this.roi;
    }

    /**
     * Permet d'affecter le rôle du roi au joueur.
     *
     * @param b Vrai si il est roi, faux sinon.
     */
    public void setRoi(boolean b) {
        this.roi = b;
    }

    /**
     * Permet de savoir si le joueur est roi mais avec des couleurs pour l'affichage.
     *
     * @return Vrai si le joueur est roi, faux sinon écrit en bleu.
     */
    public String isRoiColoured() {
        return CouleurConsole.printBlue("" + this.roi);
    }

    /**
     * Permet de savoir si le joueur est mort ou non.
     *
     * @return Vrai si le joueur est mort, faux sinon.
     */
    public boolean isMort() {
        return this.mort;
    }

    /**
     * Permet de modifier l'état d'un joueur, mort ou non.
     *
     * @param t Vrai ou Faux.
     */
    public void setMort(boolean t) {
        this.mort = t;
    }

    /**
     * Permet de savoir si le joueur a fini en premier ou non.
     *
     * @return Vrai si le joueur a fini en premier, faux sinon.
     */
    public boolean isFirst() {
        return this.first;
    }

    /**
     * Permet de mettre vrai au premier joueur qui gagne.
     *
     * @param b Vrai ou faux.
     */
    public void setFirst(boolean b) {
        this.first = b;
    }

    /**
     * Permet de récupérer le nombre de points qu'a le joueur.
     *
     * @return Le nombre de points qu'a le joueur.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Permet de récupérer la stratégie qu'utilise le joueur.
     *
     * @return La stratégie qu'utilise le joueur.
     */
    public Strategie getStrategie() {
        return this.strategie;
    }

    /**
     * Permet de récupérer le nom de la stratégie qu'utilise le joueur.
     *
     * @return Le nom de la stratégie qu'utilise le joueur.
     */
    public String getNomStrategie() {
        return this.strategie.getiPiocher().nomStrategie();
    }

    /**
     * Permet de récupérer le nom de la stratégie qu'utilise le joueur mais avec des couleurs pour l'affichage.
     *
     * @return Le nom de la stratégie en violet.
     */
    public String getNomStrategieColoured() {
        return CouleurConsole.printPurple(this.strategie.getiPiocher().nomStrategie());
    }

    /**
     * Permet de récupérer la liste des gemmes des quartiers que le joueur a construit.
     *
     * @return La liste des gemmes des quartiers que le joueur a construit.
     */
    public ArrayList<String> getGemmesQuartiersConstruit() {
        return this.calculerGemmesQuartiers();
    }

    /**
     * Permet de récupérer la liste des gemmes des quarties que le joueur a construit mais avec des couleurs pour l'affichage.
     *
     * @return La liste des gemmes des quartiers que le joueur a construit en violet.
     */
    public ArrayList<String> getGemmesQuartiersColoured() {
        return new ArrayList<>(this.getGemmesQuartiersConstruit().stream().map(CouleurConsole::printPurple).toList());
    }

    /**
     * Trouve les gemmes correspondant aux quartiers contruits.
     *
     * @return La liste des gemmes dans les quartiers construits.
     */
    private ArrayList<String> calculerGemmesQuartiers() {
        return new ArrayList<>(this.quartiersConstruits.stream().map(CarteQuartier::getGemme).distinct().toList());
    }

    /**
     * Correspond au pouvoir de la carte cour des miracles.
     * Une fois la partie finie, et si on a construit la cour des miracles on peut choisir la couleur de sa gemme.
     */
    public void pouvoirCourDesMiracles() {
        ArrayList<String> gemmesPossibles = new ArrayList<>(MoteurDeJeu.deck.getQuartiersPossibles().stream().map(CarteQuartier::getGemme).distinct().toList());
        ArrayList<String> gemmesQuartiers = new ArrayList<>(this.getGemmesQuartiersConstruit());
        if (gemmesQuartiers.size() == 4 && this.quartiersConstruits.stream().filter(quartier -> quartier.getGemme().equals("Prestige")).count() >= 2) {
            String gemmeManquante = gemmesPossibles.stream().filter(gemme -> !gemmesQuartiers.contains(gemme)).findFirst().orElse("");
            gemmesQuartiers.add(gemmeManquante);
            for (CarteQuartier cq : this.quartiersConstruits) {
                if (cq.getNom().equals("Cour des miracles")) {
                    cq.setGemme(gemmeManquante);
                }
            }
        }
    }

    /**
     * Affiche les détails du joueur qui joue, s'il n'est pas mort on joue sinon on passe au prochain tour.
     */
    public void jouer() {
        this.printDetails();
        if (!this.mort) this.strategie.prochainTour();
        else System.out.println(this.getNomColoured() + " est " + CouleurConsole.printRed("Mort"));
    }

    /**
     * Permet de piocher de l'or.
     */
    public void piocherOr() {
        this.ajouteOr(MoteurDeJeu.orAPiocher);
        System.out.println(CouleurConsole.printGold("| Piocher Or"));
        System.out.println(CouleurConsole.printGold("| ") + this.getNomColoured() + " a pioché " + CouleurConsole.printGold("" + MoteurDeJeu.orAPiocher) + " pièce" + (MoteurDeJeu.orAPiocher > 1 ? "s" : "") + " d'" + CouleurConsole.printGold("Or"));
    }

    /**
     * Permet d'ajouter de l'or dans la main du joueur.
     *
     * @param n L'or a ajouté dans la main du joueur.
     */
    public void ajouteOr(int n) {
        this.or += MoteurDeJeu.banque.transaction(n);
    }

    /**
     * Calcule les points du joueur.
     * Il y a des cas spécifiques pour le calcul des points en fonction de quand il termine et avec quoi.
     */
    public void calculePoints() {
        if (this.contientQuartier("Cour des miracles")) {
            this.pouvoirCourDesMiracles();
        }
        this.points = this.quartiersConstruits.stream().mapToInt(CarteQuartier::getPrix).sum();
        this.points += 2 * this.quartiersConstruits.stream().filter(quartier -> quartier.getNom().equals("Université") || quartier.getNom().equals("Dracoport")).count();
        if (this.quartiersConstruits.size() >= MoteurDeJeu.quartiersAConstruire) {
            this.points += 2;
            if (this.first) {
                this.points += 2;
            }
        }
        if (this.getGemmesQuartiersConstruit().size() == 5) {
            this.points += 3;
        }
    }

    /**
     * Le joueur pioche une carte personnage.
     * Si le joueur était mort au tour dernier, il ne l'est plus à ce tour ci.
     */
    public void piocherPersonnage() {
        this.mort = false;
        CartePersonnage cp = MoteurDeJeu.deck.piocherPersonnage();
        System.out.println(this.getNomColoured() + " a pioché: " + cp.getNomColoured());
        this.personnage = cp;
    }

    /**
     * On pioche une carte quartier et on l'ajoute dans la main.
     * Certaines cartes ont des effets sur la pioche de cartes quand lorsqu'elles sont construites.
     */
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

    /**
     * Pioche une carte quartier dans la pioche en jeu.
     *
     * @return Une pioche quartier.
     */
    public CarteQuartier piocherQuartier() {
        CarteQuartier cq = MoteurDeJeu.deck.piocherQuartier();
        if (cq != null) System.out.println(this.getNomColoured() + " a pioché: " + cq.getNomColoured());
        return cq;
    }

    /**
     * Choisit une carte quartier que le joueur met dans sa main.
     *
     * @param quartiersPioches La liste des quartiers piochés.
     * @return Une carte quartier.
     */
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
        if (cq != null)
            System.out.println("\n" + CouleurConsole.printPurple("| ") + this.getNomColoured() + " a choisi: " + cq.getNomColoured());
        return cq;
    }

    /**
     * Méthode pour construire un quartier. On vérifie si on a assez d'argent, si on a de quoi construire.
     */
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

    /**
     * Permet de récupérer les quartiers constructibles dans notre main.
     *
     * @return La liste des cartes quartiers constructibles.
     */
    public ArrayList<CarteQuartier> quartiersConstructible() {
        return new ArrayList<>(this.quartiers.stream().filter(quartier -> (quartier.getPrix() <= this.or) && (!this.contientQuartier(quartier.getNom()))).toList());
    }

    /**
     * Permet de savoir si le quartier est construit.
     *
     * @param nom Le nom du quartier.
     * @return Vrai si on a le quartier, faux sinon.
     */
    public boolean contientQuartier(String nom) {
        return this.quartiersConstruits.stream().anyMatch(quartier -> quartier.getNom().equals(nom));
    }

    /**
     * Permet de savoir le nombre de quartiers constructibles dans notre main.
     *
     * @return Le nombre de quartiers constructibles.
     */
    public int nombre2QuartiersConstructible() {
        return new ArrayList<>(this.quartiers.stream().filter(quartier -> quartier.getPrix() <= this.or).toList()).size();
    }

    /**
     * Revoie les informations au tour du joueur.
     */
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

    /**
     * Affichage de toutes les informations du joueur, son nom, son or, si il est roi, son personnage, les quartiers en main, les quartiers construits et la statégie utilisée.
     *
     * @return Les informations du quartier.
     */
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

    /**
     * Compare les points de deux joueurs.
     *
     * @param j Le joueur avec qui on veut comparer.
     * @return La différence entre les points des deux joueurs.
     */
    @Override
    public int compareTo(Joueur j) {
        return j.getPoints() - this.getPoints();
    }

    /**
     * Permet de savoir si un objet est égal.
     *
     * @param obj Un objet a comparé.
     * @return Vrai ou faux.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
