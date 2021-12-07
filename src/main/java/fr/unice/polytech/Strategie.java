package fr.unice.polytech;

import fr.unice.polytech.piocher.*;
import fr.unice.polytech.pouvoirs.*;
import fr.unice.polytech.strategie.IStrategie;
import fr.unice.polytech.strategie.QuartierMerveilles;
import fr.unice.polytech.strategie.RusherQuartiers;

public class Strategie {
    private final Joueur joueur;
    private IPiocher iPiocher = new SuffisammentQuartier();
    private IPouvoir iPouvoir;
    private IStrategie iStrategie;

    public Strategie(Joueur joueur) {
        this.joueur = joueur;
    }

    public IPiocher getiPiocher() {
        this.choisirType2Piochage();
        return iPiocher;
    }

    public void setStrategie(String strategie) {
        switch (strategie) {
            case "Rusher" -> iStrategie = new RusherQuartiers();
            case "Merveille" -> iStrategie = new QuartierMerveilles();
            default -> iStrategie = null;
        }
    }

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

    public void choisirType2Piochage() {
        boolean parDefaut = true;
        if (this.joueur.getQuartiers().size() < 2) { // Check le nombre de bonnes cartes dans la main
            this.iPiocher = new RechercheMeilleurQuartier();
            parDefaut = false;
        }
        if (this.joueur.getQuartiers().size() > 6) {
            this.iPiocher = new SuffisammentQuartier();
            parDefaut = false;
        }
        if (this.joueur.getOr() < 2) {
            this.iPiocher = new EconomiserArgent();
            parDefaut = false;
        }
        if (this.joueur.getOr() > 6) {
            this.iPiocher = new SuffisammentOr();
            parDefaut = false;
        }
        if (parDefaut) {
            this.iPiocher = new SuffisammentQuartier();
        }
    }

    public void prochainTour() {
        this.choisirType2Piochage();
        String nomPersonnage = joueur.getPersonnage().getNom();
        if (nomPersonnage.equals("Marchand") || nomPersonnage.equals("Évêque")) {
            this.actionPersonnage();
            this.iPouvoir.utiliserPouvoir(this.joueur);
            System.out.println();
            this.choisirType2Piochage();
            if (this.iPiocher != null) this.iPiocher.utiliserStrategie(this.joueur);
        } else {
            if (this.iPiocher != null) this.iPiocher.utiliserStrategie(this.joueur);
            this.choisirType2Piochage();
            System.out.println();
            this.actionPersonnage();
            this.iPouvoir.utiliserPouvoir(this.joueur);
        }
    }

    @Override
    public String toString() {
        return "Strategie{" +
                "iPiocher=" + iPiocher +
                ", iPouvoir=" + iPouvoir +
                ", iStrategie=" + iStrategie +
                '}';
    }
}
