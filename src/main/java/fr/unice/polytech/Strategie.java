package fr.unice.polytech;

import fr.unice.polytech.piocher.*;
import fr.unice.polytech.pouvoirs.*;
import fr.unice.polytech.strategie.*;

/**
 * Classe qui initialise les pouvoirs des personnages et les stratégies
 */
public class Strategie {
    /**
     * Le joueur.
     */
    private final Joueur joueur;

    /**
     * Le choix de pioche du joueur.
     */
    private IPiocher iPiocher = new SuffisammentQuartier();

    /**
     * Le pouvoir du joueur.
     */
    private IPouvoir iPouvoir = null;

    /**
     * La stratégie utilisée.
     */
    private IStrategie iStrategie = new ComportementDefault();

    /**
     * Le constructeur de la stratégie initialisée avec un joueur.
     *
     * @param joueur Le joueur.
     */
    public Strategie(Joueur joueur) {
        this.joueur = joueur;
    }

    /**
     * Permet de récupérer une pioche.
     *
     * @return Une pioche.
     */
    public IPiocher getIPiocher() {
        this.choisirType2Piochage();
        return iPiocher;
    }

    /**
     * Permet de récupérer une stratégie.
     *
     * @return Une stratégie.
     */
    public IStrategie getIStrategie() {
        return iStrategie;
    }

    /**
     * Détermine la stratégie qu'utilise le joueur.
     *
     * @param strategie La stratégie qu'utilise le joueur.
     */
    public void setStrategie(String strategie) {
        switch (strategie) {
            case "Rusher" -> iStrategie = new RusherQuartiers();
            case "Merveille" -> iStrategie = new QuartierMerveilles();
            case "Agressif" -> iStrategie = new Agressif();
            case "VStrat" -> iStrategie = new VStrat();
            case "Commerce" -> iStrategie = new Commerce();
            case "Batisseur" -> iStrategie= new Batisseur();
            default -> iStrategie = new ComportementDefault();
        }
    }

    /**
     * Détermine l'action du pouvoir du joueur par rapport à son personnage.
     */
    public void actionPersonnage() {
        switch (this.joueur.getPersonnage().getNom()) {
            case "Assassin" -> iPouvoir = new PouvoirAssassin();
            case "Voleur" -> iPouvoir = new PouvoirVoleur();
            case "Magicien" -> iPouvoir = new PouvoirMagicien();
            case "Roi" -> iPouvoir = new PouvoirRoi();
            case "Évêque" -> iPouvoir = new PouvoirEveque();
            case "Marchand" -> iPouvoir = new PouvoirMarchand();
            case "Architecte" -> iPouvoir = new PouvoirArchitecte();
            case "Condottiere" -> iPouvoir = new PouvoirCondottiere();
            default -> iPouvoir = null;
        }
    }

    /**
     * Permet de choisir une stratégie en fonction de ce qui se trouve dans notre main, en fonction des cartes quartiers et de l'or.
     */
    public void choisirType2Piochage() {
        boolean parDefault = true;
        if (this.joueur.getQuartiers().size() < 2) { // Check le nombre de bonnes cartes dans la main
            this.iPiocher = new RechercheMeilleurQuartier();
            parDefault = false;
        }
        if (this.joueur.getQuartiers().size() > 6) {
            this.iPiocher = new SuffisammentQuartier();
            parDefault = false;
        }
        if (this.joueur.getOr() < 2) {
            this.iPiocher = new EconomiserArgent();
            parDefault = false;
        }
        if (this.joueur.getOr() > 6) {
            this.iPiocher = new SuffisammentOr();
            parDefault = false;
        }
        if (parDefault) {
            this.iPiocher = new SuffisammentQuartier();
        }
    }

    /**
     * Permet de passer au prochain tour. Si on est marchand ou évêque on utilise notre pouvoir en début de tour sinon on continue normalement.
     */
    public void prochainTour() {
        this.choisirType2Piochage();
        String nomPersonnage = joueur.getPersonnage().getNom();
        if (nomPersonnage.equals("Marchand") || nomPersonnage.equals("Évêque")) {
            this.actionPersonnage();
            this.iPouvoir.utiliserPouvoir(this.joueur);
            Affichage.sauterLigne();
            this.choisirType2Piochage();
            if (this.iPiocher != null) this.iPiocher.utiliserStrategie(this.joueur);
        } else {
            if (this.iPiocher != null) this.iPiocher.utiliserStrategie(this.joueur);
            this.choisirType2Piochage();
            Affichage.sauterLigne();
            this.actionPersonnage();
            this.iPouvoir.utiliserPouvoir(this.joueur);
        }
    }

    /**
     * Affichage de la stratégie utilisée par le joueur.
     *
     * @return Les informations sur la stratégie.
     */
    @Override
    public String toString() {
        return "Strategie{" +
                "iPiocher=" + iPiocher +
                ", iPouvoir=" + iPouvoir +
                ", iStrategie=" + iStrategie +
                '}';
    }
}
