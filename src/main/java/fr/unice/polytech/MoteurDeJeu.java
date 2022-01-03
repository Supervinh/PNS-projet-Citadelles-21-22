package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.lecteurFichiers.ExcelReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

/**
 * Classe qui gère le déroulement du jeu.
 */
public class MoteurDeJeu {

    public static final boolean nomAleatoire = false;
    public static final int or2Depart = 2;
    public static final int orAPiocher = 2;
    public static final int carte2Depart = 4;
    public static final int carteAPiocher = 2;
    public static final int quartiersAConstruire = 8;
    public static final int piecesEnJeu = 30;
    public static int nbJoueurs = 7;
    public static Deck deck;
    public static Banque banque;
    public static ArrayList<Joueur> joueurs;
    public static ArrayList<Joueur> personnagesConnus;
    public static Level messageLvl = Level.ALL;
    private final int nombre2Personnages;
    private final ArrayList<CartePersonnage> cartesVisibles = new ArrayList<>();
    private int nb2Tours = 0;
    private int roiIndex = 0;
    private boolean avaitRoi = true;
    private CartePersonnage carteCachee;

    public MoteurDeJeu() {
        deck = new Deck();
        banque = new Banque();
        joueurs = new ArrayList<>();
        personnagesConnus = new ArrayList<>();
        Joueur.numJoueur = 0;
        this.nombre2Personnages = deck.getPersonnages().size();
        if (deck.getQuartiersPossibles().size() != 65 || nbJoueurs < 4 || nbJoueurs > 7) {
            Affichage.malInitialise();
            System.exit(0);
        }
    }

    public static void setMessageLvl(Level messageLvl) {
        MoteurDeJeu.messageLvl = messageLvl;
    }

    public CartePersonnage getCarteCachee() {
        return this.carteCachee;
    }

    public void jouer() {
        Affichage.citadelle();
        this.initialiseJoueurs(joueurs, !nomAleatoire);
        this.printJoueursInitialises(joueurs);
        this.lancerTourDeJeu(joueurs);
        this.printGagnant(joueurs);
        this.printClassement(joueurs);
    }

    public void setJoueurs(ArrayList<Joueur> joueursAjoutes) {
        joueurs = joueursAjoutes;
        nbJoueurs = joueurs.size();
    }

    public void lancerTourDeJeu(ArrayList<Joueur> joueurs) {
        while (joueurs.stream().noneMatch(Joueur::isFirst)) {
            personnagesConnus = new ArrayList<>();
            Affichage.tourNumX(++this.nb2Tours);
            this.trouverQuiEstRoi(joueurs);
            this.piocherPersonnage(joueurs);
            this.jouerDansLOrdreDesPersonnages(joueurs);
        }
    }

    public void jouerDansLOrdreDesPersonnages(ArrayList<Joueur> joueurs) {
        for (int i = 1; i <= this.nombre2Personnages; i++) {
            for (Joueur joueur : joueurs) {
                if (joueur.getPersonnage().getId() == i) this.tourDeJeu(joueur);
            }
        }
        this.avaitRoi = joueurs.stream().anyMatch(joueur -> joueur.getPersonnage().getNom().equals("Roi") && !joueur.isMort());
        joueurs.forEach(joueur -> deck.ajoutePersonnage(joueur.getPersonnage()));

        this.cartesVisibles.forEach(cp -> deck.ajoutePersonnage(cp));
        deck.ajoutePersonnage(this.carteCachee);
    }

    public void tourDeJeu(Joueur joueur) {
        Affichage.tourAX(joueur);
        joueur.jouer();
        personnagesConnus.add(joueur);
        if (aFini(joueur)) {
            Affichage.premierFini(joueur);
        }
        joueur.calculePoints();

        if (!banque.sommeArgentCirculationCorrecte() || deck.getQuartiersPossibles().size() != 65 || deck.getPersonnagesPossibles().size() != 8) {
            throw new IllegalArgumentException(
                    "Erreur, Perte d'objet.");
        }
    }

    public void initialiseJoueurs(ArrayList<Joueur> joueurs, boolean nameless) {
        ExcelReader ER = new ExcelReader();
        for (int i = 1; i <= MoteurDeJeu.nbJoueurs; i++) {
            if (nameless) {
                joueurs.add(new Joueur());
            } else {
                joueurs.add(new Joueur(ER.getRandomName()));
            }
        }
        joueurs.get(0).getStrategie().setStrategie("Rusher");
        joueurs.get(1).getStrategie().setStrategie("Merveille");
        joueurs.get(2).getStrategie().setStrategie("Agressif");
        joueurs.get(3).getStrategie().setStrategie("VStrat");
        joueurs.get(4).getStrategie().setStrategie("Commerce");
        joueurs.get(0).setRoi(true);
    }

    public void initialisePileCartes() {
        this.cartesVisibles.clear();
        if (nbJoueurs == 4) {
            this.choixCartesVisibles();
            this.choixCartesVisibles();
        }
        if (nbJoueurs == 5) {
            this.choixCartesVisibles();
        }
        this.carteCachee = deck.piocherPersonnage();
        Affichage.carteVisibleEtCachee(this.cartesVisibles, this.carteCachee);
    }

    private void choixCartesVisibles() {
        CartePersonnage cp = deck.piocherPersonnage();
        if (cp.getNom().equals("Roi")) {
            this.cartesVisibles.add(deck.piocherPersonnage());
            deck.ajoutePersonnage(cp);
        } else {
            this.cartesVisibles.add(cp);
        }
    }

    public void trouverQuiEstRoi(ArrayList<Joueur> joueurs) {
        AtomicInteger k = new AtomicInteger(0);
        this.roiIndex = joueurs.stream().peek(v -> k.getAndIncrement()).anyMatch(Joueur::isRoi) ? k.get() - 1 : this.roiIndex;
        if (!this.avaitRoi) {
            joueurs.get(this.roiIndex).setRoi(false);
            this.roiIndex = (this.roiIndex + 1) % nbJoueurs;
            joueurs.get(this.roiIndex).setRoi(true);
        }
    }

    public void piocherPersonnage(ArrayList<Joueur> joueurs) {
        initialisePileCartes();
        for (int i = this.roiIndex; i < joueurs.size(); i++) {
            joueurs.get(i).piocherPersonnage();
        }
        for (int i = 0; i < this.roiIndex; i++) {
            joueurPiochePersonnage(joueurs, i);
        }
    }

    public void joueurPiochePersonnage(ArrayList<Joueur> joueurs, int i) {
        if (joueurs.size() < 7) joueurs.get(i).piocherPersonnage();
        else {
            if (deck.getPersonnages().size() == 1) {
                remettreCarteCachee();
                joueurs.get(i).piocherPersonnage();
                this.carteCachee = deck.piocherPersonnage();
                Affichage.nouvelleCachee(this.carteCachee);
            } else joueurs.get(i).piocherPersonnage();
        }
    }

    public void remettreCarteCachee() {
        deck.ajoutePersonnage(carteCachee);
        Affichage.remettreCachee(this.carteCachee);
        this.carteCachee = null;
    }

    public boolean aFini(Joueur joueur) {
        if (joueur.getQuartiersConstruits().size() >= MoteurDeJeu.quartiersAConstruire && joueurs.stream().noneMatch(Joueur::isFirst)) {
            joueur.setFirst(true);
            return true;
        }
        return false;
    }

    public int obtenirScoreMax(ArrayList<Joueur> joueurs) {
        joueurs.forEach(Joueur::calculePoints);
        return joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
    }

    public Joueur obtenirGagnant(ArrayList<Joueur> joueurs) {
        int score = obtenirScoreMax(joueurs);
        ArrayList<Joueur> gagnants = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPoints() == score).toList());
        if (gagnants.size() > 1) {
            int premierJoueur = gagnants.stream().mapToInt(joueur -> (int) joueur.getPersonnage().getId()).min().orElse(0);
            gagnants = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPersonnage().getId() == premierJoueur).toList());
        }
        return gagnants.get(0);
    }

    public void printGagnant(ArrayList<Joueur> joueurs) {
        Affichage.gagnant(obtenirGagnant(joueurs));
    }

    public void printJoueursInitialises(ArrayList<Joueur> joueurs) {
        Affichage.initialisation(joueurs);
    }

    public void printClassement(ArrayList<Joueur> joueurs) {
        Collections.sort(joueurs);
        Affichage.classement(joueurs, this.nb2Tours);
    }

    public void setNbJoueurs(int nombre) {
        nbJoueurs = nombre;
    }
}
