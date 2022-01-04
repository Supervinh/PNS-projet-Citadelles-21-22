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

    /**
     * Permet de choisir si on veut des joueurs avec des vrais noms ou juste un nom d'ordinateur.
     */
    public static final boolean nomAleatoire = false;

    /**
     * Nombre d'or en début de partie.
     */
    public static final int or2Depart = 2;

    /**
     * Nombre d'or à piocher par tours.
     */
    public static final int orAPiocher = 2;

    /**
     * Nombre de cartes en début de partie.
     */
    public static final int carte2Depart = 4;

    /**
     * Nombre de cartes à piocher par tours.
     */
    public static final int carteAPiocher = 2;

    /**
     * Nombre de quartiers à construire pour terminer la partie.
     */
    public static final int quartiersAConstruire = 8;

    /**
     * Nombre de pieces total dans le jeu.
     */
    public static final int piecesEnJeu = 30;

    /**
     * Nombre de joueurs.
     */
    public static int nbJoueurs = 7;

    /**
     * Les pioches de cartes.
     */
    public static Deck deck;

    /**
     * La banque pour pouvoir gérer l'argent dans le jeu.
     */
    public static Banque banque;

    /**
     * La liste des joueurs de la partie.
     */
    public static ArrayList<Joueur> joueurs;

    /**
     * La liste des personnages connus durant le tours.
     */
    public static ArrayList<Joueur> personnagesConnus;

    /**
     * Niveau des messages à afficher.
     */
    public static Level messageLvl = Level.ALL;

    /**
     * Nombre de personnages dans la pioche.
     */
    private final int nombre2Personnages;

    /**
     * Liste des cartes visibles de personnages durant le tours.
     */
    private final ArrayList<CartePersonnage> cartesVisibles = new ArrayList<>();

    /**
     * À quel tours on est rendu.
     */
    private int nb2Tours = 0;

    /**
     * Permet de savoir qui est roi.
     */
    private int roiIndex = 0;

    /**
     * Permet de savoir si on est roi.
     */
    private boolean avaitRoi = true;

    /**
     * La carte cachée des personnages.
     */
    private CartePersonnage carteCachee;


    /**
     * Le constructeur du moteur de jeu où on initialise le deck, la banque, les joueurs, les personnages connus, le numéro du joueur,
     * le nombre de personnages dans la pioche, et arrête le jeu si on a pas le bon nombre de quartiers, il y a moins de 4 joueurs
     * ou plus de 7 joueurs.
     */
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

    /**
     * Permet d'affecter le niveau du message à afficher.
     *
     * @param messageLvl Le niveau du message.
     */
    public void setMessageLvl(Level messageLvl) {
        MoteurDeJeu.messageLvl = messageLvl;
        Affichage.levelUpdate();
    }

    /**
     * Permet de récupérer la carte cachée du personnage.
     *
     * @return La carte du personnage.
     */
    public CartePersonnage getCarteCachee() {
        return this.carteCachee;
    }

    /**
     * Permet de lancer une partie entière.
     */
    public void jouer() {
        Affichage.citadelle();
        this.initialiseJoueurs(joueurs, !nomAleatoire);
        this.printJoueursInitialises(joueurs);
        this.lancerTourDeJeu(joueurs);
        this.printGagnant(joueurs);
        this.printClassement(joueurs);
    }

    /**
     * Permet d'affecter les joueurs à la liste des joueurs.
     *
     * @param joueursAjoutes Les joueurs à ajouter.
     */
    public void setJoueurs(ArrayList<Joueur> joueursAjoutes) {
        joueurs = joueursAjoutes;
        nbJoueurs = joueurs.size();
    }

    /**
     * Permet de lancer un tours de jeu, tout le monde jouera donc une fois.
     *
     * @param joueurs La liste des joueurs à jouer.
     */
    public void lancerTourDeJeu(ArrayList<Joueur> joueurs) {
        while (joueurs.stream().noneMatch(Joueur::isFirst)) {
            personnagesConnus = new ArrayList<>();
            Affichage.tourNumX(++this.nb2Tours);
            this.trouverQuiEstRoi(joueurs);
            this.piocherPersonnage(joueurs);
            this.jouerDansLOrdreDesPersonnages(joueurs);
        }
    }

    /**
     * Permet d'appeler les joueurs dans l'ordre des personnages.
     *
     * @param joueurs La liste des joueurs.
     */
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

    /**
     * À la fin du tours du joueur, on connait son personnage et si il a fini on termine le tour.
     * On calcule aussi les points du joueurs.
     * Si il n'y a plus toutes les pièces en jeu, qu'il manque des cartes quartiers, ou que il n'y a plus toutes les cartes
     * personnage on arrête le jeu.
     *
     * @param joueur Le joueur qui joue pendant son tour.
     */
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

    /**
     * Permet d'initialiser le nom des joueurs avec ou sans vrais noms.
     * On force aussi une stratégie à chaque joueurs.
     *
     * @param joueurs  La liste des joueurs qui jouent.
     * @param nameless Vrai si on veut des vrais noms, faux sinon.
     */
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
        if (joueurs.size() > 4) joueurs.get(4).getStrategie().setStrategie("Commerce");
        joueurs.get(0).setRoi(true);
    }

    /**
     * Initialise les personnages.
     * Si on est 4 joueurs on a 2 cartes faces visibles.
     * Si on a 5 joueurs on a 1 carte face visible.
     * Si on est plus nombreux on a pas de cartes face visible mais on a dans tout les cas une carte cachée.
     */
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

    /**
     * Quand on pioche une carte visible, si c'est le roi on le remet dans la pile et on repioche une carte.
     */
    private void choixCartesVisibles() {
        CartePersonnage cp = deck.piocherPersonnage();
        if (cp.getNom().equals("Roi")) {
            this.cartesVisibles.add(deck.piocherPersonnage());
            deck.ajoutePersonnage(cp);
        } else {
            this.cartesVisibles.add(cp);
        }
    }

    /**
     * Permet de savoir qui est roi pendant le tour.
     *
     * @param joueurs Les joueurs qui jouent.
     */
    public void trouverQuiEstRoi(ArrayList<Joueur> joueurs) {
        AtomicInteger k = new AtomicInteger(0);
        this.roiIndex = joueurs.stream().peek(v -> k.getAndIncrement()).anyMatch(Joueur::isRoi) ? k.get() - 1 : this.roiIndex;
        if (!this.avaitRoi) {
            joueurs.get(this.roiIndex).setRoi(false);
            this.roiIndex = (this.roiIndex + 1) % nbJoueurs;
            joueurs.get(this.roiIndex).setRoi(true);
        }
    }

    /**
     * Une fois la pioche initialisée on pioche une carte en commençant par le roi.
     *
     * @param joueurs Les joueurs qui jouent.
     */
    public void piocherPersonnage(ArrayList<Joueur> joueurs) {
        Affichage.personnageTitre();
        initialisePileCartes();
        for (int i = this.roiIndex; i < joueurs.size(); i++) {
            joueurs.get(i).piocherPersonnage();
        }
        for (int i = 0; i < this.roiIndex; i++) {
            joueurPiochePersonnage(joueurs, i);
        }
    }


    /**
     * Permet de piocher une carte personnage.
     * Si on est 7 joueurs, le dernier à choisir doit récupérer la carte que l'on a caché et choisir entre les deux cartes et en
     * remettre une face cachée.
     *
     * @param joueurs Les joueurs qui jouent.
     * @param i       L'index du joueur.
     */
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

    /**
     * Permet de remettre dans le deck des personnages la carte cachée.
     */
    public void remettreCarteCachee() {
        deck.ajoutePersonnage(carteCachee);
        Affichage.remettreCachee(this.carteCachee);
        this.carteCachee = null;
    }

    /**
     * Quand le joueur a le nombre de quartiers maximum et que personne d'autre n'a fini avant lui alors il est le premier à gagner.
     *
     * @param joueur Le joueur qui joue pendant le tours.
     * @return Vrai si il est le premier a avoir terminé, faux sinon.
     */
    public boolean aFini(Joueur joueur) {
        if (joueur.getQuartiersConstruits().size() >= MoteurDeJeu.quartiersAConstruire && joueurs.stream().noneMatch(Joueur::isFirst)) {
            joueur.setFirst(true);
            return true;
        }
        return false;
    }

    /**
     * Permet d'obtenir le score maximal de tous les joueurs.
     *
     * @param joueurs Les joueurs qui jouent.
     * @return Le score maximal.
     */
    public int obtenirScoreMax(ArrayList<Joueur> joueurs) {
        joueurs.forEach(Joueur::calculePoints);
        return joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
    }

    /**
     * On récupère le joueur avec le score maximal, si il y a des égalités c'est le joueur avec la carte personnage qui a commencé
     * en premier qui est le vainqueur.
     *
     * @param joueurs Les joueurs qui jouent.
     * @return Le joueur gagnant.
     */
    public Joueur obtenirGagnant(ArrayList<Joueur> joueurs) {
        int score = obtenirScoreMax(joueurs);
        ArrayList<Joueur> gagnants = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPoints() == score).toList());
        if (gagnants.size() > 1) {
            int premierJoueur = gagnants.stream().mapToInt(joueur -> (int) joueur.getPersonnage().getId()).min().orElse(0);
            gagnants = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPersonnage().getId() == premierJoueur).toList());
        }
        return gagnants.get(0);
    }

    /**
     * Imprime le gagnant.
     *
     * @param joueurs Les joueurs qui jouent.
     */
    public void printGagnant(ArrayList<Joueur> joueurs) {
        Affichage.gagnant(obtenirGagnant(joueurs));
    }

    /**
     * Imprime les noms des joueurs.
     *
     * @param joueurs Les joueurs qui jouent.
     */
    public void printJoueursInitialises(ArrayList<Joueur> joueurs) {
        Affichage.initialisation(joueurs);
    }

    /**
     * Imprime le classement des joueurs.
     *
     * @param joueurs Les joueurs qui jouent.
     */
    public void printClassement(ArrayList<Joueur> joueurs) {
        Collections.sort(joueurs);
        Affichage.classement(joueurs, this.nb2Tours);
    }

    /**
     * Permet d'affecter le nombre de joueurs pour une partie.
     *
     * @param nombre Nombre de joueurs d'une partie.
     */
    public void setNbJoueurs(int nombre) {
        nbJoueurs = nombre;
    }
}
